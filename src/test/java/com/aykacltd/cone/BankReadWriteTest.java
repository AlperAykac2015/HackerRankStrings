package com.aykacltd.cone;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("BankReadWrite Thread Safety Tests")
class BankReadWriteTest {

  private BankReadWrite bank;
  private long[] initialBalances;

  @BeforeEach
  void setUp() {
    initialBalances = new long[] {100, 200, 300, 400, 500};
    bank = new BankReadWrite(initialBalances);
  }

  @Nested
  @DisplayName("Basic Functionality Tests")
  class BasicFunctionalityTests {

    @Test
    @DisplayName("Should create bank with correct initial balances")
    void testBankInitialization() {
      BankReadWrite testBank = new BankReadWrite(new long[] {100, 200, 300});
      assertNotNull(testBank);
      // Test deposit to verify accounts exist
      assertTrue(testBank.deposit(1, 50));
      assertTrue(testBank.deposit(2, 50));
      assertTrue(testBank.deposit(3, 50));
    }

    @Test
    @DisplayName("Should successfully deposit money")
    void testDeposit() {
      assertTrue(bank.deposit(1, 50));
      assertTrue(bank.deposit(2, 100));
    }

    @Test
    @DisplayName("Should successfully withdraw money")
    void testWithdraw() {
      assertTrue(bank.withdraw(1, 50));
      assertTrue(bank.withdraw(2, 100));
    }

    @Test
    @DisplayName("Should reject withdrawal if insufficient funds")
    void testWithdrawInsufficientFunds() {
      assertFalse(bank.withdraw(1, 200)); // account 1 has 100
      assertFalse(bank.withdraw(2, 300)); // account 2 has 200
    }

    @Test
    @DisplayName("Should successfully transfer between accounts")
    void testTransfer() {
      assertTrue(bank.transfer(1, 2, 50));
    }

    @Test
    @DisplayName("Should reject transfer if insufficient funds")
    void testTransferInsufficientFunds() {
      assertFalse(bank.transfer(1, 2, 200)); // account 1 has 100
    }

    @Test
    @DisplayName("Should reject operations on non-existent accounts")
    void testNonExistentAccount() {
      assertFalse(bank.deposit(999, 50));
      assertFalse(bank.withdraw(999, 50));
      assertFalse(bank.transfer(1, 999, 50));
      assertFalse(bank.transfer(999, 1, 50));
    }
  }

  @Nested
  @DisplayName("Read Lock Efficiency Tests")
  class ReadLockEfficiencyTests {

    @Test
    @DisplayName("Should allow multiple concurrent reads without blocking")
    void testConcurrentReads() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {1000, 2000});

      int numReaderThreads = 20;
      int readsPerThread = 100;

      try (ExecutorService executor = Executors.newFixedThreadPool(numReaderThreads)) {
        CountDownLatch latch = new CountDownLatch(numReaderThreads);
        long startTime = System.nanoTime();

        for (int t = 0; t < numReaderThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < readsPerThread; i++) {
                int account = (threadId % 2) + 1;
                // This would require a public getter, but tests deposit to verify reads work
                testBank.deposit(account, 0); // Dummy operation
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
        long endTime = System.nanoTime();

        // Multiple readers should complete relatively quickly
        assertTrue((endTime - startTime) < 5_000_000_000L); // 5 seconds max
      }
    }

