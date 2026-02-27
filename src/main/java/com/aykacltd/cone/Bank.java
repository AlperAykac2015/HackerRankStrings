package com.aykacltd.cone;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Bank {

  private Map<Integer, Account> map;

  public Bank(long[] balance) {
    this.map = new ConcurrentHashMap<>();
    for (int i = 0; i < balance.length; i++) {
      map.put(i + 1, new Account(balance[i]));
    }
  }

  public boolean transfer(int account1, int account2, long money) {
    if (!map.containsKey(account1) || !map.containsKey(account2) ||
        !map.get(account1).withdraw(money)) {
      return false;
    }
    return map.get(account2).deposit(money);
  }

  public boolean deposit(int account, long money) {
    if (!map.containsKey(account)) {
      return false;
    }
    return map.get(account).deposit(money);
  }

  public boolean withdraw(int account, long money) {
    if (!map.containsKey(account)) {
      return false;
    }
    return map.get(account).withdraw(money);
  }
}
