# Performance Tests Quick Reference

## 📋 Test Files Created

```
✅ BankPerformanceTest.java (645 lines)
   Location: src/test/java/com/aykacltd/cone/BankPerformanceTest.java
   
✅ BankReadWritePerformanceTest.java (735 lines)
   Location: src/test/java/com/aykacltd/cone/BankReadWritePerformanceTest.java
```

## 🚀 Quick Start

### Run All Performance Tests

```bash
mvn test -Dtest="BankPerformanceTest,BankReadWritePerformanceTest"
```

### Run Bank Performance Tests Only

```bash
mvn test -Dtest=BankPerformanceTest
```

### Run BankReadWrite Performance Tests Only

```bash
mvn test -Dtest=BankReadWritePerformanceTest
```

### Run Specific Test Category

```bash
# Single-threaded tests
mvn test -Dtest=BankPerformanceTest$SingleThreadedPerformance

# Multi-threaded tests
mvn test -Dtest=BankPerformanceTest$MultiThreadedPerformance

# Scalability tests
mvn test -Dtest=BankPerformanceTest$ScalabilityTests
```

## 📊 Test Categories

### BankPerformanceTest (ReentrantLock)

| Category        | Tests | Focus                 |
|-----------------|-------|-----------------------|
| Single Threaded | 3     | Individual op timing  |
| Multi Threaded  | 5     | Concurrent throughput |
| Scalability     | 1     | Thread scaling        |

### BankReadWritePerformanceTest (ReentrantReadWriteLock)

| Category        | Tests | Focus                 |
|-----------------|-------|-----------------------|
| Single Threaded | 3     | Individual op timing  |
| Multi Threaded  | 5     | Concurrent throughput |
| Scalability     | 2     | Thread & read scaling |

## 📈 Metrics Measured

### Single Threaded

- Average operation time (nanoseconds)
- Average operation time (microseconds)
- Total execution time (milliseconds)
- Minimum operation time
- Maximum operation time

### Concurrent

- Total operations completed
- Total execution time (milliseconds)
- Average time per operation (microseconds)
- **Operations per second (throughput)**
- Thread count
- Operations per thread

### Scalability

- Performance with 1, 5, 10, 20, 50 threads
- Throughput improvement analysis
- Scaling efficiency metrics

## 🎯 What Gets Tested

### Operations Measured

- ✅ **Deposit** - 10,000 single-threaded iterations
- ✅ **Withdrawal** - 10,000 single-threaded iterations
- ✅ **Transfer** - 10,000 single-threaded iterations
- ✅ **Concurrent Deposits** - 10 threads × 1000 ops
- ✅ **Concurrent Withdrawals** - 10 threads × 500 ops
- ✅ **Concurrent Transfers** - 10 threads × 500 ops
- ✅ **Mixed Operations** - 20 threads × 500 ops
- ✅ **Read-Heavy** (BankReadWrite only) - 20 threads × 500 reads

## 💡 Key Differences

### BankPerformanceTest Features

- Tests ReentrantLock implementation
- Single lock per account
- All operations serialized
- Good baseline for comparison

### BankReadWritePerformanceTest Features

- Tests ReentrantReadWriteLock implementation
- Separate read/write locks
- Multiple readers can proceed in parallel
- Includes read-heavy workload test
- Includes read concurrency scaling test

## 📊 Expected Results

### Single Threaded (Typical)

```
Deposit:   ~2-5 microseconds per operation
Withdraw:  ~2-5 microseconds per operation
Transfer:  ~3-8 microseconds per operation
```

### Concurrent Throughput (Typical)

```
Bank Deposits:      10,000-50,000 ops/sec
Bank Transfers:     5,000-20,000 ops/sec

BankReadWrite Deposits:      10,000-50,000 ops/sec
BankReadWrite Transfers:     5,000-20,000 ops/sec
BankReadWrite Reads:         20,000-100,000 ops/sec (much higher!)
```

## 🔍 Understanding Output

### Example Single-Threaded Output

```
=== BANK DEPOSIT PERFORMANCE ===
Iterations: 10000
Average Time: 2.345 microseconds
Average Time: 2345 nanoseconds
Total Time: 23 ms
Min Time: 1200 ns
Max Time: 18900 ns
```

### Example Concurrent Output

