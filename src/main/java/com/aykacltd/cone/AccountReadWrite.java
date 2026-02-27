package com.aykacltd.cone;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccountReadWrite {

  private long balance;
  private ReentrantReadWriteLock.ReadLock readLock;
  private ReentrantReadWriteLock.WriteLock writeLock;

  public AccountReadWrite(long balance) {
    this.balance = balance;
    this.readLock = new ReentrantReadWriteLock(true).readLock();
    this.writeLock = new ReentrantReadWriteLock(true).writeLock();
  }

  public boolean deposit(long money) {
    this.writeLock.lock();
    try {
      this.balance += money;
    } finally {
      this.writeLock.unlock();
    }
    return true;
  }

  public boolean withdraw(long money) {
    this.writeLock.lock();
    try {
      if (this.balance < money) {
        return false;
      }
      this.balance -= money;
    } finally {
      this.writeLock.unlock();
    }
    return true;
  }

  public long getBalance() {
    this.readLock.lock();
    try {
      return this.balance;
    } finally {
      this.readLock.unlock();
    }
  }

}
