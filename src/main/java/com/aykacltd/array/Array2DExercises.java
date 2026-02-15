package com.aykacltd.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>Java Arrays — 2D &amp; Multi-Dimensional Operations Guide</h1>
 *
 * <p>This class covers <strong>50+</strong> operations on two-dimensional
 * and multi-dimensional arrays (matrices), from basic creation and
 * traversal to rotation, transposition, spiral traversal, and
 * mathematical operations.</p>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Creating 2D Arrays &amp; Matrices</li>
 *   <li>Section 2  – Traversal Patterns</li>
 *   <li>Section 3  – Row &amp; Column Operations</li>
 *   <li>Section 4  – Matrix Transposition</li>
 *   <li>Section 5  – Matrix Rotation (90°, 180°, 270°)</li>
 *   <li>Section 6  – Spiral &amp; Diagonal Traversal</li>
 *   <li>Section 7  – Matrix Math (add, subtract, multiply, scalar)</li>
 *   <li>Section 8  – Matrix Search</li>
 *   <li>Section 9  – Matrix Comparison &amp; Transformation</li>
 *   <li>Section 10 – Jagged Arrays (rows of different lengths)</li>
 *   <li>Section 11 – 3D and Higher-Dimensional Arrays</li>
 *   <li>Section 12 – Streams with 2D Arrays</li>
 *   <li>Section 13 – Real-World Matrix Patterns</li>
 * </ol>
 *
 * @author 2D Array Exercise Suite
 */
public class Array2DExercises {

  public static void main(String[] args) {
    System.out.println("═══════════════════════════════════════════════════════════");
    System.out.println("  JAVA ARRAYS — 2D & MULTI-DIMENSIONAL GUIDE");
    System.out.println("═══════════════════════════════════════════════════════════\n");

    printSection("1 — CREATING 2D ARRAYS & MATRICES");
    creating2DArrays();
    creatingWithStreams();
    identityAndSpecialMatrices();

    printSection("2 — TRAVERSAL PATTERNS");
    rowMajorTraversal();
    columnMajorTraversal();
    boundaryTraversal();
    zigzagTraversal();

    printSection("3 — ROW & COLUMN OPERATIONS");
    rowSums();
    columnSums();
    rowMinMax();
    columnMinMax();
    swapRows();
    swapColumns();

    printSection("4 — TRANSPOSITION");
    transposeSquare();
    transposeRectangular();
    transposeWithStream();

    printSection("5 — ROTATION");
    rotate90Clockwise();
    rotate90CounterClockwise();
    rotate180();
    rotate90InPlace();

    printSection("6 — SPIRAL & DIAGONAL TRAVERSAL");
    spiralTraversal();
    primaryDiagonal();
    secondaryDiagonal();
    allDiagonals();

    printSection("7 — MATRIX MATH");
    matrixAddition();
    matrixSubtraction();
    scalarMultiplication();
    matrixMultiplication();
    elementWiseMultiplication();
    matrixPower();

    printSection("8 — MATRIX SEARCH");
    linearSearchMatrix();
    searchSortedMatrix();
    searchRowColSortedMatrix();
    findSaddlePoint();

    printSection("9 — COMPARISON & TRANSFORMATION");
    compareMatrices();
    flattenMatrix();
    reshapeMatrix();
    mirrorHorizontal();
    mirrorVertical();

    printSection("10 — JAGGED ARRAYS");
    createJaggedArray();
    jaggedArrayOperations();

    printSection("11 — 3D AND HIGHER DIMENSIONS");
    create3DArray();
    traverse3DArray();
    rubiksCubeSlice();

    printSection("12 — STREAMS WITH 2D ARRAYS");
    flattenWithStream();
    streamRowOperations();
    streamColumnOperations();
    streamDiagonalSum();
    stream2DCollecting();

    printSection("13 — REAL-WORLD PATTERNS");
    gameOfLifeStep();
    imageBorderPadding();
    matrixPathSum();
    heatmapAnalysis();

    System.out.println("\n═══════════════════════════════════════════════════════════");
    System.out.println("  ALL 2D ARRAY EXERCISES COMPLETED");
    System.out.println("═══════════════════════════════════════════════════════════");
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 1 — CREATING 2D ARRAYS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Different ways to create 2D arrays.
   *
   * <p><strong>Key point:</strong> A 2D array in Java is an array of
   * arrays. {@code int[3][4]} creates 3 rows of 4 columns. Each row
   * is an independent {@code int[]} object, which is why Java supports
   * jagged arrays (rows of different lengths).</p>
   *
   * <p><strong>Memory layout:</strong> Java 2D arrays are NOT contiguous
   * in memory like C arrays. Each row is a separate heap object. This
   * means row-major traversal (across columns in one row) is cache-friendly,
   * while column-major traversal is not.</p>
   */
  static void creating2DArrays() {
    // Literal initialisation
    int[][] literal = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    // Size-based (all zeros)
    int[][] zeroed = new int[3][4];

    // Row-by-row (jagged allowed)
    int[][] rowByRow = new int[3][];
    rowByRow[0] = new int[] {1, 2};
    rowByRow[1] = new int[] {3, 4, 5};
    rowByRow[2] = new int[] {6};

    printMatrix("1.1 Literal 3x3", literal);
    printExercise("    Zeroed 3x4 dimensions",
        zeroed.length + " rows × " + zeroed[0].length + " cols");
    printExercise("    Jagged rows", Arrays.deepToString(rowByRow));
  }

  /**
   * Creating 2D arrays with streams and generators.
   */
  static void creatingWithStreams() {
    // Using stream to create and fill
    int rows = 4, cols = 5;

    // Each cell = row * cols + col (linear index)
    int[][] indexed = IntStream.range(0, rows)
        .mapToObj(r -> IntStream.range(0, cols)
            .map(c -> r * cols + c)
            .toArray())
        .toArray(int[][]::new);

    // Multiplication table
    int[][] mulTable = IntStream.rangeClosed(1, 5)
        .mapToObj(r -> IntStream.rangeClosed(1, 5)
            .map(c -> r * c)
            .toArray())
        .toArray(int[][]::new);

    printMatrix("1.2 Indexed (stream)", indexed);
    printMatrix("    Multiplication table", mulTable);
  }

  /**
   * Creating special matrices: identity, diagonal, ones.
   */
  static void identityAndSpecialMatrices() {
    int n = 4;

    // Identity matrix (1s on diagonal)
    int[][] identity = IntStream.range(0, n)
        .mapToObj(r -> IntStream.range(0, n)
            .map(c -> r == c ? 1 : 0).toArray())
        .toArray(int[][]::new);

    // All ones
    int[][] ones = new int[n][n];
      for (int[] row : ones) {
          Arrays.fill(row, 1);
      }

    // Diagonal matrix
    int[] diagValues = {5, 10, 15, 20};
    int[][] diagonal = IntStream.range(0, n)
        .mapToObj(r -> IntStream.range(0, n)
            .map(c -> r == c ? diagValues[r] : 0).toArray())
        .toArray(int[][]::new);

    printMatrix("1.3 Identity 4×4", identity);
    printMatrix("    Diagonal", diagonal);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 2 — TRAVERSAL PATTERNS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Row-major traversal (standard left-to-right, top-to-bottom).
   *
   * <p><strong>Key point:</strong> Row-major is the natural traversal
   * in Java and is cache-friendly because rows are contiguous arrays.</p>
   */
  static void rowMajorTraversal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    List<Integer> order = new ArrayList<>();
      for (int[] row : m) {
          for (int val : row) {
              order.add(val);
          }
      }

    printExercise("2.1 Row-major", order);
  }

  /**
   * Column-major traversal (top-to-bottom, left-to-right).
   *
   * <p><strong>Key point:</strong> Column-major iterates down each
   * column before moving right. Less cache-friendly in Java because
   * it jumps between different row arrays.</p>
   */
  static void columnMajorTraversal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    List<Integer> order = new ArrayList<>();
      for (int c = 0; c < m[0].length; c++) {
          for (int[] row : m) {
              order.add(row[c]);
          }
      }

    printExercise("2.2 Column-major", order);
  }

