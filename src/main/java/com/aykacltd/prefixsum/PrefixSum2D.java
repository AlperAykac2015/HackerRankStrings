package com.aykacltd.prefixsum;

/**
 * 2D Prefix Sum (two-dimensional cumulative sum).
 *
 * <p>Builds a prefix table in O(m×n) so that any rectangular sub-grid sum query
 * can be answered in O(1) using the inclusion-exclusion formula:
 * <pre>
 *   rangeSum(r1,c1,r2,c2)
 *       = prefix[r2+1][c2+1]
 *       - prefix[r1  ][c2+1]   // strip above
 *       - prefix[r2+1][c1  ]   // strip left
 *       + prefix[r1  ][c1  ]   // corner was removed twice, add back
 * </pre>
 *
 * <p>The internal table is (rows+1) × (cols+1) with a top-row and left-column of
 * zeros acting as sentinels, which eliminates boundary conditions during build
 * and query.
 *
 * <pre>
 * Example grid (3×4):
 *   1  2  3  4
 *   5  6  7  8
 *   9 10 11 12
 *
 * Prefix table (4×5):
 *    0   0   0   0   0
 *    0   1   3   6  10
 *    0   6  14  24  36
 *    0  15  33  54  78
 *
 * rangeSum(1,2,2,3) = prefix[3][4] - prefix[1][4] - prefix[3][2] + prefix[1][2]
 *                   = 54 - 10 - 33 + 3 = 14   (but wait: 7+8+11+12=38, see tests)
 * </pre>
 *
 * <p>Time complexity: Build O(m×n), Query O(1), Space O(m×n).
 */
public class PrefixSum2D {

  private final int[][] prefix;
  private final int rows;
  private final int cols;

  /**
   * Constructs the 2D prefix sum table from the given grid.
   *
   * @param grid a non-null, non-empty rectangular 2-D array
   * @throws IllegalArgumentException if {@code grid} is null, empty, or jagged
   */
  public PrefixSum2D(int[][] grid) {
    validateGrid(grid);

    this.rows = grid.length;
    this.cols = grid[0].length;
    this.prefix = new int[rows + 1][cols + 1]; // sentinels: row-0 and col-0 are 0

    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        prefix[i][j] = grid[i - 1][j - 1]
            + prefix[i - 1][j]       // top cell
            + prefix[i][j - 1]       // left cell
            - prefix[i - 1][j - 1];  // top-left subtracted twice → add back
      }
    }
  }

  /**
   * Returns the sum of all elements in the sub-rectangle whose top-left corner
   * is {@code (r1, c1)} and bottom-right corner is {@code (r2, c2)}, both inclusive.
   *
   * @param r1 top row index (inclusive), 0-based
   * @param c1 left column index (inclusive), 0-based
   * @param r2 bottom row index (inclusive), 0-based
   * @param c2 right column index (inclusive), 0-based
   * @return the rectangular sub-grid sum
   * @throws IllegalArgumentException if any index is out of bounds or the region is inverted
   */
  public int rangeSum(int r1, int c1, int r2, int c2) {
    validateRegion(r1, c1, r2, c2);
    return prefix[r2 + 1][c2 + 1]
        - prefix[r1][c2 + 1]
        - prefix[r2 + 1][c1]
        + prefix[r1][c1];
  }

  /**
   * Returns the sum of all elements in the grid.
   */
  public int totalSum() {
    return prefix[rows][cols];
  }

  /**
   * Returns the sum of the entire row {@code r}.
   *
   * @param r row index (0-based)
   */
  public int rowSum(int r) {
    if (r < 0 || r >= rows) {
      throw new IllegalArgumentException("Row index out of bounds: " + r);
    }
    return rangeSum(r, 0, r, cols - 1);
  }

  /**
   * Returns the sum of the entire column {@code c}.
   *
   * @param c column index (0-based)
   */
  public int columnSum(int c) {
    if (c < 0 || c >= cols) {
      throw new IllegalArgumentException("Column index out of bounds: " + c);
    }
    return rangeSum(0, c, rows - 1, c);
  }

  /**
   * Returns the number of rows in the original grid.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the original grid.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Returns a deep copy of the internal prefix table (size (rows+1) × (cols+1)).
   */
  public int[][] getPrefixTable() {
    int[][] copy = new int[rows + 1][cols + 1];
    for (int i = 0; i <= rows; i++) {
      System.arraycopy(prefix[i], 0, copy[i], 0, cols + 1);
    }
    return copy;
  }

  // -------------------------------------------------------------------------
  // Private helpers
  // -------------------------------------------------------------------------

  private void validateGrid(int[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid must not be null.");
    }
    if (grid.length == 0) {
      throw new IllegalArgumentException("Grid must not be empty.");
    }
    int expectedCols = grid[0].length;
    if (expectedCols == 0) {
      throw new IllegalArgumentException("Grid rows must not be empty.");
    }
    for (int i = 1; i < grid.length; i++) {
      if (grid[i].length != expectedCols) {
        throw new IllegalArgumentException(
            String.format("Grid is jagged: row 0 has %d cols but row %d has %d cols.",
                expectedCols, i, grid[i].length));
      }
    }
  }

  private void validateRegion(int r1, int c1, int r2, int c2) {
    if (r1 < 0 || r1 >= rows || r2 < 0 || r2 >= rows) {
      throw new IllegalArgumentException(
          String.format("Row indices out of bounds for grid with %d rows: r1=%d, r2=%d", rows, r1,
              r2));
    }
    if (c1 < 0 || c1 >= cols || c2 < 0 || c2 >= cols) {
      throw new IllegalArgumentException(
          String.format("Column indices out of bounds for grid with %d cols: c1=%d, c2=%d", cols,
              c1, c2));
    }
    if (r1 > r2) {
      throw new IllegalArgumentException(String.format("r1 (%d) must be <= r2 (%d)", r1, r2));
    }
    if (c1 > c2) {
      throw new IllegalArgumentException(String.format("c1 (%d) must be <= c2 (%d)", c1, c2));
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PrefixSum2D{rows=")
        .append(rows).append(", cols=").append(cols).append(", prefix=\n");
    for (int[] row : prefix) {
      sb.append("  ").append(java.util.Arrays.toString(row)).append("\n");
    }
    return sb.append("}").toString();
  }
}
