package com.aykacltd.cone;

import java.util.concurrent.locks.ReentrantLock;

/**
 * LeetCode 2043 - Simple BankClaude System
 * <p>
 * Thread-Safety Strategy:
 * - Each account has its own ReentrantLock (fine-grained locking).
 * - For transfer(), we always acquire locks in a consistent order
 * (lower index first) to prevent deadlock.
 * - deposit() and withdraw() lock only the single relevant account.
 * <p>
 * Time:  O(1) per operation
 * Space: O(n) for balances + locks arrays
 */
public class BankClaude {

  private final long[] balance;
  private final ReentrantLock[] locks;
  private final int n;

  public BankClaude(long[] balance) {
    this.n = balance.length;
    this.balance = balance.clone();          // defensive copy
    this.locks = new ReentrantLock[n];
    for (int i = 0; i < n; i++) {
      locks[i] = new ReentrantLock();
    }
  }

  // ── Validation helper ────────────────────────────────────────────────────

  public static void main(String[] args) throws InterruptedException {

    // ── Basic correctness test ────────────────────────────────────────
    System.out.println("=== Basic Correctness ===");
    BankClaude bankClaude = new BankClaude(new long[] {10, 100, 20, 50, 30});

    System.out.println(bankClaude.withdraw(3, 10));   // true  (20 >= 10)
    System.out.println(bankClaude.transfer(5, 1, 20));// true  (30 >= 20)
    System.out.println(bankClaude.deposit(5, 20));    // true
    System.out.println(bankClaude.transfer(3, 4, 15));// false (10 < 15)
    System.out.println(bankClaude.transfer(3, 2, 10));// true  (10 >= 10)

    // ── Thread-safety stress test ─────────────────────────────────────
    System.out.println("\n=== Thread-Safety Stress Test ===");

    // 2 accounts, each starting with 1000; run 10000 transfers concurrently
    BankClaude stressBankClaude = new BankClaude(new long[] {1000, 1000});
    int THREADS = 20;
    int OPS = 500;

    Thread[] threads = new Thread[THREADS];
    for (int t = 0; t < THREADS; t++) {
      final int tid = t;
      threads[t] = new Thread(() -> {
        for (int i = 0; i < OPS; i++) {
          if (tid % 2 == 0) {
            stressBankClaude.transfer(1, 2, 10);
          } else {
            stressBankClaude.transfer(2, 1, 10);
          }
        }
      });
    }

    for (Thread th : threads) {
      th.start();
    }
    for (Thread th : threads) {
      th.join();
    }

    // Total money must still equal 2000 (conservation invariant)
    long total = stressBankClaude.balance[0] + stressBankClaude.balance[1];
    System.out.println("Total balance after concurrent transfers: " + total
        + " (expected 2000) " + (total == 2000 ? "✓ PASS" : "✗ FAIL"));
  }

  // ── Public API ───────────────────────────────────────────────────────────

  private boolean isValid(int account) {
    return account >= 1 && account <= n;     // accounts are 1-indexed
  }

  /**
   * Deposit money into account.
   * Valid if: account in [1, n].  (deposit can never overdraw)
   */
  public boolean deposit(int account, long money) {
    if (!isValid(account)) {
      return false;
    }

    int idx = account - 1;
    locks[idx].lock();
    try {
      balance[idx] += money;
      return true;
    } finally {
      locks[idx].unlock();           // always released, even on exception
    }
  }

  /**
   * Withdraw money from account.
   * Valid if: account in [1, n] AND balance[account] >= money.
   */
  public boolean withdraw(int account, long money) {
    if (!isValid(account)) {
      return false;
    }

    int idx = account - 1;
    locks[idx].lock();
    try {
      if (balance[idx] < money) {
        return false;
      }
      balance[idx] -= money;
      return true;
    } finally {
      locks[idx].unlock();
    }
  }

  // ── Demo / Test ──────────────────────────────────────────────────────────

  /**
   * Transfer money from account1 to account2.
   * Valid if: both accounts in [1, n] AND balance[account1] >= money.
   * <p>
   * Deadlock prevention: always lock the lower-index account first.
   * This enforces a global lock-ordering invariant across all threads.
   */
  public boolean transfer(int account1, int account2, long money) {
    if (!isValid(account1) || !isValid(account2)) {
      return false;
    }
    if (account1 == account2) {
      // Same account: just validate balance, no net change needed
      int idx = account1 - 1;
      locks[idx].lock();
      try {
        return balance[idx] >= money;
      } finally {
        locks[idx].unlock();
      }
    }

    // Determine locking order to prevent deadlock
    int first = Math.min(account1, account2) - 1;
    int second = Math.max(account1, account2) - 1;
    int src = account1 - 1;
    int dst = account2 - 1;

    locks[first].lock();
    try {
      locks[second].lock();
      try {
        if (balance[src] < money) {
          return false;
        }
        balance[src] -= money;
        balance[dst] += money;
        return true;
      } finally {
        locks[second].unlock();
      }
    } finally {
      locks[first].unlock();
    }
  }
}