  /**
   * Boundary (perimeter) traversal.
   */
  static void boundaryTraversal() {
    int[][] m = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    int rows = m.length, cols = m[0].length;
    List<Integer> boundary = new ArrayList<>();

    // Top row left→right
      for (int c = 0; c < cols; c++) {
          boundary.add(m[0][c]);
      }
    // Right column top→bottom (skip first)
      for (int r = 1; r < rows; r++) {
          boundary.add(m[r][cols - 1]);
      }
    // Bottom row right→left (skip last)
      for (int c = cols - 2; c >= 0; c--) {
          boundary.add(m[rows - 1][c]);
      }
    // Left column bottom→top (skip first and last)
      for (int r = rows - 2; r >= 1; r--) {
          boundary.add(m[r][0]);
      }

    printExercise("2.3 Boundary traversal", boundary);
  }

  /**
   * Zigzag traversal (alternate direction per row).
   */
  static void zigzagTraversal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    List<Integer> zigzag = new ArrayList<>();

    for (int r = 0; r < m.length; r++) {
      if (r % 2 == 0) {
          for (int c = 0; c < m[r].length; c++) {
              zigzag.add(m[r][c]);
          }
      } else {
          for (int c = m[r].length - 1; c >= 0; c--) {
              zigzag.add(m[r][c]);
          }
      }
    }

    printExercise("2.4 Zigzag (snake)", zigzag);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 3 — ROW & COLUMN OPERATIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Sum of each row using streams.
   */
  static void rowSums() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    int[] sums = Arrays.stream(m)
        .mapToInt(row -> Arrays.stream(row).sum())
        .toArray();

