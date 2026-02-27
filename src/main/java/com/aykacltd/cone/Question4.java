package com.aykacltd.cone;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Question4 {

  // ── Domain Model ─────────────────────────────────────────────────────────

  public static void main(String[] args) {
    Bank bank = new Bank("JavaBank");

    Account alice = bank.createAccount("Alice", 1000.00);
    Account bob = bank.createAccount("Bob", 500.00);

    System.out.println();
    bank.checkBalance(alice.getId());
    bank.checkBalance(bob.getId());

    System.out.println();
    bank.deposit(alice.getId(), 250.00);
    bank.withdraw(bob.getId(), 100.00);

    System.out.println();
    bank.transfer(alice.getId(), bob.getId(), 300.00);

    System.out.println();
    bank.checkBalance(alice.getId());
    bank.checkBalance(bob.getId());

    // Edge-case: insufficient funds
    System.out.println();
    try {
      bank.withdraw(bob.getId(), 9999.00);
    } catch (IllegalStateException e) {
      System.out.println("Caught expected error: " + e.getMessage());
    }
  }

  // ── BankClaude Service ─────────────────────────────────────────────────────────

  static class Account {
    private final String id;
    private final String owner;
    private double balance;

    Account(String owner, double initialDeposit) {
      if (initialDeposit < 0) {
        throw new IllegalArgumentException("Initial deposit cannot be negative.");
      }
      this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
      this.owner = owner;
      this.balance = initialDeposit;
    }

    String getId() {
      return id;
    }

    String getOwner() {
      return owner;
    }

    double getBalance() {
      return balance;
    }

    void deposit(double amount) {
      if (amount <= 0) {
        throw new IllegalArgumentException("Deposit amount must be positive.");
      }
      balance += amount;
    }

    void withdraw(double amount) {
      if (amount <= 0) {
        throw new IllegalArgumentException("Withdrawal amount must be positive.");
      }
      if (amount > balance) {
        throw new IllegalStateException("Insufficient funds.");
      }
      balance -= amount;
    }

    @Override
    public String toString() {
      return String.format("Account[id=%s, owner=%s, balance=%.2f]", id, owner, balance);
    }
  }

  // ── Demo ─────────────────────────────────────────────────────────────────

  static class Bank {
    private final String name;
    private final Map<String, Account> accounts = new HashMap<>();

    Bank(String name) {
      this.name = name;
    }

    /**
     * Create a new account and return it.
     */
    Account createAccount(String owner, double initialDeposit) {
      Account acc = new Account(owner, initialDeposit);
      accounts.put(acc.getId(), acc);
      System.out.printf("[%s] Account created: %s%n", name, acc);
      return acc;
    }

    /**
     * Deposit money into an account.
     */
    void deposit(String accountId, double amount) {
      Account acc = getAccount(accountId);
      acc.deposit(amount);
      System.out.printf("[%s] Deposited %.2f to %s. New balance: %.2f%n",
          name, amount, accountId, acc.getBalance());
    }

    /**
     * Withdraw money from an account.
     */
    void withdraw(String accountId, double amount) {
      Account acc = getAccount(accountId);
      acc.withdraw(amount);
      System.out.printf("[%s] Withdrew %.2f from %s. New balance: %.2f%n",
          name, amount, accountId, acc.getBalance());
    }

    /**
     * Transfer funds between two accounts atomically.
     */
    void transfer(String fromId, String toId, double amount) {
      Account from = getAccount(fromId);
      Account to = getAccount(toId);
      from.withdraw(amount);
      to.deposit(amount);
      System.out.printf("[%s] Transferred %.2f from %s to %s.%n", name, amount, fromId, toId);
    }

    /**
     * Print balance for an account.
     */
    void checkBalance(String accountId) {
      Account acc = getAccount(accountId);
      System.out.printf("[%s] Balance for %s (%s): %.2f%n",
          name, acc.getOwner(), accountId, acc.getBalance());
    }

    private Account getAccount(String id) {
      Account acc = accounts.get(id);
      if (acc == null) {
        throw new IllegalArgumentException("Account not found: " + id);
      }
      return acc;
    }
  }
}