```
=== BANK CONCURRENT DEPOSIT PERFORMANCE ===
Threads: 10
Deposits per Thread: 1000
Total Operations: 10000
Total Time: 456 ms
Average Time per Operation: 0.045 microseconds
Operations per Second: 21929
```

### Example Scalability Output

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
```

## ✅ Performance Assertions

### BankPerformanceTest Passes If

- Single op time < 100 microseconds (deposit/withdraw)
- Single op time < 200 microseconds (transfer)
- Concurrent throughput > 10,000 ops/sec (deposits)
- Concurrent throughput > 5,000 ops/sec (withdrawals/transfers)

### BankReadWritePerformanceTest Passes If

- Single op time < 100 microseconds (deposit/withdraw)
- Single op time < 200 microseconds (transfer)
- Concurrent throughput > 10,000 ops/sec (deposits)
- Concurrent throughput > 5,000 ops/sec (withdrawals/transfers)
- Read-heavy throughput > 20,000 ops/sec ⭐ (advantage of RWLock)

## 🔄 Comparing Results

### Key Comparison Points

1. **Single Threaded**
    - Both should be similar
    - Bank might be slightly faster (less lock overhead)

2. **Concurrent Writes**
    - Performance should be similar
    - Both use exclusive locks

3. **Read-Heavy (BankReadWrite advantage)**
    - BankReadWrite should show **much higher throughput**
    - Multiple readers proceed in parallel
    - Bank serializes all reads

4. **Scalability**
    - BankReadWrite should maintain throughput better with reads
    - Bank should remain consistent

## 🧮 Calculation Examples

### Converting Nanoseconds to Microseconds

```
microseconds = nanoseconds / 1000
Example: 2345 ns = 2.345 μs
```

### Calculating Operations Per Second

```
OPS/SEC = (Total Ops / Total Time in ms) × 1000
Example: 10000 ops / 456 ms = 21,929 OPS/SEC
```

### Time Per Operation

```
Time Per Op (μs) = (Total Time in ms × 1000) / Total Ops
Example: (456 ms × 1000) / 10000 ops = 0.045 μs per op
```

## 📝 Test Configuration

### Iterations & Threads

- Single-threaded: 10,000 iterations
- Multi-threaded: 500-1,000 per thread, 10-20 threads
- Scalability: 1,000 per thread, varying thread counts

### Initial Balances

- 100,000 per account (sufficient for all tests)
- 5 accounts total

### Operation Parameters

- Deposit/Withdraw amount: 1 unit
- Transfer amount: 1 unit
- All operations are minimal to measure lock overhead

## 📚 Related Documentation

- `PERFORMANCE_TESTS_DOCUMENTATION.md` - Complete detailed guide
- `BankTest.java` - Functional tests for Bank
- `BankReadWriteTest.java` - Functional tests for BankReadWrite

## 🎯 Best Practices

1. **Run multiple times** - Averages out JVM warmup effects
2. **Close other apps** - Reduces CPU contention
3. **Consistent environment** - Same time of day, same conditions
4. **Warm-up first** - Let JVM optimize before actual test
5. **Large samples** - More iterations = better statistics

## 🏆 Performance Benchmarking Tips

### To get accurate benchmarks:

1. Run test 3-5 times and average the results
2. Discard first run (JVM warmup)
3. Monitor system resources during test
4. Compare same conditions for both implementations
5. Use the ops/sec metric for easy comparison

### Watch for:

- Variance in times (large min/max difference)
- Throughput drop with more threads (lock contention)
- Reads much faster than writes (expected)
- Transfer slowest (involves two account locks)

## 📞 Quick Commands Reference

```bash
# Build and test
mvn clean test

# Run all performance tests
mvn test -Dtest="BankPerformanceTest,BankReadWritePerformanceTest"

# Run with specific pattern
mvn test -Dtest=Bank*Performance*

# Run and skip other tests
mvn test -Dtest=BankPerformanceTest -DfailIfNoTests=false

# Show system info before tests
echo "=== System Info ===" && system_profiler SPHardwareDataType && mvn test -Dtest=BankPerformanceTest
```

---

**Created**: 2026-02-27
**Status**: ✅ Ready to Use
**Files**: 2 comprehensive performance test classes
**Total Tests**: 14 performance tests
**Metrics**: Comprehensive timing and throughput analysis
