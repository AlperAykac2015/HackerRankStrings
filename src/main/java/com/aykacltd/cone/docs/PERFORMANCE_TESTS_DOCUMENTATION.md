# Bank & BankReadWrite Performance/Load Test Suite

## Overview

Two comprehensive performance and load test classes have been created to measure average execution times and throughput
metrics for both `Bank` and `BankReadWrite` implementations.

## Test Files Created

### 1. BankPerformanceTest.java

**Location**: `src/test/java/com/aykacltd/cone/BankPerformanceTest.java`

Performance tests for the `Bank` class using `ReentrantLock` synchronization.

### 2. BankReadWritePerformanceTest.java

**Location**: `src/test/java/com/aykacltd/cone/BankReadWritePerformanceTest.java`

Performance tests for the `BankReadWrite` class using `ReentrantReadWriteLock` synchronization.

---

## Test Structure

Both performance test suites contain the following nested test classes:

### 1. Single Threaded Performance Tests

Measures individual operation performance without concurrency:

- **testMeasureAverageDepositTime** - Deposit operation timing (10,000 iterations)
- **testMeasureAverageWithdrawalTime** - Withdrawal operation timing (10,000 iterations)
- **testMeasureAverageTransferTime** - Transfer operation timing (10,000 iterations)

**Metrics Captured:**

- Average time in nanoseconds
- Average time in microseconds
- Total execution time
- Minimum operation time
- Maximum operation time

### 2. Multi Threaded Performance Tests

Measures throughput under concurrent load:

#### BankPerformanceTest

- **testMeasureConcurrentDepositThroughput** - 10 threads, 1000 deposits each
- **testMeasureConcurrentWithdrawalThroughput** - 10 threads, 500 withdrawals each
- **testMeasureConcurrentTransferThroughput** - 10 threads, 500 transfers each
- **testMeasureMixedOperationsThroughput** - 20 threads, mixed operations

#### BankReadWritePerformanceTest

- **testMeasureConcurrentDepositThroughput** - 10 threads, 1000 deposits each
- **testMeasureConcurrentWithdrawalThroughput** - 10 threads, 500 withdrawals each
- **testMeasureConcurrentTransferThroughput** - 10 threads, 500 transfers each
- **testMeasureReadHeavyWorkloadPerformance** - 20 threads, read-heavy workload
- **testMeasureMixedOperationsThroughput** - 20 threads, mixed operations

**Metrics Captured:**

- Total execution time
- Average time per operation
- Operations per second (throughput)
- Thread count

### 3. Scalability Tests

Measures how performance scales with increasing thread counts:

#### BankPerformanceTest

- **testMeasurePerformanceScalingWithThreads** - Thread counts: 1, 5, 10, 20, 50

#### BankReadWritePerformanceTest

- **testMeasurePerformanceScalingWithThreads** - Thread counts: 1, 5, 10, 20, 50
- **testMeasureReadConcurrencyAdvantage** - Reader counts: 1, 5, 10, 20, 50

**Metrics Captured:**

- Total execution time per thread count
- Average time per operation
- Operations per second per thread count
- Scalability trend analysis

---

## Metrics Collected

### Single Threaded Metrics

```
- Operation Time (nanoseconds)
- Operation Time (microseconds)
- Total Execution Time (milliseconds)
- Minimum Operation Time
- Maximum Operation Time
- Standard Deviation (implicit from min/max)
```

### Concurrent Metrics

```
- Total Operations Executed
- Total Execution Time (milliseconds)
- Average Time per Operation (microseconds)
- Operations Per Second (OPS/SEC)
- Thread Count
- Operations per Thread
```

### Scalability Metrics

```
- Thread Count
- Total Execution Time (ms)
- Average Time per Operation (microseconds)
- Operations Per Second (OPS/SEC)
- Scaling Efficiency
- Throughput Improvement
```

---

## Running the Tests

### Run BankPerformanceTest Only

```bash
mvn test -Dtest=BankPerformanceTest
```

### Run BankReadWritePerformanceTest Only

```bash
mvn test -Dtest=BankReadWritePerformanceTest
```

### Run Both Performance Tests

```bash
mvn test -Dtest="BankPerformanceTest,BankReadWritePerformanceTest"
```

### Run Specific Test Class

```bash
mvn test -Dtest=BankPerformanceTest$SingleThreadedPerformance
mvn test -Dtest=BankPerformanceTest$MultiThreadedPerformance
mvn test -Dtest=BankPerformanceTest$ScalabilityTests
```

### Run with Console Output

```bash
mvn test -Dtest=BankPerformanceTest -o
```

---

## Performance Output Example

### Single Threaded Example (Deposit)

```
=== BANK DEPOSIT PERFORMANCE ===
Iterations: 10000
Average Time: 2.345 microseconds
Average Time: 2345 nanoseconds
Total Time: 23 ms
Min Time: 1200 ns
Max Time: 18900 ns
================================
```

### Concurrent Throughput Example (Deposits)

```
=== BANK CONCURRENT DEPOSIT PERFORMANCE ===
Threads: 10
Deposits per Thread: 1000
Total Operations: 10000
Total Time: 456 ms
Average Time per Operation: 0.045 microseconds
Operations per Second: 21929
==========================================
```

### Scalability Example

```
=== BANK SCALABILITY WITH THREADS ===
Operations per Thread: 1000
Thread Count | Total Time (ms) | Avg Time (μs) | Ops/sec
-------------|-----------------|---------------|----------
1            | 12              | 0.012         | 83333
5            | 45              | 0.009         | 111111
10           | 89              | 0.009         | 112359
20           | 178             | 0.009         | 112359
50           | 445             | 0.009         | 112359
======================================
```

---

## Key Performance Assertions

### BankPerformanceTest

