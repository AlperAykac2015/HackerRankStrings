package com.aykacltd.prefixsum;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive unit tests for {@link PrefixSum1D}.
 * <p>
 * Test categories:
 * 1. Construction – happy path and invalid inputs
 * 2. rangeSum     – typical ranges, edges, single elements
 * 3. totalSum     – convenience method
 * 4. prefixSumUpTo – convenience method
 * 5. Edge cases   – single-element array, all zeros, negatives, large values
 * 6. Immutability – mutating source array must not affect prefix table
 * 7. getPrefixArray – defensive copy behaviour
 */
@DisplayName("PrefixSum1D Tests")
class PrefixSum1DTest {

  // arr    = [3, 1, 4, 1, 5, 9, 2, 6]
  // prefix = [0, 3, 4, 8, 9,14,23,25,31]
  private static final int[] BASE_ARR = {3, 1, 4, 1, 5, 9, 2, 6};
  private PrefixSum1D ps;

  @BeforeEach
  void setUp() {
    ps = new PrefixSum1D(BASE_ARR);
  }

  // =========================================================================
  // 1. Construction
  // =========================================================================

  @Nested
  @DisplayName("Construction")
  class ConstructionTests {

    @Test
    @DisplayName("Builds correctly for standard array")
    void buildsCorrectPrefixTable() {
      int[] expected = {0, 3, 4, 8, 9, 14, 23, 25, 31};
      assertArrayEquals(expected, ps.getPrefixArray());
    }

    @Test
    @DisplayName("Reports correct size")
    void reportCorrectSize() {
      assertEquals(8, ps.size());
    }

    @Test
    @DisplayName("Throws on null input")
    void throwsOnNullInput() {
      IllegalArgumentException ex = assertThrows(
          IllegalArgumentException.class, () -> new PrefixSum1D(null));
      assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    @DisplayName("Throws on empty array")
    void throwsOnEmptyArray() {
      IllegalArgumentException ex = assertThrows(
          IllegalArgumentException.class, () -> new PrefixSum1D(new int[] {}));
      assertTrue(ex.getMessage().contains("empty"));
    }
  }

  // =========================================================================
  // 2. rangeSum – happy path
  // =========================================================================

  @Nested
  @DisplayName("rangeSum – happy path")
  class RangeSumHappyPath {

    @Test
    @DisplayName("Full range returns total sum")
    void fullRange() {
      assertEquals(31, ps.rangeSum(0, 7));
    }

    @Test
    @DisplayName("Middle range [2..5] = 4+1+5+9 = 19")
    void middleRange() {
      assertEquals(19, ps.rangeSum(2, 5));
    }

    @Test
    @DisplayName("First element only [0..0] = 3")
    void firstElementOnly() {
      assertEquals(3, ps.rangeSum(0, 0));
    }

    @Test
    @DisplayName("Last element only [7..7] = 6")
    void lastElementOnly() {
      assertEquals(6, ps.rangeSum(7, 7));
    }

    @Test
    @DisplayName("Two adjacent elements [3..4] = 1+5 = 6")
    void twoAdjacentElements() {
      assertEquals(6, ps.rangeSum(3, 4));
    }

    @Test
    @DisplayName("First half [0..3] = 3+1+4+1 = 9")
    void firstHalf() {
      assertEquals(9, ps.rangeSum(0, 3));
    }

    @Test
    @DisplayName("Second half [4..7] = 5+9+2+6 = 22")
    void secondHalf() {
      assertEquals(22, ps.rangeSum(4, 7));
    }

    @Test
    @DisplayName("Consistency: sum of halves equals total")
    void halvesAddUpToTotal() {
      assertEquals(ps.totalSum(), ps.rangeSum(0, 3) + ps.rangeSum(4, 7));
    }
  }

  // =========================================================================
  // 3. rangeSum – invalid inputs
  // =========================================================================

  @Nested
  @DisplayName("rangeSum – invalid inputs")
  class RangeSumInvalidInputs {

    @Test
    @DisplayName("Throws when left < 0")
    void throwsWhenLeftNegative() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(-1, 3));
    }

