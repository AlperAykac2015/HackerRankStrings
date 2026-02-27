package com.aykacltd.cone;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * Thread-safety tests for {@link Bank}.
 *
 * <p>Each test verifies a correctness invariant that could silently break under concurrency:
 * <ul>
 *   <li><b>Deposit atomicity</b>  – exact balance after N concurrent deposits</li>
 *   <li><b>No-overdraft</b>       – balance never goes negative</li>
 *   <li><b>Money conservation</b> – total balance across all accounts is unchanged by transfers</li>
 *   <li><b>Deadlock freedom</b>   – circular transfers always complete within a timeout</li>
 *   <li><b>Account isolation</b>  – operations on independent accounts do not block each other</li>
 * </ul>
 *
 * <p>Balance inspection is done via reflection (package-private access would expose internals
 * unnecessarily); all other interactions use only the public {@code Bank} API.
 */
@DisplayName("Bank – Concurrency / Thread-Safety")
class BankConcurrencyTest {

  // ── Reflection helpers ────────────────────────────────────────────────────

  @SuppressWarnings("unchecked")
  private long balance(Bank bank, int accountId) {
    try {
      Field mapField = Bank.class.getDeclaredField("map");
      mapField.setAccessible(true);
      Map<Integer, Account> map = (Map<Integer, Account>) mapField.get(bank);

      Account account = map.get(accountId);
      Field balanceField = Account.class.getDeclaredField("balance");
      balanceField.setAccessible(true);
      return balanceField.getLong(account);
    } catch (ReflectiveOperationException e) {
      throw new AssertionError("Reflection failed – verify field names match Bank/Account", e);
    }
  }

  private long totalBalance(Bank bank, int numAccounts) {
    long total = 0;
    for (int i = 1; i <= numAccounts; i++) {
      total += balance(bank, i);
    }
    return total;
  }

  // ── 1. Deposit atomicity ─────────────────────────────────────────────────

  @Nested
  @DisplayName("1. Deposit atomicity")
  class DepositAtomicity {

    /**
     * N threads each deposit amount A into the same account D times.
     * Expected final balance = initial + N * D * A (no lost updates).
     */
    @RepeatedTest(5)
    @DisplayName("Concurrent deposits must not lose updates")
    void noLostUpdates() throws InterruptedException {
      int threads = 20;
      int depositsPerThread = 200;
      long amount = 3L;
      long initial = 0L;

      Bank bank = new Bank(new long[] {initial});
      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch ready = new CountDownLatch(threads);
      CountDownLatch start = new CountDownLatch(1);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        pool.submit(() -> {
          ready.countDown();
          try {
            start.await();
          } catch (InterruptedException ignored) {
          }
          for (int i = 0; i < depositsPerThread; i++) {
            bank.deposit(1, amount);
          }
          done.countDown();
        });
      }