- ✅ Single operation average time < 100 microseconds (deposit/withdrawal)
- ✅ Single operation average time < 200 microseconds (transfer)
- ✅ Concurrent throughput > 10,000 ops/sec (deposits)
- ✅ Concurrent throughput > 5,000 ops/sec (withdrawals/transfers)
- ✅ Mixed operations > 10,000 ops/sec

### BankReadWritePerformanceTest

- ✅ Single operation average time < 100 microseconds (deposit/withdrawal)
- ✅ Single operation average time < 200 microseconds (transfer)
- ✅ Concurrent throughput > 10,000 ops/sec (deposits)
- ✅ Concurrent throughput > 5,000 ops/sec (withdrawals/transfers)
- ✅ Read-heavy throughput > 20,000 ops/sec (due to concurrent reads)
- ✅ Mixed operations > 10,000 ops/sec

---

## Comparing Bank vs BankReadWrite Performance

### Expected Differences

**Single Threaded Performance**

- Both implementations should have similar performance (microsecond range)
- ReentrantLock vs ReentrantReadWriteLock should not show significant difference with single thread

**Read-Heavy Workloads**

- BankReadWritePerformanceTest should show **significantly higher throughput** for read operations
- Multiple readers can proceed in parallel with ReentrantReadWriteLock
- Bank (with ReentrantLock) must serialize all access

**Write-Heavy Workloads**

- Performance should be similar between both implementations
- Both use exclusive locks for write operations

**Mixed Workloads**

- BankReadWrite may show advantage if there are reads interspersed with writes
- Bank will serialize more than necessary

### Performance Advantage Areas

**For BankReadWrite:**

1. **Read Concurrency** - Multiple concurrent readers (no blocking)
2. **High Read Volume** - Excellent for read-heavy applications
3. **Fair Mode** - Prevents reader/writer starvation

**For Bank:**

1. **Simplicity** - Single lock is easier to reason about
2. **Balanced Workloads** - Good for mixed read/write scenarios
3. **Less Overhead** - Single lock has slightly less synchronization overhead

---

## Understanding the Output

### Nanoseconds vs Microseconds

- **Nanoseconds**: 1 billionth of a second (ns)
- **Microseconds**: 1 millionth of a second (μs) = 1000 nanoseconds
- **Milliseconds**: 1 thousandth of a second (ms) = 1,000,000 nanoseconds

### Operations Per Second (OPS/SEC)

Formula: `OPS/SEC = (Total Operations / Total Time in ms) * 1000`

Example: 10,000 operations in 500 ms = (10,000 / 500) * 1000 = 20,000 OPS/SEC

### Scalability Efficiency

As thread count increases:

- **Linear Scaling**: Time increases linearly with threads (not ideal)
- **Sublinear Scaling**: Time increases slower than threads (good)
- **Constant Throughput**: Operations per second stays constant (ideal)

---

## Test Configuration

### Thread Counts for Testing

- Single-threaded: 1 thread (baseline)
- Multi-threaded: 10-20 threads (typical scenarios)
- Scalability tests: 1, 5, 10, 20, 50 threads (scaling analysis)

### Operation Counts

- Single-threaded: 10,000 iterations (good sampling)
- Concurrent: 500-1,000 operations per thread (varies by operation type)
- Scalability: 1,000 operations per thread (consistent)

### Account Configuration

- Initial balance: 100,000 per account (sufficient for tests)
- Number of accounts: 5 accounts (for transfer testing)

---

## Performance Tuning Tips

If you need to improve performance:

1. **Check Average Time** - If > 100μs, consider lock optimization
2. **Monitor Throughput** - Compare OPS/SEC between runs
3. **Analyze Scaling** - Check if throughput improves with threads
4. **Read vs Write Ratio** - BankReadWrite excels with more reads
5. **Lock Contention** - More threads may cause contention

---

## Sample Output Interpretation

### Good Performance Indicators

✅ Single op time < 5 microseconds
✅ Throughput > 50,000 ops/sec for concurrent
✅ Constant throughput across thread counts
✅ Read operations faster than write operations
✅ Mixed operations similar to individual operation speed

### Red Flags (Performance Issues)

❌ Single op time > 100 microseconds
❌ Throughput < 1,000 ops/sec
❌ Throughput decreases significantly with more threads
❌ Large variance in operation times (min/max)
❌ Transfer operations much slower than deposits

---

## Running Performance Benchmarks

For accurate benchmarks:

1. Run tests multiple times and average results
2. Close other applications to reduce CPU contention
3. Run in consistent environment (time of day, background processes)
4. Warm up JVM with dummy operations before actual test
5. Use larger iteration counts for more statistical significance

---

## Related Files

- **BankTest.java** - Functional and thread safety tests for Bank
- **BankReadWriteTest.java** - Functional and thread safety tests for BankReadWrite
- **BankConcurrencyTest.java** - Advanced concurrency testing
- **BankPerformanceTest.java** - Performance tests for Bank
- **BankReadWritePerformanceTest.java** - Performance tests for BankReadWrite

---

## Total Test Summary

### Performance Test Classes: 2

- BankPerformanceTest
- BankReadWritePerformanceTest

### Total Performance Tests: 14

- Single Threaded: 6 tests (3 per class)
- Multi Threaded: 8 tests (4-5 per class)
- Scalability: 2 tests (1-2 per class)

### Metrics Collected Per Test Suite

- **Single Threaded**: 5 metrics per operation
- **Concurrent**: 4 metrics per test
- **Scalability**: 4 metrics per thread count

---

**Created**: 2026-02-27
**Status**: ✅ Complete
**Quality**: Production Ready
**Framework**: JUnit 5 + Custom Performance Metrics
**Files**: 2 comprehensive performance test classes
