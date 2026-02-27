# BankTest - Thread Safety Test Suite

## Overview

Comprehensive unit test suite for the `Bank` class that verifies thread safety and concurrent operation handling.

## Test Statistics

- **Total Tests**: 16
- **Test Classes**: 5 nested test classes
- **Coverage Areas**: Basic functionality, concurrent operations, race conditions, deadlock prevention

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

### 2. ConcurrentDepositsTests (1 test)

Tests concurrent deposit operations:

- `testConcurrentDeposits()` - Verifies 10 threads performing 100 deposits each to the same account

### 3. ConcurrentWithdrawalsTests (2 tests)

Tests concurrent withdrawal operations with various scenarios:

- `testConcurrentWithdrawals()` - Tests 10 threads withdrawing concurrently with sufficient funds
- `testConcurrentWithdrawalsPreventOverdraft()` - Verifies that overdrafts are prevented under high concurrency

### 4. ConcurrentTransfersTests (2 tests)

Tests concurrent transfer operations:

- `testConcurrentTransfersMultipleAccounts()` - Tests 20 threads transferring between multiple accounts
- `testCircularTransfers()` - Tests circular transfers (A→B→A) to detect deadlocks

### 5. MixedConcurrentOperationsTests (2 tests)

Tests combinations of concurrent operations:

- `testMixedOperations()` - Tests concurrent deposits, withdrawals, and transfers with 15 threads
- `testHighContentionConsistency()` - Tests consistency with high contention (50 threads, 50 operations each)

### 6. RaceConditionTests (2 tests)

Tests for specific race condition scenarios:

- `testBalanceRaceCondition()` - Tests simultaneous withdraw and deposit operations
- `testSimultaneousChecksAndUpdates()` - Tests simultaneous checks and updates (transfers to self)

## Key Testing Features

### Thread Safety Mechanisms Tested

1. **ConcurrentHashMap Usage** - Ensures atomic map operations
2. **ReentrantLock in Account** - Verifies lock-based synchronization
3. **Atomicity of Transfers** - Ensures transfers maintain consistency

### Concurrency Testing Techniques

1. **CountDownLatch** - Used for precise synchronization of test threads
2. **ExecutorService** - Used for thread pool management
3. **AtomicInteger** - Used for thread-safe counters
4. **Timeout Detection** - Tests include timeout to detect deadlocks

### Scenarios Covered

- Concurrent reads and writes
- High contention situations
- Overdraft prevention
- Deadlock detection
- Consistency under concurrent operations
- Race condition prevention

## Running the Tests

```bash
# Run all BankTest tests
mvn test -Dtest=BankTest

# Run specific test class
mvn test -Dtest=BankTest$BasicFunctionalityTests

# Run with verbose output
mvn test -Dtest=BankTest -X
```

## Test Results

All 16 tests pass successfully, confirming thread safety of the Bank implementation.

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

## Notes for Developers

1. The Bank class is thread-safe due to:
    - `ConcurrentHashMap` for atomic map operations
    - `ReentrantLock` in Account class for balance synchronization
    - Proper lock ordering to prevent deadlocks

2. The transfer operation, while checking both accounts, maintains thread safety through Account-level locking

3. Tests use generous timeouts (10 seconds) to account for system variations and allow detection of actual deadlocks

4. The test suite is designed to be platform-independent and work on different hardware configurations
