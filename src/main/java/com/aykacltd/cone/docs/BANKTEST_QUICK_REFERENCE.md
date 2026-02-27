# BankTest Quick Reference

## Test File Location

```
src/test/java/com/aykacltd/cone/BankTest.java
```

## Test Execution Summary

✅ **16 tests** - All passing

- BasicFunctionalityTests: 7 tests
- ConcurrentDepositsTests: 1 test
- ConcurrentWithdrawalsTests: 2 tests
- ConcurrentTransfersTests: 2 tests
- MixedConcurrentOperationsTests: 2 tests
- RaceConditionTests: 2 tests

## Test Categories

### ✓ Single-threaded Operations

Ensures basic correctness of deposit, withdraw, transfer operations

### ✓ Concurrent Deposits

- 10 threads × 100 deposits = 1000 total operations
- Verifies atomicity of concurrent deposits

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

## How Thread Safety is Achieved

1. **ConcurrentHashMap** - Atomic map operations
2. **ReentrantLock** - Per-account balance protection
3. **Proper Lock Semantics** - No deadlock risk

## Run Tests

```bash
# All tests
mvn test -Dtest=BankTest

# Specific test class
mvn test -Dtest=BankTest$BasicFunctionalityTests

# With detailed output
mvn test -Dtest=BankTest -Dtest.verbose=true
```

## Key Features

- **Timeout Protection**: 10-second timeout for deadlock detection
- **Atomic Counters**: AtomicInteger for thread-safe result counting
- **CountDownLatch**: Precise thread synchronization
- **ExecutorService**: Thread pool management
- **Isolation**: Each test uses fresh Bank instance

## Expected Behavior

- All operations complete without hanging
- Overdrafts are prevented
- Transfers maintain balance consistency
- No deadlocks occur
- Results are deterministic

---

Created: 2026-02-27
Test Framework: JUnit Jupiter (JUnit 5)
