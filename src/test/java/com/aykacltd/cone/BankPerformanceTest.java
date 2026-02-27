package com.aykacltd.cone;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Bank Performance/Load Tests")
class BankPerformanceTest {

  private Bank bank;

  @BeforeEach
  void setUp() {
    bank = new Bank(new long[] {100000, 100000, 100000, 100000, 100000});
  }

  @Nested
  @DisplayName("Single Threaded Performance")
  class SingleThreadedPerformance {

    @Test
    @DisplayName("Measure average deposit time")
    void measureAverageDepositTime() {
      int iterations = 10000;
      long[] times = new long[iterations];

      for (int i = 0; i < iterations; i++) {
        long startTime = System.nanoTime();
        bank.deposit(1, 1);
        long endTime = System.nanoTime();
        times[i] = endTime - startTime;
      }

      long totalTime = 0;
      for (long time : times) {
        totalTime += time;
      }
      double averageTimeNanos = (double) totalTime / iterations;
      double averageTimeMicros = averageTimeNanos / 1000;

      System.out.println("\n=== BANK DEPOSIT PERFORMANCE ===");
      System.out.println("Iterations: " + iterations);
      System.out.println(
          "Average Time: " + String.format("%.3f", averageTimeMicros) + " microseconds");
      System.out.println(
          "Average Time: " + String.format("%.0f", averageTimeNanos) + " nanoseconds");
      System.out.println("Total Time: " + (totalTime / 1_000_000) + " ms");
      System.out.println("Min Time: " + findMin(times) + " ns");
      System.out.println("Max Time: " + findMax(times) + " ns");
      System.out.println("================================\n");

      assertTrue(averageTimeMicros < 100, "Average deposit should be less than 100 microseconds");
    }

    @Test
    @DisplayName("Measure average withdrawal time")
    void measureAverageWithdrawalTime() {
      int iterations = 10000;
      long[] times = new long[iterations];

      for (int i = 0; i < iterations; i++) {
        long startTime = System.nanoTime();
        bank.withdraw(1, 1);
        long endTime = System.nanoTime();
        times[i] = endTime - startTime;
      }

      long totalTime = 0;
      for (long time : times) {
        totalTime += time;
      }
      double averageTimeNanos = (double) totalTime / iterations;
      double averageTimeMicros = averageTimeNanos / 1000;

      System.out.println("\n=== BANK WITHDRAWAL PERFORMANCE ===");
      System.out.println("Iterations: " + iterations);
      System.out.println(
          "Average Time: " + String.format("%.3f", averageTimeMicros) + " microseconds");
      System.out.println(
          "Average Time: " + String.format("%.0f", averageTimeNanos) + " nanoseconds");
      System.out.println("Total Time: " + (totalTime / 1_000_000) + " ms");
      System.out.println("Min Time: " + findMin(times) + " ns");
      System.out.println("Max Time: " + findMax(times) + " ns");
      System.out.println("====================================\n");

      assertTrue(averageTimeMicros < 100,
          "Average withdrawal should be less than 100 microseconds");
    }

    @Test
    @DisplayName("Measure average transfer time")
    void measureAverageTransferTime() {
      int iterations = 10000;
      long[] times = new long[iterations];

      for (int i = 0; i < iterations; i++) {
        long startTime = System.nanoTime();
        bank.transfer(1, 2, 1);
        long endTime = System.nanoTime();
        times[i] = endTime - startTime;
      }

      long totalTime = 0;
      for (long time : times) {
        totalTime += time;
      }
      double averageTimeNanos = (double) totalTime / iterations;
      double averageTimeMicros = averageTimeNanos / 1000;

      System.out.println("\n=== BANK TRANSFER PERFORMANCE ===");
      System.out.println("Iterations: " + iterations);
      System.out.println(
          "Average Time: " + String.format("%.3f", averageTimeMicros) + " microseconds");
      System.out.println(
          "Average Time: " + String.format("%.0f", averageTimeNanos) + " nanoseconds");
      System.out.println("Total Time: " + (totalTime / 1_000_000) + " ms");
      System.out.println("Min Time: " + findMin(times) + " ns");
      System.out.println("Max Time: " + findMax(times) + " ns");
      System.out.println("==================================\n");

      assertTrue(averageTimeMicros < 200, "Average transfer should be less than 200 microseconds");
    }
  }

