package com.aykacltd.prefixsum;

import java.util.Arrays;

/**
 * 1D Prefix Sum (also known as Cumulative Sum).
 *
 * <p>Builds a prefix table in O(n) so that any range sum query [left, right]
 * can be answered in O(1) using the formula:
 * <pre>
 *   rangeSum(l, r) = prefix[r + 1] - prefix[l]
 * </pre>
 *
 * <p>The internal array uses a 1-based sentinel at index 0 (always 0) to avoid
 * boundary checks and simplify the subtraction formula.
 *
 * <pre>
 * Example:
 *   arr    = [3, 1, 4, 1, 5, 9, 2, 6]
 *   prefix = [0, 3, 4, 8, 9,14,23,25,31]
 *
 *   rangeSum(2, 5) = prefix[6] - prefix[2] = 23 - 4 = 19  (4+1+5+9)
 * </pre>
 *
 * <p>Time complexity: Build O(n), Query O(1), Space O(n).
 */
public class PrefixSum1D {

  private final int[] prefix;
  private final int n;

  /**
   * Constructs the prefix sum table from the given array.
   *
   * @param arr the source array; must not be {@code null} and must not be empty
   * @throws IllegalArgumentException if {@code arr} is null or empty
   */
  public PrefixSum1D(int[] arr) {
    if (arr == null) {
      throw new IllegalArgumentException("Input array must not be null.");
    }
    if (arr.length == 0) {
      throw new IllegalArgumentException("Input array must not be empty.");
    }

    this.n = arr.length;
    this.prefix = new int[n + 1]; // prefix[0] = 0 (sentinel)

    for (int i = 0; i < n; i++) {
      prefix[i + 1] = prefix[i] + arr[i];
    }
  }

  /**
   * Returns the sum of elements in the inclusive range {@code [left, right]}.
   *
   * @param left  the start index (inclusive), 0-based
   * @param right the end index (inclusive), 0-based
   * @return sum of {@code arr[left] + arr[left+1] + ... + arr[right]}
   * @throws IllegalArgumentException if indices are out of bounds or {@code left > right}
   */
  public int rangeSum(int left, int right) {
    validateRange(left, right);
    return prefix[right + 1] - prefix[left];
  }

  /**
   * Returns the total sum of all elements.
   *
   * @return sum of every element in the original array
   */
  public int totalSum() {
    return prefix[n];
  }

  /**
   * Returns the sum of elements from index 0 to {@code right} (inclusive).
   *
   * @param right the end index (inclusive), 0-based
   * @return prefix sum up to {@code right}
   * @throws IllegalArgumentException if {@code right} is out of bounds
   */
  public int prefixSumUpTo(int right) {
    return rangeSum(0, right);
  }

  /**
   * Returns the length of the original array.
   */
  public int size() {
    return n;
  }

  /**
   * Returns a defensive copy of the internal prefix array (length = n+1).
   */
  public int[] getPrefixArray() {
    return Arrays.copyOf(prefix, prefix.length);
  }

  // -------------------------------------------------------------------------
  // Private helpers
  // -------------------------------------------------------------------------

  private void validateRange(int left, int right) {
    if (left < 0 || right >= n) {
      throw new IllegalArgumentException(
          String.format("Indices out of bounds for array of size %d: left=%d, right=%d", n, left,
              right));
    }
    if (left > right) {
      throw new IllegalArgumentException(
          String.format("left (%d) must be <= right (%d)", left, right));
    }
  }

  @Override
  public String toString() {
    return "PrefixSum1D{n=" + n + ", prefix=" + Arrays.toString(prefix) + "}";
  }
}