      ready.await();
      start.countDown();          // release all threads simultaneously
      assertTrue(done.await(15, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      long expected = initial + (long) threads * depositsPerThread * amount;
      assertEquals(expected, balance(bank, 1),
          "Lost updates detected: concurrent deposits produced wrong balance");
    }

    /**
     * Deposits to different accounts must not interfere with each other.
     */
    @Test
    @DisplayName("Deposits to independent accounts are fully isolated")
    void independentAccountIsolation() throws InterruptedException {
      int threads = 10;
      int depositsPerThread = 100;
      long amount = 5L;
      int numAccounts = 5;

      Bank bank = new Bank(new long[] {0, 0, 0, 0, 0});
      try (ExecutorService pool = Executors.newFixedThreadPool(threads * numAccounts)) {
        CountDownLatch done = new CountDownLatch(threads * numAccounts);

        for (int acc = 1; acc <= numAccounts; acc++) {
          final int accountId = acc;
          for (int t = 0; t < threads; t++) {
            pool.submit(() -> {
              for (int i = 0; i < depositsPerThread; i++) {
                bank.deposit(accountId, amount);
              }
              done.countDown();
            });
          }
        }
        assertTrue(done.await(20, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      }

      long expectedPerAccount = (long) threads * depositsPerThread * amount;
      for (int acc = 1; acc <= numAccounts; acc++) {
        assertEquals(expectedPerAccount, balance(bank, acc),
            "Account " + acc + " has wrong balance after concurrent deposits");
      }
    }
  }

  // ── 2. No-overdraft guarantee ─────────────────────────────────────────────

  @Nested
  @DisplayName("2. No-overdraft guarantee")
  class NoOverdraftGuarantee {

    /**
     * Many threads compete to withdraw from a single account.
     * The number of successful withdrawals × amount must equal the amount actually drained,
     * and the final balance must be non-negative.
     */
    @RepeatedTest(5)
    @DisplayName("Balance must never go negative under concurrent withdrawals")
    void balanceNeverNegative() throws InterruptedException {
      long initial = 1_000L;
      int threads = 20;
      int attemptsPerThread = 100;
      long withdrawAmount = 7L;

      Bank bank = new Bank(new long[] {initial});
      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch ready = new CountDownLatch(threads);
      CountDownLatch start = new CountDownLatch(1);
      CountDownLatch done = new CountDownLatch(threads);
      AtomicInteger successes = new AtomicInteger(0);

      for (int t = 0; t < threads; t++) {
        pool.submit(() -> {
          ready.countDown();
          try {
            start.await();
          } catch (InterruptedException ignored) {
          }
          for (int i = 0; i < attemptsPerThread; i++) {
            if (bank.withdraw(1, withdrawAmount)) {
              successes.incrementAndGet();
            }
          }
          done.countDown();
        });
      }

      ready.await();
      start.countDown();
      assertTrue(done.await(15, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      long finalBalance = balance(bank, 1);
      assertTrue(finalBalance >= 0,
          "Balance went negative: " + finalBalance);
      assertEquals(initial - (long) successes.get() * withdrawAmount, finalBalance,
          "Successful withdrawals do not match the actual balance change");
    }

    /**
     * Exact drain: the account should be fully emptied when total attempts exactly equal
     * the initial balance with perfectly divisible amounts.
     */
    @Test
    @DisplayName("Account drains to exactly zero when demand equals supply")
    void exactDrain() throws InterruptedException {
      long perWithdrawal = 10L;
      int threads = 10;
      // Each thread withdraws 10 times → 10 * 10 * 10 = 1000 total demand = initial balance
      long initial = (long) threads * 10 * perWithdrawal;

      Bank bank = new Bank(new long[] {initial});
      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch done = new CountDownLatch(threads);
      AtomicInteger successes = new AtomicInteger(0);

      for (int t = 0; t < threads; t++) {
        pool.submit(() -> {
          for (int i = 0; i < 10; i++) {
            if (bank.withdraw(1, perWithdrawal)) {
              successes.incrementAndGet();
            }
          }
          done.countDown();
        });
      }

      assertTrue(done.await(10, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      assertEquals(threads * 10, successes.get(),
          "All withdrawals should succeed when total demand equals balance");
      assertEquals(0L, balance(bank, 1),
          "Account should be fully drained");
    }
  }

  // ── 3. Money conservation in transfers ───────────────────────────────────

  @Nested
  @DisplayName("3. Money conservation")
  class MoneyConservation {

    /**
     * N threads do random transfers between accounts. The total balance across all accounts
     * must equal the initial total regardless of concurrency.
     */
    @RepeatedTest(5)
    @DisplayName("Total balance is conserved across concurrent transfers")
    void totalBalanceConserved() throws InterruptedException {
      int numAccounts = 5;
      long balancePerAccount = 1_000L;
      long[] initial = new long[numAccounts];
      for (int i = 0; i < numAccounts; i++) {
        initial[i] = balancePerAccount;
      }
      long expectedTotal = balancePerAccount * numAccounts;

      Bank bank = new Bank(initial);
      int threads = 25;
      int transfersPerThread = 200;
      long transferAmount = 10L;

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch ready = new CountDownLatch(threads);
      CountDownLatch start = new CountDownLatch(1);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        final int threadId = t;
        pool.submit(() -> {
          ready.countDown();
          try {
            start.await();
          } catch (InterruptedException ignored) {
          }
          for (int i = 0; i < transfersPerThread; i++) {
            int from = (threadId % numAccounts) + 1;
            int to = (threadId + 1) % numAccounts + 1;
            bank.transfer(from, to, transferAmount);
          }
          done.countDown();
        });
      }

      ready.await();
      start.countDown();
      assertTrue(done.await(15, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      long actualTotal = totalBalance(bank, numAccounts);
      assertEquals(expectedTotal, actualTotal,
          "Money was created or destroyed: expected " + expectedTotal + " but got " + actualTotal);
    }

    /**
     * Bi-directional transfers (A→B and B→A simultaneously) must conserve total money.
     */
    @RepeatedTest(5)
    @DisplayName("Bidirectional transfers conserve total balance")
    void bidirectionalTransferConservation() throws InterruptedException {
      Bank bank = new Bank(new long[] {2_000L, 2_000L});
      long expectedTotal = 4_000L;
      int threads = 20;
      int ops = 500;
      long amount = 5L;

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch ready = new CountDownLatch(threads);
      CountDownLatch start = new CountDownLatch(1);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        final boolean forward = (t % 2 == 0);
        pool.submit(() -> {
          ready.countDown();
          try {
            start.await();
          } catch (InterruptedException ignored) {
          }
          for (int i = 0; i < ops; i++) {
            if (forward) {
              bank.transfer(1, 2, amount);
            } else {
              bank.transfer(2, 1, amount);
            }
          }
          done.countDown();
        });
      }

      ready.await();
      start.countDown();
      assertTrue(done.await(15, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      assertEquals(expectedTotal, totalBalance(bank, 2),
          "Bidirectional transfers did not conserve money");
    }

    /**
     * Transfers should not succeed if source has insufficient funds,
     * and no money should be lost when they are rejected.
     */
    @Test
    @DisplayName("Rejected transfers do not corrupt balances")
    void rejectedTransferPreservesBalance() throws InterruptedException {
      // Account 1 has only 100; many threads try to transfer 50, so only 2 can ever succeed
      Bank bank = new Bank(new long[] {100L, 0L});
      int threads = 20;
      int attempts = 100;

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch done = new CountDownLatch(threads);
      AtomicInteger successes = new AtomicInteger(0);

      for (int t = 0; t < threads; t++) {
        pool.submit(() -> {
          for (int i = 0; i < attempts; i++) {
            if (bank.transfer(1, 2, 50L)) {
              successes.incrementAndGet();
            }
          }
          done.countDown();
        });
      }

      assertTrue(done.await(10, TimeUnit.SECONDS), "Threads did not finish – possible deadlock");
      pool.shutdown();

      // Only 2 transfers of 50 can ever succeed (initial balance = 100)
      assertEquals(2, successes.get(),
          "More transfers succeeded than balance allows");
      assertEquals(0L, balance(bank, 1),
          "Source account balance mismatch after transfers");
      assertEquals(100L, balance(bank, 2),
          "Destination account balance mismatch after transfers");
    }
  }

  // ── 4. Deadlock freedom ───────────────────────────────────────────────────

  @Nested
  @DisplayName("4. Deadlock freedom")
  class DeadlockFreedom {

    /**
     * Classic deadlock scenario: Thread A holds lock on account 1 and waits for account 2;
     * Thread B holds lock on account 2 and waits for account 1.
     * The implementation must not deadlock.
     */
    @RepeatedTest(3)
    @DisplayName("Circular transfers between two accounts complete without deadlock")
    void circularTransfersTwoAccounts() throws InterruptedException {
      Bank bank = new Bank(new long[] {5_000L, 5_000L});
      int threads = 20;
      int ops = 500;
      long amount = 1L;

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        final boolean forward = (t % 2 == 0);
        pool.submit(() -> {
          for (int i = 0; i < ops; i++) {
            if (forward) {
              bank.transfer(1, 2, amount);
            } else {
              bank.transfer(2, 1, amount);
            }
          }
          done.countDown();
        });
      }

      assertTrue(done.await(10, TimeUnit.SECONDS),
          "Deadlock detected: transfers between two accounts timed out");
      pool.shutdown();
    }

    /**
     * More complex ring: 1→2→3→4→5→1, all simultaneously.
     */
    @RepeatedTest(3)
    @DisplayName("Ring transfers across five accounts complete without deadlock")
    void ringTransfersFiveAccounts() throws InterruptedException {
      int numAccounts = 5;
      Bank bank = new Bank(new long[] {1_000L, 1_000L, 1_000L, 1_000L, 1_000L});
      int threads = 20;
      int ops = 200;
      long amount = 1L;

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        final int threadId = t;
        pool.submit(() -> {
          for (int i = 0; i < ops; i++) {
            int from = (threadId % numAccounts) + 1;
            int to = (from % numAccounts) + 1;
            bank.transfer(from, to, amount);
          }
          done.countDown();
        });
      }

      assertTrue(done.await(10, TimeUnit.SECONDS),
          "Deadlock detected: ring transfers timed out");
      pool.shutdown();
    }
  }

  // ── 5. Mixed operations under high contention ────────────────────────────

  @Nested
  @DisplayName("5. Mixed operations under high contention")
  class HighContention {

    /**
     * Stress test: many threads doing deposits, withdrawals, and transfers simultaneously.
     * Verifies that the bank stays functional (no exceptions, no deadlock) and that
     * money is not created (total balance ≤ initial total + total deposited).
     */
    @RepeatedTest(3)
    @DisplayName("Money is never created under mixed high-contention operations")
    void moneyNotCreatedUnderMixedOps() throws InterruptedException {
      int numAccounts = 4;
      long perAccount = 1_000L;
      Bank bank = new Bank(new long[] {perAccount, perAccount, perAccount, perAccount});

      int threads = 30;
      int opsPerThread = 300;
      AtomicLong totalDeposited = new AtomicLong(0);

      ExecutorService pool = Executors.newFixedThreadPool(threads);
      CountDownLatch done = new CountDownLatch(threads);

      for (int t = 0; t < threads; t++) {
        final int threadId = t;
        pool.submit(() -> {
          for (int i = 0; i < opsPerThread; i++) {
            int account = (threadId % numAccounts) + 1;
            int other = ((threadId + 1) % numAccounts) + 1;
            int opType = i % 3;

            switch (opType) {
              case 0:
                bank.deposit(account, 5L);
                totalDeposited.addAndGet(5L);
                break;
              case 1:
                bank.withdraw(account, 5L);   // may fail – that's fine
                break;
              case 2:
                if (account != other) {
                  bank.transfer(account, other, 5L);  // may fail – that's fine
                }
                break;
            }
          }
          done.countDown();
        });
      }

      assertTrue(done.await(20, TimeUnit.SECONDS),
          "Deadlock detected: mixed-ops threads timed out");
      pool.shutdown();

      long actualTotal = totalBalance(bank, numAccounts);
      long maxPossible = perAccount * numAccounts + totalDeposited.get();
      assertTrue(actualTotal >= 0,
          "Total balance went negative: " + actualTotal);
      assertTrue(actualTotal <= maxPossible,
          "Money was created: actual " + actualTotal + " > max possible " + maxPossible);
    }

    /**
     * All threads hammer the same single account with competing deposits and withdrawals.
     * The final balance must be >= 0 and consistent with the net operations.
     */
    @RepeatedTest(3)
    @DisplayName("Single account survives extreme contention without corruption")
    void singleAccountExtremeContention() throws InterruptedException {
      long initial = 10_000L;
      Bank bank = new Bank(new long[] {initial});

      int depositorThreads = 10;
      int withdrawerThreads = 10;
      int opsPerThread = 500;
      long opAmount = 3L;

      ExecutorService pool = Executors.newFixedThreadPool(depositorThreads + withdrawerThreads);
      CountDownLatch done = new CountDownLatch(depositorThreads + withdrawerThreads);
      AtomicLong netDeposited = new AtomicLong(0);
      AtomicLong netWithdrawn = new AtomicLong(0);

      for (int t = 0; t < depositorThreads; t++) {
        pool.submit(() -> {
          for (int i = 0; i < opsPerThread; i++) {
            bank.deposit(1, opAmount);
            netDeposited.addAndGet(opAmount);
          }
          done.countDown();
        });
      }

      for (int t = 0; t < withdrawerThreads; t++) {
        pool.submit(() -> {
          for (int i = 0; i < opsPerThread; i++) {
            if (bank.withdraw(1, opAmount)) {
              netWithdrawn.addAndGet(opAmount);
            }
          }
          done.countDown();
        });
      }

      assertTrue(done.await(20, TimeUnit.SECONDS),
          "Deadlock detected: single-account contention timed out");
      pool.shutdown();

      long expectedBalance = initial + netDeposited.get() - netWithdrawn.get();
      long actualBalance = balance(bank, 1);

      assertTrue(actualBalance >= 0, "Balance went negative: " + actualBalance);
      assertEquals(expectedBalance, actualBalance,
          "Balance inconsistency detected (possible race condition): "
              + "expected " + expectedBalance + " but got " + actualBalance);
    }
  }
}
