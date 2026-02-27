package com.aykacltd.cone;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

  private long balance;
  private Lock lock;

  public Account(long balance) {
    this.balance = balance;
    this.lock = new ReentrantLock();
  }

  public boolean deposit(long money) {
    this.lock.lock();
    try {
      this.balance += money;
    } finally {
      this.lock.unlock();
    }
    return true;
  }

  public boolean withdraw(long money) {
    this.lock.lock();
    try {
      if (this.balance < money) {
        return false;
      }
      this.balance -= money;
    } finally {
      this.lock.unlock();
    }
    return true;
  }
}
