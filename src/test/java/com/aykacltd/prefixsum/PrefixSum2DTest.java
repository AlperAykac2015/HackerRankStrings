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
 * Comprehensive unit tests for {@link PrefixSum2D}.
 * <p>
 * Test categories:
 * 1. Construction  – happy path, jagged/null/empty guards
 * 2. rangeSum      – full grid, sub-rectangles, single cells, single row/col
 * 3. totalSum      – convenience method
 * 4. rowSum        – entire row sums
 * 5. columnSum     – entire column sums
 * 6. Edge cases    – 1×1, 1×n, n×1, negatives, zeros
 * 7. Validation    – inverted regions, out-of-bounds indices
 * 8. Immutability  – source mutation, prefix table copy
 */
@DisplayName("PrefixSum2D Tests")
class PrefixSum2DTest {

  /*
   * Base grid (3 × 4):
   *   1  2  3  4
   *   5  6  7  8
   *   9 10 11 12
   *
   * Prefix table (4 × 5) – row/col 0 are sentinel zeros:
   *    0   0   0   0   0
   *    0   1   3   6  10
   *    0   6  14  24  36
   *    0  15  33  54  78
   */
  private static final int[][] BASE_GRID = {
      {1, 2, 3, 4},
      {5, 6, 7, 8},
      {9, 10, 11, 12}
  };

  private PrefixSum2D ps;

  @BeforeEach
  void setUp() {
    ps = new PrefixSum2D(BASE_GRID);
  }

  // =========================================================================
  // 1. Construction
  // =========================================================================

  @Nested
  @DisplayName("Construction")
  class ConstructionTests {

    @Test
    @DisplayName("Builds correct prefix table dimensions")
    void correctDimensions() {
      assertEquals(3, ps.getRows());
      assertEquals(4, ps.getCols());
      int[][] t = ps.getPrefixTable();
      assertEquals(4, t.length);       // rows + 1
      assertEquals(5, t[0].length);    // cols + 1
    }

    @Test
    @DisplayName("Prefix table sentinel row and column are all zeros")
    void sentinelRowColAreZero() {
      int[][] t = ps.getPrefixTable();
      for (int j = 0; j < 5; j++) {
        assertEquals(0, t[0][j], "sentinel row mismatch at col " + j);
      }
      for (int i = 0; i < 4; i++) {
        assertEquals(0, t[i][0], "sentinel col mismatch at row " + i);
      }
    }

    @Test
    @DisplayName("Prefix table values match expected")
    void prefixTableValuesCorrect() {
      int[][] expected = {
          {0, 0, 0, 0, 0},
          {0, 1, 3, 6, 10},
          {0, 6, 14, 24, 36},
          {0, 15, 33, 54, 78}
      };
      int[][] actual = ps.getPrefixTable();
      for (int i = 0; i <= 3; i++) {
        assertArrayEquals(expected[i], actual[i], "Mismatch at prefix row " + i);
      }
    }

    @Test
    @DisplayName("Throws on null grid")
    void throwsOnNullGrid() {
      assertThrows(IllegalArgumentException.class, () -> new PrefixSum2D(null));
    }

    @Test
    @DisplayName("Throws on zero-row grid")
    void throwsOnEmptyRowGrid() {
      assertThrows(IllegalArgumentException.class, () -> new PrefixSum2D(new int[][] {}));
    }

    @Test
    @DisplayName("Throws on zero-column grid")
    void throwsOnEmptyColumnGrid() {
      assertThrows(IllegalArgumentException.class,
          () -> new PrefixSum2D(new int[][] {{}, {}}));
    }

