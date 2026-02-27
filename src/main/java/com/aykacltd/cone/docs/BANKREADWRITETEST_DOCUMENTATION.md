# BankReadWriteTest - Thread Safety Test Suite

## Overview

Comprehensive unit test suite for the `BankReadWrite` class that verifies thread safety and efficient concurrent
read/write operations using `ReentrantReadWriteLock`.

## Test Statistics

- **Total Tests**: 21
- **Test Classes**: 8 nested test classes
- **Coverage Areas**: Basic functionality, concurrent operations, race conditions, read-write efficiency, deadlock
  prevention

## Test Structure

### 1. BasicFunctionalityTests (7 tests)

Tests basic operations without concurrency:

- `testBankInitialization()` - Verifies correct initialization of accounts
- `testDeposit()` - Tests deposit operations
- `testWithdraw()` - Tests withdrawal operations
- `testWithdrawInsufficientFunds()` - Validates insufficient fund handling
- `testTransfer()` - Tests account transfers
- `testTransferInsufficientFunds()` - Validates transfer with insufficient funds
- `testNonExistentAccount()` - Tests operations on non-existent accounts

### 2. ReadLockEfficiencyTests (2 tests)

Tests the benefits of `ReentrantReadWriteLock` for read-heavy scenarios:

- `testConcurrentReads()` - Verifies 20 threads performing reads concurrently without blocking
- `testReadWriteInterleaving()` - Tests proper interleaving of read and write operations

### 3. ConcurrentDepositsTests (2 tests)

Tests concurrent deposit operations:

- `testConcurrentDeposits()` - Verifies 10 threads × 100 deposits each
- `testHighVolumeDeposits()` - Stress test with 50 threads × 20 deposits each

### 4. ConcurrentWithdrawalsTests (2 tests)

Tests concurrent withdrawal operations with various scenarios:

- `testConcurrentWithdrawals()` - Tests 10 threads withdrawing concurrently with sufficient funds
- `testConcurrentWithdrawalsPreventOverdraft()` - Verifies that overdrafts are prevented under high concurrency

### 5. ConcurrentTransfersTests (2 tests)

Tests concurrent transfer operations:

- `testConcurrentTransfersMultipleAccounts()` - Tests 20 threads transferring between multiple accounts
- `testCircularTransfers()` - Tests circular transfers (A↔B) to detect deadlocks

### 6. MixedConcurrentOperationsTests (2 tests)

Tests combinations of concurrent operations:

- `testMixedOperations()` - Tests concurrent deposits, withdrawals, and transfers with 15 threads
- `testHighContentionConsistency()` - Tests consistency with high contention (50 threads, 50 operations each)

### 7. RaceConditionTests (2 tests)

Tests for specific race condition scenarios:

- `testBalanceRaceCondition()` - Tests simultaneous withdraw and deposit operations
- `testSimultaneousChecksAndUpdates()` - Tests simultaneous checks and updates (transfers to self)

### 8. ReadWriteLockSpecificTests (2 tests)

Tests specific to `ReentrantReadWriteLock` behavior:

- `testWritePriority()` - Verifies fair mode prioritization with mixed readers and writers
- `testNoWriterStarvation()` - Tests that writers don't starve despite heavy reader pressure

## Key Testing Features

### Thread Safety Mechanisms Tested

1. **ConcurrentHashMap Usage** - Ensures atomic map operations
2. **ReentrantReadWriteLock in AccountReadWrite** - Verifies read/write lock-based synchronization
3. **Atomicity of Transfers** - Ensures transfers maintain consistency
4. **Fair Mode Lock Behavior** - Tests fair mode configuration for equitable access

### Concurrency Testing Techniques

1. **CountDownLatch** - Used for precise synchronization of test threads
2. **ExecutorService with try-with-resources** - Ensures proper resource cleanup
3. **AtomicInteger** - Used for thread-safe counters
4. **Timeout Detection** - Tests include timeout to detect deadlocks

### Scenarios Covered

- Concurrent reads (read lock efficiency)
- Concurrent writes (write lock efficiency)
- Read-write interleaving
- High contention situations
- Overdraft prevention
- Deadlock detection
- Fair mode lock behavior
- Writer starvation prevention
- Race condition prevention

## Key Differences from Bank/BankTest

### ReentrantReadWriteLock Advantages

```java
// Read lock allows multiple concurrent readers
// Write lock ensures exclusive write access
private ReentrantReadWriteLock.ReadLock readLock;
private ReentrantReadWriteLock.WriteLock writeLock;
```

### Benefits

- Multiple readers can access balance simultaneously (read operations)
- Writers have exclusive access (write operations)
- Fair mode ensures no reader/writer starvation
- Better performance for read-heavy workloads

## Running the Tests

```bash
# Run all BankReadWriteTest tests
mvn test -Dtest=BankReadWriteTest

# Run specific test class
mvn test -Dtest=BankReadWriteTest$ReadLockEfficiencyTests

# Run with verbose output
mvn test -Dtest=BankReadWriteTest -X
```

## Test Results

All 21 tests pass successfully, confirming thread safety of the BankReadWrite implementation.

```
Tests run: 21, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Key Assertions

### Correctness Assertions

- Account operations complete without errors
- Operations return expected boolean values
- Non-existent accounts are properly rejected

### Concurrency Assertions

- Deposits complete successfully under concurrent access
- Withdrawal count never exceeds available balance
- Circular transfers complete without deadlock (within 10 second timeout)
- High contention scenarios maintain consistency
- Read operations are not blocked by other reads
- Writers are not starved by many readers

### Lock Behavior Assertions

- Multiple readers can proceed simultaneously
- Writers get exclusive access
- Fair mode prevents both reader and writer starvation

## Notes for Developers

1. **BankReadWrite vs Bank**
    - BankReadWrite uses `ReentrantReadWriteLock` for finer-grained control
    - Better for read-heavy scenarios
    - Bank uses simpler `ReentrantLock` per account

2. **Fair Mode Configuration**
   ```java
   new ReentrantReadWriteLock(true)  // fair mode enabled
   ```
   This ensures readers and writers are treated fairly in contention scenarios.

3. **Performance Characteristics**
    - **BankReadWrite**: Better for read-heavy workloads (multiple concurrent reads)
    - **Bank**: Simpler, good for balanced read/write workloads

4. **Thread Safety Guarantees**
    - Both implementations prevent overdrafts
    - Both prevent race conditions
    - Both support concurrent operations safely
    - BankReadWrite provides better read concurrency

5. **Lock Ordering**
    - Transfer operations lock accounts in order to prevent circular deadlocks
    - Individual account locks are sufficient due to per-account synchronization