  @Nested
  @DisplayName("Multi Threaded Performance")
  class MultiThreadedPerformance {

    @Test
    @DisplayName("Measure concurrent deposit throughput")
    void measureConcurrentDepositThroughput() throws InterruptedException {
      int numThreads = 10;
      int depositsPerThread = 1000;

      long startTime = System.nanoTime();

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < depositsPerThread; i++) {
                bank.deposit(1, 1);
              }
            } finally {
              latch.countDown();
            }
          });
        }

        latch.await();
      }

      long endTime = System.nanoTime();
      long totalTimeNanos = endTime - startTime;
      long totalTimeMs = totalTimeNanos / 1_000_000;
      int totalOperations = numThreads * depositsPerThread;
      double timePerOpMicros = (double) totalTimeNanos / totalOperations / 1000;
      double opsPerSecond = (double) totalOperations / totalTimeMs * 1000;

      System.out.println("\n=== BANK CONCURRENT DEPOSIT PERFORMANCE ===");
      System.out.println("Threads: " + numThreads);
      System.out.println("Deposits per Thread: " + depositsPerThread);
      System.out.println("Total Operations: " + totalOperations);
      System.out.println("Total Time: " + totalTimeMs + " ms");
      System.out.println("Average Time per Operation: " + String.format("%.3f", timePerOpMicros) +
          " microseconds");
      System.out.println("Operations per Second: " + String.format("%.0f", opsPerSecond));
      System.out.println("==========================================\n");

      assertTrue(opsPerSecond > 10000, "Should handle at least 10,000 ops/sec");
    }

    @Test
    @DisplayName("Measure concurrent withdrawal throughput")
    void measureConcurrentWithdrawalThroughput() throws InterruptedException {
      int numThreads = 10;
      int withdrawalsPerThread = 500; // Less due to limited initial balance

      long startTime = System.nanoTime();

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          executor.submit(() -> {
            try {
              for (int i = 0; i < withdrawalsPerThread; i++) {
                bank.withdraw(1, 1);
              }
            } finally {
              latch.countDown();
            }
          });
        }

        latch.await();
      }

      long endTime = System.nanoTime();
      long totalTimeNanos = endTime - startTime;
      long totalTimeMs = totalTimeNanos / 1_000_000;
      int totalOperations = numThreads * withdrawalsPerThread;
      double timePerOpMicros = (double) totalTimeNanos / totalOperations / 1000;
      double opsPerSecond = (double) totalOperations / totalTimeMs * 1000;

      System.out.println("\n=== BANK CONCURRENT WITHDRAWAL PERFORMANCE ===");
      System.out.println("Threads: " + numThreads);
      System.out.println("Withdrawals per Thread: " + withdrawalsPerThread);
      System.out.println("Total Operations: " + totalOperations);
      System.out.println("Total Time: " + totalTimeMs + " ms");
      System.out.println("Average Time per Operation: " + String.format("%.3f", timePerOpMicros) +
          " microseconds");
      System.out.println("Operations per Second: " + String.format("%.0f", opsPerSecond));
      System.out.println("==============================================\n");

      assertTrue(opsPerSecond > 5000, "Should handle at least 5,000 ops/sec");
    }

    @Test
    @DisplayName("Measure concurrent transfer throughput")
    void measureConcurrentTransferThroughput() throws InterruptedException {
      int numThreads = 10;
      int transfersPerThread = 500;

      long startTime = System.nanoTime();

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < transfersPerThread; i++) {
                int fromAccount = (threadId % 5) + 1;
                int toAccount = ((threadId + 1) % 5) + 1;
                if (fromAccount != toAccount) {
                  bank.transfer(fromAccount, toAccount, 1);
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        latch.await();
      }

      long endTime = System.nanoTime();
      long totalTimeNanos = endTime - startTime;
      long totalTimeMs = totalTimeNanos / 1_000_000;
      int totalOperations = numThreads * transfersPerThread;
      double timePerOpMicros = (double) totalTimeNanos / totalOperations / 1000;
      double opsPerSecond = (double) totalOperations / totalTimeMs * 1000;

      System.out.println("\n=== BANK CONCURRENT TRANSFER PERFORMANCE ===");
      System.out.println("Threads: " + numThreads);
      System.out.println("Transfers per Thread: " + transfersPerThread);
      System.out.println("Total Operations: " + totalOperations);
      System.out.println("Total Time: " + totalTimeMs + " ms");
      System.out.println("Average Time per Operation: " + String.format("%.3f", timePerOpMicros) +
          " microseconds");
      System.out.println("Operations per Second: " + String.format("%.0f", opsPerSecond));
      System.out.println("===========================================\n");

      assertTrue(opsPerSecond > 5000, "Should handle at least 5,000 ops/sec");
    }

    @Test
    @DisplayName("Measure mixed operations throughput")
    void measureMixedOperationsThroughput() throws InterruptedException {
      int numThreads = 20;
      int operationsPerThread = 500;

      long startTime = System.nanoTime();

      try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int t = 0; t < numThreads; t++) {
          final int threadId = t;
          executor.submit(() -> {
            try {
              for (int i = 0; i < operationsPerThread; i++) {
                int operation = i % 3;
                int account = (threadId % 5) + 1;
                int targetAccount = ((threadId + 1) % 5) + 1;

                switch (operation) {
                  case 0:
                    bank.deposit(account, 1);
                    break;
                  case 1:
                    bank.withdraw(account, 1);
                    break;
                  case 2:
                    if (account != targetAccount) {
                      bank.transfer(account, targetAccount, 1);
                    }
                    break;
                }
              }
            } finally {
              latch.countDown();
            }
          });
        }

        latch.await();
      }

      long endTime = System.nanoTime();
      long totalTimeNanos = endTime - startTime;
      long totalTimeMs = totalTimeNanos / 1_000_000;
      int totalOperations = numThreads * operationsPerThread;
      double timePerOpMicros = (double) totalTimeNanos / totalOperations / 1000;
      double opsPerSecond = (double) totalOperations / totalTimeMs * 1000;

      System.out.println("\n=== BANK MIXED OPERATIONS PERFORMANCE ===");
      System.out.println("Threads: " + numThreads);
      System.out.println("Operations per Thread: " + operationsPerThread);
      System.out.println("Total Operations: " + totalOperations);
      System.out.println("Total Time: " + totalTimeMs + " ms");
      System.out.println("Average Time per Operation: " + String.format("%.3f", timePerOpMicros) +
          " microseconds");
      System.out.println("Operations per Second: " + String.format("%.0f", opsPerSecond));
      System.out.println("=========================================\n");

      assertTrue(opsPerSecond > 10000,
          "Should handle at least 10,000 ops/sec for mixed operations");
    }
  }

  @Nested
  @DisplayName("Scalability Tests")
  class ScalabilityTests {

    @Test
    @DisplayName("Measure performance scaling with increasing threads")
    void measureScalingWithThreads() throws InterruptedException {
      int[] threadCounts = {1, 5, 10, 20, 50};
      int operationsPerThread = 1000;

      System.out.println("\n=== BANK SCALABILITY WITH THREADS ===");
      System.out.println("Operations per Thread: " + operationsPerThread);
      System.out.println("Thread Count | Total Time (ms) | Avg Time (μs) | Ops/sec");
      System.out.println("-------------|-----------------|---------------|----------");

      for (int numThreads : threadCounts) {
        long startTime = System.nanoTime();

        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
          CountDownLatch latch = new CountDownLatch(numThreads);

          for (int t = 0; t < numThreads; t++) {
            executor.submit(() -> {
              try {
                for (int i = 0; i < operationsPerThread; i++) {
                  bank.deposit(1, 1);
                }
              } finally {
                latch.countDown();
              }
            });
          }

          latch.await();
        }

        long endTime = System.nanoTime();
        long totalTimeNanos = endTime - startTime;
        long totalTimeMs = totalTimeNanos / 1_000_000;
        int totalOperations = numThreads * operationsPerThread;
        double timePerOpMicros = (double) totalTimeNanos / totalOperations / 1000;
        double opsPerSecond = (double) totalOperations / totalTimeMs * 1000;

        System.out.printf("%-12d | %-15d | %-13.3f | %.0f%n", numThreads, totalTimeMs,
            timePerOpMicros, opsPerSecond);
      }

      System.out.println("======================================\n");
    }
  }

  // Helper methods
  private long findMin(long[] times) {
    long min = times[0];
    for (long time : times) {
      if (time < min) {
        min = time;
      }
    }
    return min;
  }

  private long findMax(long[] times) {
    long max = times[0];
    for (long time : times) {
      if (time > max) {
        max = time;
      }
    }
    return max;
  }
}
