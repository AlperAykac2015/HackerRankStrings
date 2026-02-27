# BankReadWriteTest Quick Reference

## Test File Location

```
src/test/java/com/aykacltd/cone/BankReadWriteTest.java
```

## Test Execution Summary

✅ **21 tests** - All passing

- BasicFunctionalityTests: 7 tests
- ReadLockEfficiencyTests: 2 tests
- ConcurrentDepositsTests: 2 tests
- ConcurrentWithdrawalsTests: 2 tests
- ConcurrentTransfersTests: 2 tests
- MixedConcurrentOperationsTests: 2 tests
- RaceConditionTests: 2 tests
- ReadWriteLockSpecificTests: 2 tests

## Test Categories

### ✓ Single-threaded Operations

Ensures basic correctness of deposit, withdraw, transfer operations

### ✓ Read Lock Efficiency

- Multiple concurrent reads without blocking
- Proper read-write interleaving

### ✓ Concurrent Deposits

- 10 threads × 100 deposits = 1000 total operations
- High-volume stress test (50 threads)

### ✓ Concurrent Withdrawals

- Tests with sufficient funds (all succeed)
- Tests overdraft prevention (prevents negative balance)

### ✓ Concurrent Transfers

- Multiple accounts transferring to each other
- Circular transfer deadlock detection (10-second timeout)

### ✓ Mixed Operations

- Simultaneous deposits, withdrawals, and transfers
- High-contention scenario (50 threads)

### ✓ Race Condition Detection

- Balance consistency under concurrent read/write
- Self-transfers stress test

### ✓ ReadWriteLock Specific Tests

- Fair mode prioritization
- Writer starvation prevention

## Key Advantage: ReentrantReadWriteLock

```java
// Multiple readers can access simultaneously
readLock.lock();
try {
  return this.balance;  // Read operation
} finally {
  readLock.unlock();
}

// Exclusive write access
writeLock.lock();
try {
  this.balance += money;  // Write operation
} finally {
  writeLock.unlock();
}
```

## How Thread Safety is Achieved

1. **ReentrantReadWriteLock** - Allows concurrent reads, exclusive writes
2. **ConcurrentHashMap** - Atomic map operations
3. **Fair Mode Configuration** - Prevents reader/writer starvation
4. **Proper Lock Semantics** - No deadlock risk

## Run Tests

```bash
# All tests
mvn test -Dtest=BankReadWriteTest

# Specific test class
mvn test -Dtest=BankReadWriteTest$ReadLockEfficiencyTests

# With detailed output
mvn test -Dtest=BankReadWriteTest -Dtest.verbose=true
```

## Performance Benefits

| Scenario           | Bank (ReentrantLock) | BankReadWrite (ReentrantReadWriteLock) |
|--------------------|----------------------|----------------------------------------|
| Single write       | ✓ Optimal            | ✓ Good                                 |
| Single read        | ✓ Optimal            | ✓ Optimal                              |
| Multiple writes    | ✓ Serialized         | ✓ Serialized                           |
| **Multiple reads** | ✓ Serialized         | ✅ **Concurrent**                       |
| Mixed workload     | ✓ Good               | ✓ Better for read-heavy                |

## Expected Behavior

- All operations complete without hanging
- Overdrafts are prevented
- Transfers maintain balance consistency
- No deadlocks occur
- Multiple readers never block each other
- Writers are never starved

## Key Test Timeout

- All tests use 10-second timeout for deadlock detection
- If a test times out, it indicates a deadlock issue

---

Created: 2026-02-27
Test Framework: JUnit Jupiter (JUnit 5)
Lock Type: ReentrantReadWriteLock with fair mode