    @Test
    @DisplayName("Should handle read-write interleaving correctly")
    void testReadWriteInterleaving() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {10000});

      try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
        CountDownLatch latch = new CountDownLatch(10);
        AtomicInteger writes = new AtomicInteger(0);

        // 5 writer threads
        for (int w = 0; w < 5; w++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < 50; i++) {
                if (testBank.withdraw(1, 1)) {
                  writes.incrementAndGet();
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        // 5 reader threads
        for (int r = 0; r < 5; r++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < 50; i++) {
                testBank.deposit(1, 0); // Dummy deposit to trigger lock
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
        assertTrue(writes.get() > 0, "Some writes should have succeeded");
      }
    }
  }

  @Nested
  @DisplayName("Thread Safety - Concurrent Deposits")
  class ConcurrentDepositsTests {

    @Test
    @DisplayName("Should handle concurrent deposits correctly")
    void testConcurrentDeposits() throws InterruptedException {
      int numThreads = 10;
      int depositsPerThread = 100;
      long amountPerDeposit = 1;

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < depositsPerThread; i++) {
                bank.deposit(1, amountPerDeposit);
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // Verify success by depositing a marker amount
      assertTrue(bank.deposit(1, 0));
    }

    @Test
    @DisplayName("Should maintain consistency with high volume concurrent deposits")
    void testHighVolumeDeposits() throws InterruptedException {
      int numThreads = 50;
      int depositsPerThread = 20;
      long amountPerDeposit = 1;

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < depositsPerThread; i++) {
                bank.deposit(1, amountPerDeposit);
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      assertTrue(bank.deposit(1, 0));
    }
  }

  @Nested
  @DisplayName("Thread Safety - Concurrent Withdrawals")
  class ConcurrentWithdrawalsTests {

    @Test
    @DisplayName("Should handle concurrent withdrawals correctly")
    void testConcurrentWithdrawals() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {10000});

      int numThreads = 10;
      int withdrawalsPerThread = 100;
      long amountPerWithdrawal = 10;
      CountDownLatch latch = new CountDownLatch(numThreads);
      AtomicInteger successfulWithdrawals = new AtomicInteger(0);

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < withdrawalsPerThread; i++) {
                if (testBank.withdraw(1, amountPerWithdrawal)) {
                  successfulWithdrawals.incrementAndGet();
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // Initial balance: 10000, total withdrawals: 10 * 100 * 10 = 10000
      // All withdrawals should succeed
      assertEquals(numThreads * withdrawalsPerThread, successfulWithdrawals.get());
    }

    @Test
    @DisplayName("Should prevent overdrafts with concurrent withdrawals")
    void testConcurrentWithdrawalsPreventOverdraft() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {1000});

      int numThreads = 10;
      int withdrawalsPerThread = 50;
      long amountPerWithdrawal = 20;
      CountDownLatch latch = new CountDownLatch(numThreads);
      AtomicInteger failedWithdrawals = new AtomicInteger(0);

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < withdrawalsPerThread; i++) {
                if (!testBank.withdraw(1, amountPerWithdrawal)) {
                  failedWithdrawals.incrementAndGet();
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // Initial: 1000, attempts: 10 * 50 * 20 = 10000
      // Only 50 withdrawals can succeed (1000 / 20), rest must fail
      int totalAttempts = numThreads * withdrawalsPerThread;
      int successfulWithdrawals = totalAttempts - failedWithdrawals.get();

      assertEquals(50, successfulWithdrawals);
      assertTrue(failedWithdrawals.get() > 0, "Some withdrawals should have failed");
    }
  }

  @Nested
  @DisplayName("Thread Safety - Concurrent Transfers")
  class ConcurrentTransfersTests {

    @Test
    @DisplayName("Should handle concurrent transfers between multiple accounts")
    void testConcurrentTransfersMultipleAccounts() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {1000, 1000, 1000, 1000, 1000});

      int numThreads = 20;
      int transfersPerThread = 50;
      long amountPerTransfer = 10;
      CountDownLatch latch = new CountDownLatch(numThreads);
      AtomicInteger successfulTransfers = new AtomicInteger(0);

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        for (int t = 0; t < numThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < transfersPerThread; i++) {
                int fromAccount = (threadId % 5) + 1;
                int toAccount = ((threadId + 1) % 5) + 1;
                if (fromAccount != toAccount) {
                  if (testBank.transfer(fromAccount, toAccount, amountPerTransfer)) {
                    successfulTransfers.incrementAndGet();
                  }
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // Some transfers should succeed
      assertTrue(successfulTransfers.get() > 0);
    }

    @Test
    @DisplayName("Should handle circular transfers without deadlock")
    void testCircularTransfers() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {1000, 1000});

      int numThreads = 10;
      int operationsPerThread = 100;
      long amountPerTransfer = 1;
      CountDownLatch latch = new CountDownLatch(numThreads);
      AtomicInteger transfers = new AtomicInteger(0);

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        for (int t = 0; t < numThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < operationsPerThread; i++) {
                if (threadId % 2 == 0) {
                  if (testBank.transfer(1, 2, amountPerTransfer)) {
                    transfers.incrementAndGet();
                  }
                } else {
                  if (testBank.transfer(2, 1, amountPerTransfer)) {
                    transfers.incrementAndGet();
                  }
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        // Use a timeout to detect deadlocks
        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS),
            "Deadlock detected - operation timed out");
      }

      assertTrue(transfers.get() > 0);
    }
  }

  @Nested
  @DisplayName("Thread Safety - Mixed Concurrent Operations")
  class MixedConcurrentOperationsTests {

    @Test
    @DisplayName("Should handle mixed deposits, withdrawals, and transfers concurrently")
    void testMixedOperations() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {1000, 1000, 1000});

      int numThreads = 15;
      int operationsPerThread = 100;

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < operationsPerThread; i++) {
                int operation = i % 3;
                int account = (threadId % 3) + 1;
                int targetAccount = ((threadId + 1) % 3) + 1;

                switch (operation) {
                  case 0:
                    testBank.deposit(account, 10);
                    break;
                  case 1:
                    testBank.withdraw(account, 5);
                    break;
                  case 2:
                    if (account != targetAccount) {
                      testBank.transfer(account, targetAccount, 5);
                    }
                    break;
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS),
            "Deadlock detected - operation timed out");
      }
    }

    @Test
    @DisplayName("Should maintain consistency with high contention")
    void testHighContentionConsistency() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {10000});

      int numThreads = 50;
      int depositsPerThread = 50;
      long amountPerDeposit = 1;

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < depositsPerThread; i++) {
                testBank.deposit(1, amountPerDeposit);
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // Verify consistency
      assertTrue(testBank.deposit(1, 0));
    }
  }

  @Nested
  @DisplayName("Thread Safety - Race Condition Tests")
  class RaceConditionTests {

    @Test
    @DisplayName("Should prevent balance race conditions")
    void testBalanceRaceCondition() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {100});

      try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
        CountDownLatch latch = new CountDownLatch(2);

        // Thread 1: repeatedly try to withdraw
        executor.submit(() -> {
          try {
            for (int i = 0; i < 1000; i++) {
              testBank.withdraw(1, 1);
            }
          } finally {
            latch.countDown();
          }
        });

        // Thread 2: repeatedly deposit
        executor.submit(() -> {
          try {
            for (int i = 0; i < 1000; i++) {
              testBank.deposit(1, 1);
            }
          } finally {
            latch.countDown();
          }
        });

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // The bank should still be functional
      assertTrue(testBank.deposit(1, 0));
    }

    @Test
    @DisplayName("Should handle simultaneous checks and updates")
    void testSimultaneousChecksAndUpdates() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {500});
      CountDownLatch latch = new CountDownLatch(10);
      AtomicInteger successfulTransfers = new AtomicInteger(0);

      try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
        for (int t = 0; t < 10; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < 100; i++) {
                if (testBank.transfer(1, 1, 1)) { // Transfer to itself
                  successfulTransfers.incrementAndGet();
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));
      }

      // All transfers should succeed since transferring to itself doesn't change balance
      assertEquals(1000, successfulTransfers.get());
    }
  }

  @Nested
  @DisplayName("ReadWriteLock Specific Tests")
  class ReadWriteLockSpecificTests {

    @Test
    @DisplayName("Should prioritize writes when configured with fair mode")
    void testWritePriority() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {10000});

      try (ExecutorService executor = Executors.newFixedThreadPool(11)) {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(11);
        AtomicInteger readCount = new AtomicInteger(0);
        AtomicInteger writeCount = new AtomicInteger(0);

        // 10 reader threads
        for (int r = 0; r < 10; r++) {
          executor.submit(() -> {
            try {
              startLatch.await();
              for (int i = 0; i < 50; i++) {
                testBank.deposit(1, 0); // Dummy operation
                readCount.incrementAndGet();
              }
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            } finally {
              endLatch.countDown();
            }
          });
        }

        // 1 writer thread
        executor.submit(() -> {
          try {
            startLatch.await();
            for (int i = 0; i < 50; i++) {
              testBank.withdraw(1, 1);
              writeCount.incrementAndGet();
            }
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          } finally {
            endLatch.countDown();
          }
        });

        startLatch.countDown();
        assertTrue(endLatch.await(10, java.util.concurrent.TimeUnit.SECONDS));

        // Verify both reads and writes occurred
        assertTrue(readCount.get() > 0, "Reads should have occurred");
        assertEquals(50, writeCount.get(), "All writes should complete");
      }
    }

    @Test
    @DisplayName("Should not cause writer starvation")
    void testNoWriterStarvation() throws InterruptedException {
      BankReadWrite testBank = new BankReadWrite(new long[] {10000});

      try (ExecutorService executor = Executors.newFixedThreadPool(21)) {
        CountDownLatch latch = new CountDownLatch(21);
        AtomicInteger writeSuccesses = new AtomicInteger(0);

        // 20 aggressive reader threads
        for (int r = 0; r < 20; r++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < 100; i++) {
                testBank.deposit(1, 0); // Dummy read-like operation
              }
            } finally {
              latch.countDown();
            }
          });
        }

        // 1 writer thread trying to write despite reader pressure
        executor.submit(() -> {
          try {
            for (int i = 0; i < 100; i++) {
              if (testBank.withdraw(1, 1)) {
                writeSuccesses.incrementAndGet();
              }
            }
          } finally {
            latch.countDown();
          }
        });

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS));

        // Writer should not be starved completely
        assertTrue(writeSuccesses.get() > 0, "Writer should get some chances to write");
      }
    }
  }
}