    printExercise("3.1 Row sums", Arrays.toString(sums));
  }

  /**
   * Sum of each column.
   */
  static void columnSums() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    int[] sums = IntStream.range(0, m[0].length)
        .map(c -> Arrays.stream(m).mapToInt(row -> row[c]).sum())
        .toArray();

    printExercise("3.2 Column sums", Arrays.toString(sums));
  }

  /**
   * Min and max of each row.
   */
  static void rowMinMax() {
    int[][] m = {{3, 1, 4}, {1, 5, 9}, {2, 6, 5}};

    System.out.println("  ► 3.3 Row min/max:");
    IntStream.range(0, m.length).forEach(r -> {
      int min = Arrays.stream(m[r]).min().orElse(0);
      int max = Arrays.stream(m[r]).max().orElse(0);
      System.out.println("    Row %d: min=%d, max=%d".formatted(r, min, max));
    });
  }

  /**
   * Min and max of each column.
   */
  static void columnMinMax() {
    int[][] m = {{3, 1, 4}, {1, 5, 9}, {2, 6, 5}};
    int cols = m[0].length;

    System.out.println("  ► 3.4 Column min/max:");
    IntStream.range(0, cols).forEach(c -> {
      int finalC = c;
      int min = Arrays.stream(m).mapToInt(row -> row[finalC]).min().orElse(0);
      int max = Arrays.stream(m).mapToInt(row -> row[finalC]).max().orElse(0);
      System.out.println("    Col %d: min=%d, max=%d".formatted(c, min, max));
    });
  }

  /**
   * Swapping two rows.
   */
  static void swapRows() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int r1 = 0, r2 = 2;

    // Swap is O(1) because rows are references!
    int[] temp = m[r1];
    m[r1] = m[r2];
    m[r2] = temp;

    printMatrix("3.5 After swapping rows %d and %d".formatted(r1, r2), m);
  }

  /**
   * Swapping two columns (requires element-by-element swap).
   */
  static void swapColumns() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int c1 = 0, c2 = 2;

    // O(n) because we must swap each element
    for (int[] row : m) {
      int temp = row[c1];
      row[c1] = row[c2];
      row[c2] = temp;
    }

    printMatrix("3.6 After swapping cols %d and %d".formatted(c1, c2), m);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 4 — TRANSPOSITION
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Transpose a square matrix (swap rows and columns).
   *
   * <p><strong>Key point:</strong> Transposition swaps element (i,j)
   * with (j,i). For square matrices this can be done in-place by
   * swapping only the upper triangle.</p>
   */
  static void transposeSquare() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    // In-place: swap upper triangle with lower
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int temp = m[i][j];
        m[i][j] = m[j][i];
        m[j][i] = temp;
      }
    }

    printMatrix("4.1 Transposed (square, in-place)", m);
  }

  /**
   * Transpose a rectangular matrix (m×n → n×m).
   *
   * <p><strong>Key point:</strong> Rectangular transposition requires
   * a new array because the dimensions change.</p>
   */
  static void transposeRectangular() {
    int[][] m = {{1, 2, 3, 4}, {5, 6, 7, 8}};  // 2×4
    int rows = m.length, cols = m[0].length;

    int[][] transposed = new int[cols][rows];  // 4×2
      for (int r = 0; r < rows; r++) {
          for (int c = 0; c < cols; c++) {
              transposed[c][r] = m[r][c];
          }
      }

    printMatrix("4.2 Original (2×4)", m);
    printMatrix("    Transposed (4×2)", transposed);
  }

  /**
   * Transpose using streams.
   */
  static void transposeWithStream() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int cols = m[0].length;

    int[][] transposed = IntStream.range(0, cols)
        .mapToObj(c -> Arrays.stream(m).mapToInt(row -> row[c]).toArray())
        .toArray(int[][]::new);

    printMatrix("4.3 Transpose (stream)", transposed);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 5 — ROTATION
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Rotates a matrix 90° clockwise.
   *
   * <p><strong>Algorithm:</strong> Transpose, then reverse each row.
   * Or equivalently: {@code result[c][n-1-r] = original[r][c]}</p>
   *
   * <pre>
   *   Original:      90° CW:
   *   1 2 3          7 4 1
   *   4 5 6    →     8 5 2
   *   7 8 9          9 6 3
   * </pre>
   */
  static void rotate90Clockwise() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[][] rotated = new int[n][n];
      for (int r = 0; r < n; r++) {
          for (int c = 0; c < n; c++) {
              rotated[c][n - 1 - r] = m[r][c];
          }
      }

    printMatrix("5.1 Rotate 90° CW", rotated);
  }

  /**
   * Rotates a matrix 90° counter-clockwise.
   *
   * <p><strong>Algorithm:</strong> Transpose, then reverse each column.
   * Or: {@code result[n-1-c][r] = original[r][c]}</p>
   */
  static void rotate90CounterClockwise() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[][] rotated = new int[n][n];
      for (int r = 0; r < n; r++) {
          for (int c = 0; c < n; c++) {
              rotated[n - 1 - c][r] = m[r][c];
          }
      }

    printMatrix("5.2 Rotate 90° CCW", rotated);
  }

  /**
   * Rotates a matrix 180°.
   *
   * <p><strong>Algorithm:</strong> Reverse all rows, then reverse each row.
   * Or: {@code result[n-1-r][n-1-c] = original[r][c]}</p>
   */
  static void rotate180() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[][] rotated = new int[n][n];
      for (int r = 0; r < n; r++) {
          for (int c = 0; c < n; c++) {
              rotated[n - 1 - r][n - 1 - c] = m[r][c];
          }
      }

    printMatrix("5.3 Rotate 180°", rotated);
  }

  /**
   * In-place 90° clockwise rotation (O(1) extra space).
   *
   * <p><strong>Algorithm:</strong> Transpose in-place, then reverse
   * each row in-place. Only works for square matrices.</p>
   */
  static void rotate90InPlace() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    // Step 1: transpose
      for (int i = 0; i < n; i++) {
          for (int j = i + 1; j < n; j++) {
              int temp = m[i][j];
              m[i][j] = m[j][i];
              m[j][i] = temp;
          }
      }

    // Step 2: reverse each row
      for (int[] row : m) {
          for (int i = 0, j = n - 1; i < j; i++, j--) {
              int temp = row[i];
              row[i] = row[j];
              row[j] = temp;
          }
      }

    printMatrix("5.4 Rotate 90° CW (in-place)", m);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 6 — SPIRAL & DIAGONAL TRAVERSAL
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Spiral traversal of a matrix (clockwise, outside-in).
   *
   * <p><strong>Algorithm:</strong> Use four boundaries (top, bottom,
   * left, right) and shrink them inward after traversing each edge.</p>
   *
   * <pre>
   *    1  2  3  4
   *    5  6  7  8     →  1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10
   *    9  10 11 12
   *   13  14 15 16
   * </pre>
   */
  static void spiralTraversal() {
    int[][] m = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };

    List<Integer> spiral = new ArrayList<>();
    int top = 0, bottom = m.length - 1;
    int left = 0, right = m[0].length - 1;

    while (top <= bottom && left <= right) {
      // →  traverse top row
        for (int c = left; c <= right; c++) {
            spiral.add(m[top][c]);
        }
      top++;
      // ↓  traverse right column
        for (int r = top; r <= bottom; r++) {
            spiral.add(m[r][right]);
        }
      right--;
      // ←  traverse bottom row
      if (top <= bottom) {
          for (int c = right; c >= left; c--) {
              spiral.add(m[bottom][c]);
          }
        bottom--;
      }
      // ↑  traverse left column
      if (left <= right) {
          for (int r = bottom; r >= top; r--) {
              spiral.add(m[r][left]);
          }
        left++;
      }
    }

    printExercise("6.1 Spiral traversal", spiral);
  }

  /**
   * Primary diagonal (top-left to bottom-right) elements.
   */
  static void primaryDiagonal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[] diag = IntStream.range(0, n).map(i -> m[i][i]).toArray();
    int sum = Arrays.stream(diag).sum();

    printExercise("6.2 Primary diagonal", Arrays.toString(diag) + " sum=" + sum);
  }

  /**
   * Secondary diagonal (top-right to bottom-left) elements.
   */
  static void secondaryDiagonal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[] diag = IntStream.range(0, n).map(i -> m[i][n - 1 - i]).toArray();
    int sum = Arrays.stream(diag).sum();

    printExercise("6.3 Secondary diagonal", Arrays.toString(diag) + " sum=" + sum);
  }

  /**
   * All diagonals (top-left to bottom-right direction).
   */
  static void allDiagonals() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int rows = m.length, cols = m[0].length;

    System.out.println("  ► 6.4 All diagonals (↘ direction):");
    // There are (rows + cols - 1) diagonals
    for (int d = 0; d < rows + cols - 1; d++) {
      List<Integer> diag = new ArrayList<>();
      int startRow = Math.max(0, d - cols + 1);
      int startCol = Math.max(0, cols - 1 - d);
      int r = startRow, c = startCol;
      while (r < rows && c < cols) {
        diag.add(m[r++][c++]);
      }
      System.out.println("    Diag %d: %s".formatted(d, diag));
    }
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 7 — MATRIX MATH
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Matrix addition: C = A + B (element-wise).
   *
   * <p><strong>Precondition:</strong> A and B must have the same dimensions.</p>
   */
  static void matrixAddition() {
    int[][] a = {{1, 2}, {3, 4}};
    int[][] b = {{5, 6}, {7, 8}};

    int[][] sum = IntStream.range(0, a.length)
        .mapToObj(r -> IntStream.range(0, a[0].length)
            .map(c -> a[r][c] + b[r][c]).toArray())
        .toArray(int[][]::new);

    printMatrix("7.1 A + B", sum);
  }

  /**
   * Matrix subtraction: C = A − B.
   */
  static void matrixSubtraction() {
    int[][] a = {{5, 6}, {7, 8}};
    int[][] b = {{1, 2}, {3, 4}};

    int[][] diff = IntStream.range(0, a.length)
        .mapToObj(r -> IntStream.range(0, a[0].length)
            .map(c -> a[r][c] - b[r][c]).toArray())
        .toArray(int[][]::new);

    printMatrix("7.2 A − B", diff);
  }

  /**
   * Scalar multiplication: C = k × A.
   */
  static void scalarMultiplication() {
    int[][] a = {{1, 2}, {3, 4}};
    int k = 3;

    int[][] scaled = Arrays.stream(a)
        .map(row -> Arrays.stream(row).map(x -> x * k).toArray())
        .toArray(int[][]::new);

    printMatrix("7.3 " + k + " × A", scaled);
  }

  /**
   * Matrix multiplication: C = A × B.
   *
   * <p><strong>Key point:</strong> A(m×n) × B(n×p) = C(m×p).
   * The number of columns in A must equal the number of rows in B.
   * Each element: {@code C[i][j] = Σ(A[i][k] × B[k][j])} for k=0..n-1.</p>
   *
   * <p><strong>Time:</strong> O(m × n × p)</p>
   */
  static void matrixMultiplication() {
    int[][] a = {{1, 2, 3}, {4, 5, 6}};         // 2×3
    int[][] b = {{7, 8}, {9, 10}, {11, 12}};     // 3×2

    int rows = a.length, cols = b[0].length, inner = b.length;

    int[][] product = IntStream.range(0, rows)
        .mapToObj(r -> IntStream.range(0, cols)
            .map(c -> IntStream.range(0, inner)
                .map(k -> a[r][k] * b[k][c]).sum())
            .toArray())
        .toArray(int[][]::new);

    printMatrix("7.4 A(2×3) × B(3×2)", product);
  }

  /**
   * Element-wise (Hadamard) multiplication.
   */
  static void elementWiseMultiplication() {
    int[][] a = {{1, 2}, {3, 4}};
    int[][] b = {{5, 6}, {7, 8}};

    int[][] hadamard = IntStream.range(0, a.length)
        .mapToObj(r -> IntStream.range(0, a[0].length)
            .map(c -> a[r][c] * b[r][c]).toArray())
        .toArray(int[][]::new);

    printMatrix("7.5 A ⊙ B (Hadamard)", hadamard);
  }

  /**
   * Matrix power: A^n (repeated matrix multiplication).
   */
  static void matrixPower() {
    int[][] a = {{1, 1}, {1, 0}};  // Fibonacci matrix
    int power = 6;

    int[][] result = identityMatrix(a.length);
    int[][] base = deepCopy(a);
    for (int p = 0; p < power; p++) {
      result = multiplyMatrices(result, base);
    }

    printExercise("7.6 Fibonacci matrix ^" + power, Arrays.deepToString(result));
    printExercise("    (top-left = Fibonacci(" + (power + 1) + "))", result[0][0]);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 8 — MATRIX SEARCH
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Linear search across entire matrix using streams.
   */
  static void linearSearchMatrix() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int target = 5;

    // Find the [row, col] coordinates
    int[] found = IntStream.range(0, m.length)
        .boxed()
        .flatMap(r -> IntStream.range(0, m[r].length)
            .filter(c -> m[r][c] == target)
            .mapToObj(c -> new int[] {r, c}))
        .findFirst().orElse(null);

    printExercise("8.1 Linear search for " + target,
        found != null ? "found at [%d,%d]".formatted(found[0], found[1]) : "not found");
  }

  /**
   * Search in a row-sorted matrix using binary search per row.
   */
  static void searchSortedMatrix() {
    int[][] m = {{1, 3, 5}, {7, 9, 11}, {13, 15, 17}};
    int target = 9;

    String result = "not found";
    for (int r = 0; r < m.length; r++) {
      int idx = Arrays.binarySearch(m[r], target);
      if (idx >= 0) {
        result = "found at [%d,%d]".formatted(r, idx);
        break;
      }
    }

    printExercise("8.2 Binary search in sorted rows for " + target, result);
  }

  /**
   * Search in a matrix sorted both row-wise and column-wise
   * (staircase search).
   *
   * <p><strong>Algorithm:</strong> Start at top-right corner.
   * If current &gt; target, move left. If current &lt; target, move down.</p>
   *
   * <p><strong>Time:</strong> O(m + n)</p>
   */
  static void searchRowColSortedMatrix() {
    int[][] m = {
        {1, 4, 7, 11},
        {2, 5, 8, 12},
        {3, 6, 9, 16},
        {10, 13, 14, 17}
    };
    int target = 9;

    // Start top-right
    int r = 0, c = m[0].length - 1;
    String result = "not found";
    int steps = 0;
    while (r < m.length && c >= 0) {
      steps++;
      if (m[r][c] == target) {
        result = "found at [%d,%d] in %d steps".formatted(r, c, steps);
        break;
      } else if (m[r][c] > target) {
          c--;
      } else {
          r++;
      }
    }

    printExercise("8.3 Staircase search for " + target, result);
  }

  /**
   * Finds the saddle point — an element that is the minimum in its
   * row and the maximum in its column.
   */
  static void findSaddlePoint() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    System.out.println("  ► 8.4 Saddle points:");
    boolean found = false;
    for (int r = 0; r < m.length; r++) {
      int rowMin = Arrays.stream(m[r]).min().orElse(0);
      for (int c = 0; c < m[0].length; c++) {
        if (m[r][c] == rowMin) {
          int col = c;
          int colMax = Arrays.stream(m).mapToInt(row -> row[col]).max().orElse(0);
          if (m[r][c] == colMax) {
            System.out.println("    Found at [%d,%d] = %d".formatted(r, c, m[r][c]));
            found = true;
          }
        }
      }
    }
      if (!found) {
          System.out.println("    None found");
      }
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 9 — COMPARISON & TRANSFORMATION
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Deep comparison of two matrices.
   */
  static void compareMatrices() {
    int[][] a = {{1, 2}, {3, 4}};
    int[][] b = {{1, 2}, {3, 4}};
    int[][] c = {{1, 2}, {3, 5}};

    printExercise("9.1 deepEquals(a, b)", Arrays.deepEquals(a, b));
    printExercise("    deepEquals(a, c)", Arrays.deepEquals(a, c));
  }

  /**
   * Flattening a 2D array into a 1D array.
   */
  static void flattenMatrix() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    int[] flat = Arrays.stream(m).flatMapToInt(Arrays::stream).toArray();

    printExercise("9.2 Flatten", Arrays.toString(flat));
  }

  /**
   * Reshaping a 1D array into a 2D matrix.
   */
  static void reshapeMatrix() {
    int[] flat = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int newRows = 3, newCols = 4;

    int[][] reshaped = IntStream.range(0, newRows)
        .mapToObj(r -> Arrays.copyOfRange(flat, r * newCols, (r + 1) * newCols))
        .toArray(int[][]::new);

    printMatrix("9.3 Reshape 12 → 3×4", reshaped);
  }

  /**
   * Mirror horizontally (flip left-right).
   */
  static void mirrorHorizontal() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    int[][] mirrored = Arrays.stream(m)
        .map(row -> {
          int[] rev = new int[row.length];
            for (int i = 0; i < row.length; i++) {
                rev[i] = row[row.length - 1 - i];
            }
          return rev;
        })
        .toArray(int[][]::new);

    printMatrix("9.4 Mirror horizontal (flip L↔R)", mirrored);
  }

  /**
   * Mirror vertically (flip top-bottom).
   */
  static void mirrorVertical() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int[][] mirrored = IntStream.rangeClosed(1, n)
        .mapToObj(i -> m[n - i].clone())
        .toArray(int[][]::new);

    printMatrix("9.5 Mirror vertical (flip T↔B)", mirrored);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 10 — JAGGED ARRAYS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Jagged arrays: rows of different lengths.
   *
   * <p><strong>Key point:</strong> Java's 2D arrays are truly arrays
   * of arrays, so each row can have a different length. This is
   * unlike C/C++ where 2D arrays must be rectangular.</p>
   */
  static void createJaggedArray() {
    int[][] jagged = new int[4][];
    jagged[0] = new int[] {1};
    jagged[1] = new int[] {2, 3};
    jagged[2] = new int[] {4, 5, 6};
    jagged[3] = new int[] {7, 8, 9, 10};

    // Pascal's triangle is a classic jagged array
    int[][] pascal = new int[6][];
    for (int i = 0; i < pascal.length; i++) {
      pascal[i] = new int[i + 1];
      pascal[i][0] = pascal[i][i] = 1;
      for (int j = 1; j < i; j++) {
        pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];
      }
    }

    printExercise("10.1 Jagged array", Arrays.deepToString(jagged));
    System.out.println("  ► 10.1 Pascal's triangle:");
      for (int[] row : pascal) {
          System.out.println("    " + Arrays.toString(row));
      }
  }

  /**
   * Operations on jagged arrays — sum, max, flatten.
   */
  static void jaggedArrayOperations() {
    int[][] jagged = {{1}, {2, 3}, {4, 5, 6}, {7, 8, 9, 10}};

    int totalSum = Arrays.stream(jagged).flatMapToInt(Arrays::stream).sum();
    int totalElements = Arrays.stream(jagged).mapToInt(row -> row.length).sum();
    int maxElement = Arrays.stream(jagged).flatMapToInt(Arrays::stream).max().orElse(0);

    // Longest and shortest rows
    int maxRowLen = Arrays.stream(jagged).mapToInt(row -> row.length).max().orElse(0);
    int minRowLen = Arrays.stream(jagged).mapToInt(row -> row.length).min().orElse(0);

    printExercise("10.2 Total sum", totalSum);
    printExercise("    Total elements", totalElements);
    printExercise("    Max element", maxElement);
    printExercise("    Longest row", maxRowLen + " elements");
    printExercise("    Shortest row", minRowLen + " element(s)");
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 11 — 3D AND HIGHER DIMENSIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Creating a 3D array (cube).
   *
   * <p><strong>Key point:</strong> A 3D array {@code int[layers][rows][cols]}
   * is an array of 2D arrays. Each dimension adds a level of nesting.
   * Think of it as a stack of 2D matrices (layers).</p>
   */
  static void create3DArray() {
    int layers = 2, rows = 3, cols = 4;

    int[][][] cube = new int[layers][rows][cols];
    int counter = 1;
      for (int l = 0; l < layers; l++) {
          for (int r = 0; r < rows; r++) {
              for (int c = 0; c < cols; c++) {
                  cube[l][r][c] = counter++;
              }
          }
      }

    System.out.println("  ► 11.1 3D array (2 layers × 3 rows × 4 cols):");
    for (int l = 0; l < layers; l++) {
      System.out.println("    Layer " + l + ":");
      printMatrixIndented(cube[l], "      ");
    }
  }

  /**
   * Traversal and aggregation of 3D arrays.
   */
  static void traverse3DArray() {
    int[][][] cube = {
        {{1, 2}, {3, 4}},
        {{5, 6}, {7, 8}}
    };

    // Total sum using nested streams
    int sum = Arrays.stream(cube)
        .flatMap(Arrays::stream)
        .flatMapToInt(Arrays::stream)
        .sum();

    // Count elements
    long count = Arrays.stream(cube)
        .flatMap(Arrays::stream)
        .flatMapToInt(Arrays::stream)
        .count();

    // Sum per layer
    int[] layerSums = Arrays.stream(cube)
        .mapToInt(layer -> Arrays.stream(layer)
            .flatMapToInt(Arrays::stream).sum())
        .toArray();

    printExercise("11.2 3D total sum", sum);
    printExercise("    Total elements", count);
    printExercise("    Sum per layer", Arrays.toString(layerSums));
  }

  /**
   * Extracting a 2D slice from a 3D array.
   */
  static void rubiksCubeSlice() {
    int[][][] cube = {
        {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
        {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}},
        {{19, 20, 21}, {22, 23, 24}, {25, 26, 27}}
    };

    // Layer slice (fixed layer index) — already a 2D array
    int[][] layerSlice = cube[1];

    // Column slice (fixed col index across all layers and rows)
    int colIdx = 1;
    int[][] colSlice = Arrays.stream(cube)
        .map(layer -> Arrays.stream(layer)
            .mapToInt(row -> row[colIdx]).toArray())
        .toArray(int[][]::new);

    printMatrix("11.3 Layer 1 slice", layerSlice);
    printMatrix("    Column 1 slice (across layers)", colSlice);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 12 — STREAMS WITH 2D ARRAYS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Flattening 2D to 1D with different stream approaches.
   */
  static void flattenWithStream() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    // Using flatMapToInt
    int[] flat1 = Arrays.stream(m).flatMapToInt(Arrays::stream).toArray();

    // Using Stream.of
    int[] flat2 = Stream.of(m).flatMapToInt(Arrays::stream).toArray();

    printExercise("12.1 flatMapToInt", Arrays.toString(flat1));
    printExercise("    Stream.of", Arrays.toString(flat2));
  }

  /**
   * Applying stream operations per row.
   */
  static void streamRowOperations() {
    int[][] m = {{3, 1, 4}, {1, 5, 9}, {2, 6, 5}};

    // Sort each row
    int[][] sorted = Arrays.stream(m)
        .map(row -> Arrays.stream(row).sorted().toArray())
        .toArray(int[][]::new);

    // Average per row
    double[] rowAvgs = Arrays.stream(m)
        .mapToDouble(row -> Arrays.stream(row).average().orElse(0))
        .toArray();

    printMatrix("12.2 Each row sorted", sorted);
    printExercise("    Row averages", Arrays.toString(rowAvgs));
  }

  /**
   * Stream operations on columns.
   */
  static void streamColumnOperations() {
    int[][] m = {{3, 1, 4}, {1, 5, 9}, {2, 6, 5}};
    int cols = m[0].length;

    // Extract each column as a stream, compute average
    double[] colAvgs = IntStream.range(0, cols)
        .mapToDouble(c -> {
          int finalC = c;
          return Arrays.stream(m).mapToInt(row -> row[finalC]).average().orElse(0);
        })
        .toArray();

    printExercise("12.3 Column averages", Arrays.toString(colAvgs));
  }

  /**
   * Diagonal sum using streams.
   */
  static void streamDiagonalSum() {
    int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = m.length;

    int primaryDiagSum = IntStream.range(0, n).map(i -> m[i][i]).sum();
    int secondaryDiagSum = IntStream.range(0, n).map(i -> m[i][n - 1 - i]).sum();

    printExercise("12.4 Primary diagonal sum", primaryDiagSum);
    printExercise("    Secondary diagonal sum", secondaryDiagSum);
  }

  /**
   * Collecting 2D array values with Collectors.
   */
  static void stream2DCollecting() {
    int[][] m = {{3, 1, 4}, {1, 5, 9}, {2, 6, 5}};

    // All elements as a sorted list
    List<Integer> sorted = Arrays.stream(m)
        .flatMapToInt(Arrays::stream).boxed()
        .sorted().toList();

    // Frequency map of all values
    Map<Integer, Long> freq = Arrays.stream(m)
        .flatMapToInt(Arrays::stream).boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

    // Find all cells matching a condition, with coordinates
    record Cell(int row, int col, int value) {
    }
    List<Cell> largeCells = IntStream.range(0, m.length)
        .boxed()
        .flatMap(r -> IntStream.range(0, m[r].length)
            .filter(c -> m[r][c] > 4)
            .mapToObj(c -> new Cell(r, c, m[r][c])))
        .toList();

    printExercise("12.5 All values sorted", sorted);
    printExercise("    Frequency map", freq);
    printExercise("    Cells > 4", largeCells);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 13 — REAL-WORLD PATTERNS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * One step of Conway's Game of Life.
   *
   * <p><strong>Rules:</strong></p>
   * <ul>
   *   <li>Live cell with 2–3 neighbours: survives</li>
   *   <li>Dead cell with exactly 3 neighbours: becomes alive</li>
   *   <li>All others: die or stay dead</li>
   * </ul>
   */
  static void gameOfLifeStep() {
    int[][] board = {
        {0, 1, 0, 0},
        {0, 0, 1, 0},
        {1, 1, 1, 0},
        {0, 0, 0, 0}
    };

    int rows = board.length, cols = board[0].length;
    int[][] next = new int[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int neighbours = countNeighbours(board, r, c);
        if (board[r][c] == 1) {
          next[r][c] = (neighbours == 2 || neighbours == 3) ? 1 : 0;
        } else {
          next[r][c] = (neighbours == 3) ? 1 : 0;
        }
      }
    }

    printMatrix("13.1 Game of Life — before", board);
    printMatrix("    Game of Life — after", next);
  }

  /**
   * Padding a matrix with a border of zeros (like image border padding).
   */
  static void imageBorderPadding() {
    int[][] image = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int pad = 1;

    int newRows = image.length + 2 * pad;
    int newCols = image[0].length + 2 * pad;
    int[][] padded = new int[newRows][newCols];

    for (int r = 0; r < image.length; r++) {
      System.arraycopy(image[r], 0, padded[r + pad], pad, image[r].length);
    }

    printMatrix("13.2 Original", image);
    printMatrix("    Padded (border=1)", padded);
  }

  /**
   * Minimum path sum from top-left to bottom-right (dynamic programming).
   *
   * <p><strong>Algorithm:</strong> At each cell, the min cost to reach
   * it is its value plus the minimum of the cell above and the cell
   * to the left. This is a classic DP problem on 2D arrays.</p>
   */
  static void matrixPathSum() {
    int[][] grid = {
        {1, 3, 1},
        {1, 5, 1},
        {4, 2, 1}
    };

    int rows = grid.length, cols = grid[0].length;
    int[][] dp = new int[rows][cols];
    dp[0][0] = grid[0][0];

    // Fill first row
      for (int c = 1; c < cols; c++) {
          dp[0][c] = dp[0][c - 1] + grid[0][c];
      }
    // Fill first column
      for (int r = 1; r < rows; r++) {
          dp[r][0] = dp[r - 1][0] + grid[r][0];
      }
    // Fill rest
      for (int r = 1; r < rows; r++) {
          for (int c = 1; c < cols; c++) {
              dp[r][c] = grid[r][c] + Math.min(dp[r - 1][c], dp[r][c - 1]);
          }
      }

    printMatrix("13.3 Grid costs", grid);
    printMatrix("    DP table (min path sums)", dp);
    printExercise("    Min path sum (top-left → bottom-right)", dp[rows - 1][cols - 1]);
  }

  /**
   * Heatmap analysis: finding the "hotspot" (maximum sub-region).
   */
  static void heatmapAnalysis() {
    int[][] heatmap = {
        {1, 2, 3, 0},
        {4, 5, 6, 1},
        {7, 8, 9, 2},
        {0, 1, 2, 3}
    };

    // Global max
    int globalMax = Arrays.stream(heatmap).flatMapToInt(Arrays::stream).max().orElse(0);

    // Find all cells with max value
    record Cell(int row, int col, int value) {
    }
    List<Cell> hotspots = IntStream.range(0, heatmap.length)
        .boxed()
        .flatMap(r -> IntStream.range(0, heatmap[r].length)
            .filter(c -> heatmap[r][c] == globalMax)
            .mapToObj(c -> new Cell(r, c, heatmap[r][c])))
        .toList();

    // Average per quadrant
    int midR = heatmap.length / 2, midC = heatmap[0].length / 2;
    double topLeft = subMatrixAvg(heatmap, 0, midR, 0, midC);
    double topRight = subMatrixAvg(heatmap, 0, midR, midC, heatmap[0].length);
    double bottomLeft = subMatrixAvg(heatmap, midR, heatmap.length, 0, midC);
    double bottomRight = subMatrixAvg(heatmap, midR, heatmap.length, midC, heatmap[0].length);

    printExercise("13.4 Global max", globalMax);
    printExercise("    Hotspots", hotspots);
    printExercise("    Quadrant averages",
        "TL=%.1f, TR=%.1f, BL=%.1f, BR=%.1f"
            .formatted(topLeft, topRight, bottomLeft, bottomRight));
  }

  // ═══════════════════════════════════════════════════════════════════
  //  HELPERS
  // ═══════════════════════════════════════════════════════════════════

  private static int countNeighbours(int[][] board, int row, int col) {
    int count = 0;
      for (int dr = -1; dr <= 1; dr++) {
          for (int dc = -1; dc <= 1; dc++) {
              if (dr == 0 && dc == 0) {
                  continue;
              }
              int r = row + dr, c = col + dc;
              if (r >= 0 && r < board.length && c >= 0 && c < board[0].length) {
                  count += board[r][c];
              }
          }
      }
    return count;
  }

  private static double subMatrixAvg(int[][] m, int r1, int r2, int c1, int c2) {
    return IntStream.range(r1, r2)
        .flatMap(r -> IntStream.range(c1, c2).map(c -> m[r][c]))
        .average().orElse(0);
  }

  private static int[][] identityMatrix(int n) {
    int[][] id = new int[n][n];
      for (int i = 0; i < n; i++) {
          id[i][i] = 1;
      }
    return id;
  }

  private static int[][] deepCopy(int[][] m) {
    return Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
  }

  private static int[][] multiplyMatrices(int[][] a, int[][] b) {
    int rows = a.length, cols = b[0].length, inner = b.length;
    int[][] result = new int[rows][cols];
      for (int r = 0; r < rows; r++) {
          for (int c = 0; c < cols; c++) {
              for (int k = 0; k < inner; k++) {
                  result[r][c] += a[r][k] * b[k][c];
              }
          }
      }
    return result;
  }

  private static void printMatrix(String label, int[][] m) {
    System.out.println("  ► " + label + ":");
    for (int[] row : m) {
      System.out.println("    " + Arrays.toString(row));
    }
  }

  private static void printMatrixIndented(int[][] m, String indent) {
      for (int[] row : m) {
          System.out.println(indent + Arrays.toString(row));
      }
  }

  private static void printExercise(String label, Object result) {
    System.out.println("  ► " + label + ": " + result);
  }

  private static void printSection(String title) {
    System.out.println("\n╔══════════════════════════════════════════════════════════╗");
    System.out.println("║  SECTION " + title);
    System.out.println("╚══════════════════════════════════════════════════════════╝");
  }
}