    @Test
    @DisplayName("Throws on jagged grid")
    void throwsOnJaggedGrid() {
      IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
          () -> new PrefixSum2D(new int[][] {{1, 2}, {3}}));
      assertTrue(ex.getMessage().toLowerCase().contains("jagged"));
    }
  }

  // =========================================================================
  // 2. rangeSum – happy path
  // =========================================================================

  @Nested
  @DisplayName("rangeSum – happy path")
  class RangeSumHappyPath {

    @Test
    @DisplayName("Entire grid sum = 78")
    void entireGrid() {
      assertEquals(78, ps.rangeSum(0, 0, 2, 3));
    }

    @Test
    @DisplayName("Top-left 1×1 cell (0,0) = 1")
    void topLeftCell() {
      assertEquals(1, ps.rangeSum(0, 0, 0, 0));
    }

    @Test
    @DisplayName("Bottom-right 1×1 cell (2,3) = 12")
    void bottomRightCell() {
      assertEquals(12, ps.rangeSum(2, 3, 2, 3));
    }

    @Test
    @DisplayName("Top-left 2×2 = 1+2+5+6 = 14")
    void topLeft2x2() {
      assertEquals(14, ps.rangeSum(0, 0, 1, 1));
    }

    @Test
    @DisplayName("Bottom-right 2×2 = 7+8+11+12 = 38")
    void bottomRight2x2() {
      assertEquals(38, ps.rangeSum(1, 2, 2, 3));
    }

    @Test
    @DisplayName("Middle 1×2 region (1,1)-(1,2) = 6+7 = 13")
    void middleRow() {
      assertEquals(13, ps.rangeSum(1, 1, 1, 2));
    }

    @Test
    @DisplayName("Centre 2×2 = 6+7+10+11 = 34")
    void centre2x2() {
      assertEquals(34, ps.rangeSum(1, 1, 2, 2));
    }

    @Test
    @DisplayName("First row full = 1+2+3+4 = 10")
    void firstRowFull() {
      assertEquals(10, ps.rangeSum(0, 0, 0, 3));
    }

    @Test
    @DisplayName("Last row full = 9+10+11+12 = 42")
    void lastRowFull() {
      assertEquals(42, ps.rangeSum(2, 0, 2, 3));
    }

    @Test
    @DisplayName("First column full = 1+5+9 = 15")
    void firstColumnFull() {
      assertEquals(15, ps.rangeSum(0, 0, 2, 0));
    }

    @Test
    @DisplayName("Last column full = 4+8+12 = 24")
    void lastColumnFull() {
      assertEquals(24, ps.rangeSum(0, 3, 2, 3));
    }

    @Test
    @DisplayName("Partition check: two halves sum to total")
    void twoHalvesSumToTotal() {
      int top = ps.rangeSum(0, 0, 0, 3); // row 0
      int bottom = ps.rangeSum(1, 0, 2, 3); // rows 1-2
      assertEquals(ps.totalSum(), top + bottom);
    }

    @Test
    @DisplayName("Partition check: four quadrants sum to total")
    void fourQuadrantsSumToTotal() {
      int q1 = ps.rangeSum(0, 0, 1, 1);
      int q2 = ps.rangeSum(0, 2, 1, 3);
      int q3 = ps.rangeSum(2, 0, 2, 1);
      int q4 = ps.rangeSum(2, 2, 2, 3);
      assertEquals(ps.totalSum(), q1 + q2 + q3 + q4);
    }
  }

  // =========================================================================
  // 3. totalSum
  // =========================================================================

  @Nested
  @DisplayName("totalSum")
  class TotalSumTests {

    @Test
    @DisplayName("Returns sum of entire grid")
    void returnsTotalSum() {
      assertEquals(78, ps.totalSum());
    }

    @Test
    @DisplayName("totalSum == rangeSum(0,0,rows-1,cols-1)")
    void matchesRangeSum() {
      assertEquals(ps.rangeSum(0, 0, ps.getRows() - 1, ps.getCols() - 1), ps.totalSum());
    }
  }

  // =========================================================================
  // 4. rowSum
  // =========================================================================

  @Nested
  @DisplayName("rowSum")
  class RowSumTests {

    @Test
    @DisplayName("Row 0 = 1+2+3+4 = 10")
    void row0() {
      assertEquals(10, ps.rowSum(0));
    }

    @Test
    @DisplayName("Row 1 = 5+6+7+8 = 26")
    void row1() {
      assertEquals(26, ps.rowSum(1));
    }

    @Test
    @DisplayName("Row 2 = 9+10+11+12 = 42")
    void row2() {
      assertEquals(42, ps.rowSum(2));
    }

    @Test
    @DisplayName("All row sums add up to totalSum")
    void allRowSumsAddToTotal() {
      int sum = 0;
      for (int r = 0; r < ps.getRows(); r++) {
        sum += ps.rowSum(r);
      }
      assertEquals(ps.totalSum(), sum);
    }

    @Test
    @DisplayName("Throws on negative row index")
    void throwsOnNegativeRow() {
      assertThrows(IllegalArgumentException.class, () -> ps.rowSum(-1));
    }

    @Test
    @DisplayName("Throws on out-of-bounds row index")
    void throwsOnOutOfBoundsRow() {
      assertThrows(IllegalArgumentException.class, () -> ps.rowSum(3));
    }
  }

  // =========================================================================
  // 5. columnSum
  // =========================================================================

  @Nested
  @DisplayName("columnSum")
  class ColumnSumTests {

    @Test
    @DisplayName("Col 0 = 1+5+9 = 15")
    void col0() {
      assertEquals(15, ps.columnSum(0));
    }

    @Test
    @DisplayName("Col 1 = 2+6+10 = 18")
    void col1() {
      assertEquals(18, ps.columnSum(1));
    }

    @Test
    @DisplayName("Col 2 = 3+7+11 = 21")
    void col2() {
      assertEquals(21, ps.columnSum(2));
    }

    @Test
    @DisplayName("Col 3 = 4+8+12 = 24")
    void col3() {
      assertEquals(24, ps.columnSum(3));
    }

    @Test
    @DisplayName("All column sums add up to totalSum")
    void allColumnSumsAddToTotal() {
      int sum = 0;
      for (int c = 0; c < ps.getCols(); c++) {
        sum += ps.columnSum(c);
      }
      assertEquals(ps.totalSum(), sum);
    }

    @Test
    @DisplayName("Throws on negative column index")
    void throwsOnNegativeCol() {
      assertThrows(IllegalArgumentException.class, () -> ps.columnSum(-1));
    }

    @Test
    @DisplayName("Throws on out-of-bounds column index")
    void throwsOnOutOfBoundsCol() {
      assertThrows(IllegalArgumentException.class, () -> ps.columnSum(4));
    }
  }

  // =========================================================================
  // 6. Edge cases
  // =========================================================================

  @Nested
  @DisplayName("Edge cases")
  class EdgeCases {

    @Test
    @DisplayName("1×1 grid")
    void singleCellGrid() {
      PrefixSum2D single = new PrefixSum2D(new int[][] {{99}});
      assertEquals(1, single.getRows());
      assertEquals(1, single.getCols());
      assertEquals(99, single.rangeSum(0, 0, 0, 0));
      assertEquals(99, single.totalSum());
    }

    @Test
    @DisplayName("1×n (single row) grid")
    void singleRowGrid() {
      PrefixSum2D row = new PrefixSum2D(new int[][] {{1, 2, 3, 4, 5}});
      assertEquals(15, row.totalSum());
      assertEquals(6, row.rangeSum(0, 1, 0, 3));  // 2+3+4
    }

    @Test
    @DisplayName("n×1 (single column) grid")
    void singleColumnGrid() {
      PrefixSum2D col = new PrefixSum2D(new int[][] {{1}, {2}, {3}, {4}});
      assertEquals(10, col.totalSum());
      assertEquals(5, col.rangeSum(1, 0, 2, 0)); // 2+3
    }

    @Test
    @DisplayName("All-zero grid")
    void allZeroGrid() {
      PrefixSum2D zeros = new PrefixSum2D(new int[][] {{0, 0}, {0, 0}});
      assertEquals(0, zeros.totalSum());
      assertEquals(0, zeros.rangeSum(0, 0, 1, 1));
    }

    @Test
    @DisplayName("Grid with negative values")
    void negativeValues() {
      PrefixSum2D neg = new PrefixSum2D(new int[][] {
          {-1, -2},
          {-3, -4}
      });
      assertEquals(-10, neg.totalSum());
      assertEquals(-3, neg.rangeSum(0, 0, 0, 1));  // -1-2
      assertEquals(-4, neg.rangeSum(0, 0, 1, 0));  // -1 + -3 = -4
    }

    @Test
    @DisplayName("Grid with mixed positive and negative values")
    void mixedValues() {
      PrefixSum2D mixed = new PrefixSum2D(new int[][] {
          {5, -3, 2},
          {-1, 4, -6}
      });
      assertEquals(1, mixed.totalSum()); // 5-3+2-1+4-6 = 1
      assertEquals(2, mixed.rangeSum(0, 0, 0, 1));  // 5-3
      assertEquals(-3, mixed.rangeSum(0, 1, 1, 2));  // -3+2+4-6 = -3
    }

    @Test
    @DisplayName("Non-square grid (2×5)")
    void nonSquareGrid() {
      PrefixSum2D rect = new PrefixSum2D(new int[][] {
          {1, 2, 3, 4, 5},
          {6, 7, 8, 9, 10}
      });
      assertEquals(55, rect.totalSum());
      assertEquals(16, rect.rangeSum(0, 0, 1, 1)); // 1+2+6+7
    }
  }

  // =========================================================================
  // 7. Validation – invalid regions
  // =========================================================================

  @Nested
  @DisplayName("rangeSum – invalid inputs")
  class ValidationTests {

    @Test
    @DisplayName("Throws when r1 < 0")
    void throwsWhenR1Negative() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(-1, 0, 1, 1));
    }

    @Test
    @DisplayName("Throws when r2 >= rows")
    void throwsWhenR2TooLarge() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(0, 0, 3, 1));
    }

    @Test
    @DisplayName("Throws when c1 < 0")
    void throwsWhenC1Negative() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(0, -1, 1, 2));
    }

    @Test
    @DisplayName("Throws when c2 >= cols")
    void throwsWhenC2TooLarge() {
      assertThrows(IllegalArgumentException.class, () -> ps.rangeSum(0, 0, 1, 4));
    }

    @Test
    @DisplayName("Throws when r1 > r2")
    void throwsWhenR1GreaterThanR2() {
      IllegalArgumentException ex = assertThrows(
          IllegalArgumentException.class, () -> ps.rangeSum(2, 0, 1, 3));
      assertTrue(ex.getMessage().contains("r1") && ex.getMessage().contains("r2"));
    }

    @Test
    @DisplayName("Throws when c1 > c2")
    void throwsWhenC1GreaterThanC2() {
      IllegalArgumentException ex = assertThrows(
          IllegalArgumentException.class, () -> ps.rangeSum(0, 3, 2, 1));
      assertTrue(ex.getMessage().contains("c1") && ex.getMessage().contains("c2"));
    }
  }

  // =========================================================================
  // 8. Immutability
  // =========================================================================

  @Nested
  @DisplayName("Immutability")
  class ImmutabilityTests {

    @Test
    @DisplayName("Mutating source grid does not affect prefix sums")
    void sourceMutationDoesNotAffectPrefix() {
      int[][] grid = {{1, 2}, {3, 4}};
      PrefixSum2D local = new PrefixSum2D(grid);
      int before = local.totalSum(); // 10

      grid[0][0] = 9999; // mutate source
      assertEquals(before, local.totalSum(),
          "Prefix table must be independent of source grid mutations");
    }

    @Test
    @DisplayName("Mutating returned prefix table copy does not affect internal state")
    void prefixTableCopyIsIndependent() {
      int[][] copy = ps.getPrefixTable();
      copy[1][1] = 9999; // mutate copy

      // Internal table cell [1][1] should still be 1
      assertEquals(1, ps.getPrefixTable()[1][1],
          "getPrefixTable() must return a deep defensive copy");
    }
  }
}