    @Test
    @DisplayName("Throws when right >= n")
    void throwsWhenRightTooLarge() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(0, 8));
    }

    @Test
    @DisplayName("Throws when left > right")
    void throwsWhenLeftGreaterThanRight() {
      IllegalArgumentException ex = assertThrows(
          IllegalArgumentException.class, () -> ps.rangeSum(5, 3));
      assertTrue(ex.getMessage().contains("left") && ex.getMessage().contains("right"));
    }

    @Test
    @DisplayName("Throws when both indices negative")
    void throwsWhenBothNegative() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(-2, -1));
    }
  }

  // =========================================================================
  // 4. totalSum
  // =========================================================================

  @Nested
  @DisplayName("totalSum")
  class TotalSumTests {

    @Test
    @DisplayName("Returns sum of all elements")
    void returnsCorrectTotal() {
      assertEquals(31, ps.totalSum());
    }

    @Test
    @DisplayName("totalSum == rangeSum(0, n-1)")
    void matchesRangeSum() {
      assertEquals(ps.rangeSum(0, ps.size() - 1), ps.totalSum());
    }
  }

  // =========================================================================
  // 5. prefixSumUpTo
  // =========================================================================

  @Nested
  @DisplayName("prefixSumUpTo")
  class PrefixSumUpToTests {

    @Test
    @DisplayName("prefixSumUpTo(0) = first element")
    void upToFirstElement() {
      assertEquals(3, ps.prefixSumUpTo(0));
    }

    @Test
    @DisplayName("prefixSumUpTo(4) = 3+1+4+1+5 = 14")
    void upToFifthElement() {
      assertEquals(14, ps.prefixSumUpTo(4));
    }

    @Test
    @DisplayName("prefixSumUpTo(n-1) == totalSum()")
    void upToLastElementMatchesTotal() {
      assertEquals(ps.totalSum(), ps.prefixSumUpTo(ps.size() - 1));
    }
  }

  // =========================================================================
  // 6. Edge cases
  // =========================================================================

  @Nested
  @DisplayName("Edge cases")
  class EdgeCases {

    @Test
    @DisplayName("Single-element array")
    void singleElementArray() {
      PrefixSum1D single = new PrefixSum1D(new int[] {42});
      assertEquals(1, single.size());
      assertEquals(42, single.rangeSum(0, 0));
      assertEquals(42, single.totalSum());
    }

    @Test
    @DisplayName("All-zero array")
    void allZeroArray() {
      PrefixSum1D zeros = new PrefixSum1D(new int[] {0, 0, 0, 0});
      assertEquals(0, zeros.rangeSum(0, 3));
      assertEquals(0, zeros.totalSum());
    }

    @Test
    @DisplayName("Array with negative values")
    void negativeValues() {
      // arr = [-5, 3, -2, 7]
      // prefix = [0, -5, -2, -4, 3]
      PrefixSum1D neg = new PrefixSum1D(new int[] {-5, 3, -2, 7});
      assertEquals(-5, neg.rangeSum(0, 0));
      assertEquals(-2, neg.rangeSum(0, 1));  // -5+3
      assertEquals(-4, neg.rangeSum(0, 2));  // -5+3-2
      assertEquals(3, neg.rangeSum(0, 3));  // -5+3-2+7
      assertEquals(5, neg.rangeSum(2, 3));  // -2+7
    }

    @Test
    @DisplayName("Array with mixed positive and negative values")
    void mixedValues() {
      PrefixSum1D mixed = new PrefixSum1D(new int[] {10, -10, 10, -10, 10});
      assertEquals(0, mixed.rangeSum(0, 1));
      assertEquals(10, mixed.rangeSum(0, 2));
      assertEquals(10, mixed.totalSum());
    }

    @Test
    @DisplayName("Large values do not overflow within int range")
    void largeValuesWithinIntRange() {
      // Each element ~500_000 so sum fits in int
      int[] large = {500_000, 400_000, 300_000, 200_000, 100_000};
      PrefixSum1D ps = new PrefixSum1D(large);
      assertEquals(1_500_000, ps.totalSum());
    }

    @Test
    @DisplayName("Two-element array boundary")
    void twoElementArray() {
      PrefixSum1D two = new PrefixSum1D(new int[] {7, 13});
      assertEquals(7, two.rangeSum(0, 0));
      assertEquals(13, two.rangeSum(1, 1));
      assertEquals(20, two.rangeSum(0, 1));
    }
  }

  // =========================================================================
  // 7. Immutability – source mutation should not affect prefix table
  // =========================================================================

  @Nested
  @DisplayName("Immutability")
  class ImmutabilityTests {

    @Test
    @DisplayName("Mutating source array does not change prefix sums")
    void sourceMutationDoesNotAffectPrefix() {
      int[] arr = {1, 2, 3, 4, 5};
      PrefixSum1D localPs = new PrefixSum1D(arr);

      int originalSum = localPs.totalSum(); // 15

      arr[0] = 999; // mutate source
      assertEquals(originalSum, localPs.totalSum(),
          "Prefix table must be independent of source array mutations");
    }

    @Test
    @DisplayName("Mutating returned prefix array does not affect internal state")
    void prefixArrayCopyIsIndependent() {
      int[] copy = ps.getPrefixArray();
      copy[1] = 9999; // mutate the copy

      // internal state must be unchanged: prefix[1] should still be 3
      assertEquals(3, ps.getPrefixArray()[1],
          "getPrefixArray() must return a defensive copy");
    }
  }
}
