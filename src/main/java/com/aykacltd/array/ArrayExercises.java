package com.aykacltd.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>Java Arrays — 1D Operations Comprehensive Guide</h1>
 *
 * <p>This class contains <strong>60+</strong> exercises covering every
 * major operation on one-dimensional arrays in Java, with Stream API
 * alternatives wherever possible.</p>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Creating &amp; Initialising Arrays</li>
 *   <li>Section 2  – Arrays Utility Class</li>
 *   <li>Section 3  – Rotation (left, right, by K)</li>
 *   <li>Section 4  – Reversal &amp; Shifting</li>
 *   <li>Section 5  – Merging Two Arrays</li>
 *   <li>Section 6  – Comparing Arrays &amp; Finding Differences</li>
 *   <li>Section 7  – Set Operations (union, intersection, difference)</li>
 *   <li>Section 8  – Searching &amp; Filtering</li>
 *   <li>Section 9  – Math Operations (sum, product, min, max, avg, stats)</li>
 *   <li>Section 10 – Prefix Sums &amp; Sliding Windows</li>
 *   <li>Section 11 – Frequency, Duplicates &amp; Distinct</li>
 *   <li>Section 12 – Partitioning, Grouping &amp; Chunking</li>
 *   <li>Section 13 – Sorting Variations</li>
 *   <li>Section 14 – Two-Pointer &amp; In-Place Techniques</li>
 *   <li>Section 15 – Conversion: Array ↔ List ↔ Stream ↔ Set</li>
 * </ol>
 *
 * @author Array Exercise Suite
 */
public class ArrayExercises {

  public static void main(String[] args) {
    twoSumSorted();
  }

  public static void main2(String[] args) {
    System.out.println("═══════════════════════════════════════════════════════════");
    System.out.println("  JAVA ARRAYS — 1D OPERATIONS COMPREHENSIVE GUIDE");
    System.out.println("═══════════════════════════════════════════════════════════\n");

    printSection("1 — CREATING & INITIALISING");
    creatingArrays();
    fillingArrays();

    printSection("2 — ARRAYS UTILITY CLASS");
    arraysUtilityMethods();

    printSection("3 — ROTATION");
    rotateLeft();
    rotateRight();
    rotateByK();
    rotateByKInPlace();

    printSection("4 — REVERSAL & SHIFTING");
    reverseArray();
    reverseWithStream();
    shiftElements();

    printSection("5 — MERGING TWO ARRAYS");
    mergeConcatenate();
    mergeSortedArrays();
    mergeAlternating();
    mergeWithoutDuplicates();

    printSection("6 — COMPARING & DIFFERENCES");
    compareArraysEquals();
    compareArraysDeepEquals();
    findMismatchIndex();
    compareWithStream();

    printSection("7 — SET OPERATIONS");
    unionOfArrays();
    intersectionOfArrays();
    differenceOfArrays();
    symmetricDifference();
    commonElementsMultipleArrays();

    printSection("8 — SEARCHING & FILTERING");
    linearSearch();
    binarySearchDemo();
    filterWithStream();
    findFirstAndLast();
    findKthLargestSmallest();

    printSection("9 — MATH OPERATIONS");
    sumAndProduct();
    minMaxAvg();
    summaryStatisticsDemo();
    cumulativeSum();
    dotProduct();
    normalise();
    elementWiseOperations();
    rangeAndVariance();

    printSection("10 — PREFIX SUMS & SLIDING WINDOWS");
    prefixSum();
    rangeSumQuery();
    slidingWindowMax();
    slidingWindowAverage();

    printSection("11 — FREQUENCY, DUPLICATES & DISTINCT");
    countFrequencies();
    findDuplicates();
    removeDuplicates();
    removeDuplicatesSorted();
    distinctWithStream();
    mostFrequentElement();

    printSection("12 — PARTITIONING, GROUPING & CHUNKING");
    partitionByPredicate();
    groupByModulo();
    chunkArray();
    separateEvenOdd();

    printSection("13 — SORTING VARIATIONS");
    sortAscDesc();
    sortByAbsoluteValue();
    sortByFrequency();
    dutchNationalFlag();

    printSection("14 — TWO-POINTER & IN-PLACE");
    twoSumSorted();
    moveZeroesToEnd();
    removeDuplicatesInPlace();
    partitionAroundPivot();

    printSection("15 — CONVERSIONS");
    arrayToListConversions();
    listToArrayConversions();
    primitiveBoxedConversions();
    arrayToStreamAndBack();
    arrayToSetAndBack();

    System.out.println("\n═══════════════════════════════════════════════════════════");
    System.out.println("  ALL 1D ARRAY EXERCISES COMPLETED");
    System.out.println("═══════════════════════════════════════════════════════════");
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 1 — CREATING & INITIALISING
  // ═══════════════════════════════════════════════════════════════════

  /**
   * All the ways to create arrays in Java.
   *
   * <p><strong>Key point:</strong> Arrays are fixed-size, contiguous
   * blocks of memory. Once created, their length cannot change.
   * Default values: 0 for numeric types, false for boolean, null
   * for reference types.</p>
   */
  static void creatingArrays() {
    // Literal initialisation
    int[] literal = {10, 20, 30, 40, 50};

    // new + size (default values)
    int[] zeroed = new int[5];        // all zeros
    String[] nulls = new String[3];   // all null
    boolean[] falses = new boolean[3]; // all false

    // new + inline
    int[] inline = new int[] {1, 2, 3};

    // From stream
    int[] fromStream = IntStream.rangeClosed(1, 10).toArray();

    // From generator
    int[] generated = IntStream.generate(() -> (int) (Math.random() * 100))
        .limit(5).toArray();

    // From function
    int[] fromFunction = IntStream.iterate(1, n -> n * 2).limit(8).toArray();

    print("1.1 Literal", literal);
    print("    Zeroed", zeroed);
    print("    Nulls", nulls);
    print("    From range stream", fromStream);
    print("    Generated random", generated);
    print("    Powers of 2", fromFunction);
  }

  /**
   * Filling arrays with {@link Arrays#fill} and generator functions.
   *
   * <p><strong>Key point:</strong> {@code Arrays.fill()} sets every
   * element to the same value. {@code Arrays.setAll()} uses an
   * index-based generator for computed initial values.</p>
   */
  static void fillingArrays() {
    int[] filled = new int[5];
    Arrays.fill(filled, 42);

    // Fill a range only
    int[] partial = new int[8];
    Arrays.fill(partial, 2, 6, 99);

    // setAll with index-based generator (Java 8+)
    int[] squares = new int[8];
    Arrays.setAll(squares, i -> (i + 1) * (i + 1));

    // parallelSetAll for large arrays
    int[] parallel = new int[8];
    Arrays.parallelSetAll(parallel, i -> i * 10);

    print("1.2 fill(42)", filled);
    print("    fill range [2,6)", partial);
    print("    setAll (squares)", squares);
    print("    parallelSetAll", parallel);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 2 — ARRAYS UTILITY CLASS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Key methods from {@link java.util.Arrays}.
   *
   * <p><strong>Key point:</strong> The {@code Arrays} class provides
   * static utility methods for sorting, searching, comparing,
   * copying, and converting arrays. Always prefer these over
   * manual loops for clarity and performance.</p>
   */
  static void arraysUtilityMethods() {
    int[] arr = {5, 3, 8, 1, 9, 2, 7};

    // Sort (mutates the original)
    int[] sorted = arr.clone();
    Arrays.sort(sorted);
    print("2.1 sort()", sorted);

    // Sort a range
    int[] rangeSorted = arr.clone();
    Arrays.sort(rangeSorted, 1, 5);  // sort indices [1, 5)
    print("    sort range [1,5)", rangeSorted);

    // Binary search (array MUST be sorted first)
    int idx = Arrays.binarySearch(sorted, 7);
    printExercise("    binarySearch(7)", "found at index " + idx);

    // Copy
    int[] copy = Arrays.copyOf(arr, arr.length);
    int[] shorter = Arrays.copyOf(arr, 3);
    int[] longer = Arrays.copyOf(arr, 10);  // padded with 0
    int[] range = Arrays.copyOfRange(arr, 2, 5);
    print("    copyOf (exact)", copy);
    print("    copyOf (shorter)", shorter);
    print("    copyOf (longer, padded)", longer);
    print("    copyOfRange [2,5)", range);

    // toString, hashCode, equals
    printExercise("    toString()", Arrays.toString(arr));
    printExercise("    hashCode()", Arrays.hashCode(arr));
    printExercise("    equals(arr, copy)", Arrays.equals(arr, copy));

    // Mismatch (Java 9+): first index where arrays differ
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {1, 2, 9, 4, 5};
    int mismatch = Arrays.mismatch(a, b);
    printExercise("    mismatch([1,2,3..],[1,2,9..])", "index " + mismatch);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 3 — ROTATION
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Rotates an array to the LEFT by one position.
   *
   * <p><strong>How it works:</strong> The first element moves to the end.
   * All other elements shift one position to the left.</p>
   * <pre>[1, 2, 3, 4, 5] → [2, 3, 4, 5, 1]</pre>
   */
  static void rotateLeft() {
    int[] arr = {1, 2, 3, 4, 5};

    // Imperative approach
    int first = arr[0];
    System.arraycopy(arr, 1, arr, 0, arr.length - 1);
    arr[arr.length - 1] = first;
    print("3.1 Rotate left by 1", arr);

    // Stream approach (creates new array)
    int[] original = {1, 2, 3, 4, 5};
    int[] rotated = IntStream.concat(
        IntStream.of(Arrays.copyOfRange(original, 1, original.length)),
        IntStream.of(original[0])
    ).toArray();
    print("    Stream rotate left", rotated);
  }

  /**
   * Rotates an array to the RIGHT by one position.
   *
   * <p><strong>How it works:</strong> The last element moves to the front.
   * All other elements shift one position to the right.</p>
   * <pre>[1, 2, 3, 4, 5] → [5, 1, 2, 3, 4]</pre>
   */
  static void rotateRight() {
    int[] arr = {1, 2, 3, 4, 5};
    int last = arr[arr.length - 1];
    System.arraycopy(arr, 0, arr, 1, arr.length - 1);
    arr[0] = last;
    print("3.2 Rotate right by 1", arr);
  }

  /**
   * Rotates an array by K positions using the slice-and-concat approach.
   *
   * <p><strong>Algorithm:</strong> Split at position K, swap the two halves.
   * For left rotation by K: {@code [K..end] + [0..K)}.</p>
   *
   * <p><strong>Time:</strong> O(n), <strong>Space:</strong> O(n)</p>
   */
  static void rotateByK() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7};
    int k = 3;
    int n = arr.length;
    k = k % n;  // handle k > n

    // Stream approach: concat(subarray[k..n], subarray[0..k])
    int[] rotatedLeft = IntStream.concat(
        Arrays.stream(arr, k, n),
        Arrays.stream(arr, 0, k)
    ).toArray();

    // Right rotation by K = left rotation by (n - k)
    int rightK = n - k;
    int[] rotatedRight = IntStream.concat(
        Arrays.stream(arr, rightK, n),
        Arrays.stream(arr, 0, rightK)
    ).toArray();

    print("3.3 Original", arr);
    print("    Left rotate by " + k, rotatedLeft);
    print("    Right rotate by " + k, rotatedRight);
  }

  /**
   * Rotates an array in-place using the reversal algorithm.
   *
   * <p><strong>Algorithm (left rotate by K):</strong></p>
   * <ol>
   *   <li>Reverse the first K elements</li>
   *   <li>Reverse the remaining N−K elements</li>
   *   <li>Reverse the entire array</li>
   * </ol>
   *
   * <p><strong>Time:</strong> O(n), <strong>Space:</strong> O(1) — no extra array needed!</p>
   *
   * <p>This is the classic interview-favourite in-place rotation algorithm.</p>
   */
  static void rotateByKInPlace() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7};
    int k = 3;
    k = k % arr.length;

    print("3.4 Before in-place rotation", arr);
    reverseRange(arr, 0, k - 1);        // reverse [0..k)
    reverseRange(arr, k, arr.length - 1); // reverse [k..n)
    reverseRange(arr, 0, arr.length - 1); // reverse entire array
    print("    After left rotate by " + k + " (reversal algo)", arr);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 4 — REVERSAL & SHIFTING
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Reverses an array — imperative, in-place approach.
   *
   * <p><strong>Algorithm:</strong> Swap elements from both ends moving inward.
   * {@code swap(arr[i], arr[n-1-i])} for i from 0 to n/2.</p>
   */
  static void reverseArray() {
    int[] arr = {10, 20, 30, 40, 50};
    int[] reversed = arr.clone();
    for (int i = 0, j = reversed.length - 1; i < j; i++, j--) {
      System.out.println("Swapping elements at indices " + i + " and " + j);
      int temp = reversed[i];
      reversed[i] = reversed[j];
      reversed[j] = temp;
    }

    for (int i = 0; i < arr.length / 2; i++) {
      int tmp = arr[i];
      arr[i] = arr[arr.length - 1 - i];
      arr[arr.length - 1 - i] = tmp;
    }
    print("4.1 Original", arr);
    print("    Reversed (in-place)", reversed);
  }

  /**
   * Reverses an array using Stream API.
   *
   * <p><strong>Key point:</strong> Streams don't have a built-in reverse.
   * Common workaround: iterate indices in reverse and map to elements.</p>
   */
  static void reverseWithStream() {
    int[] arr = {10, 20, 30, 40, 50};
    int n = arr.length;

    int[] reversed = IntStream.rangeClosed(1, n)
        .map(i -> arr[n - i])
        .toArray();

    // For String/Object arrays, use boxed collections
    String[] words = {"alpha", "bravo", "charlie", "delta"};
    String[] reversedWords = IntStream.rangeClosed(1, words.length)
        .mapToObj(i -> words[words.length - i])
        .toArray(String[]::new);

    print("4.2 Reversed (stream)", reversed);
    printExercise("    Reversed strings", Arrays.toString(reversedWords));
  }

  /**
   * Shifts all elements left/right by N positions, filling gaps with a value.
   */
  static void shiftElements() {
    int[] arr = {10, 20, 30, 40, 50};

    // Shift left by 2: [30, 40, 50, 0, 0]
    int[] shiftedLeft = new int[arr.length];
    System.arraycopy(arr, 2, shiftedLeft, 0, arr.length - 2);

    // Shift right by 2: [0, 0, 10, 20, 30]
    int[] shiftedRight = new int[arr.length];
    System.arraycopy(arr, 0, shiftedRight, 2, arr.length - 2);

    print("4.3 Shift left by 2", shiftedLeft);
    print("    Shift right by 2", shiftedRight);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 5 — MERGING TWO ARRAYS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Simple concatenation of two arrays.
   *
   * <p><strong>Key point:</strong> {@code IntStream.concat()} is the
   * cleanest way to merge primitive arrays with streams.</p>
   */
  static void mergeConcatenate() {
    int[] a = {1, 3, 5};
    int[] b = {2, 4, 6};

    // Stream approach
    int[] merged = IntStream.concat(
        Arrays.stream(a), Arrays.stream(b)
    ).toArray();

    // System.arraycopy approach (no boxing, fast)
    int[] arrayCopyMerge = new int[a.length + b.length];
    System.arraycopy(a, 0, arrayCopyMerge, 0, a.length);
    System.arraycopy(b, 0, arrayCopyMerge, a.length, b.length);

    print("5.1 Concat (stream)", merged);
    print("    Concat (arraycopy)", arrayCopyMerge);
  }

  /**
   * Merges two sorted arrays into one sorted array (merge step
   * of merge-sort).
   *
   * <p><strong>Algorithm:</strong> Two-pointer technique — compare
   * heads of both arrays, take the smaller, advance that pointer.</p>
   *
   * <p><strong>Time:</strong> O(n + m), <strong>Space:</strong> O(n + m)</p>
   */
  static void mergeSortedArrays() {
    int[] a = {1, 3, 5, 7, 9};
    int[] b = {2, 4, 6, 8, 10};

    // Two-pointer merge
    int[] merged = new int[a.length + b.length];
    int i = 0, j = 0, k = 0;
    while (i < a.length && j < b.length) {
      merged[k++] = (a[i] <= b[j]) ? a[i++] : b[j++];
    }
    while (i < a.length) {
      merged[k++] = a[i++];
    }
    while (j < b.length) {
      merged[k++] = b[j++];
    }

    // Stream shortcut (sorts after concat, not a true merge)
    int[] streamMerge = IntStream.concat(
        Arrays.stream(a), Arrays.stream(b)
    ).sorted().toArray();

    print("5.2 Merge sorted (two-pointer)", merged);
    print("    Merge sorted (stream+sort)", streamMerge);
  }

  /**
   * Interleaves two arrays element by element.
   * <pre>[1,3,5] + [2,4,6] → [1,2,3,4,5,6]</pre>
   */
  static void mergeAlternating() {
    int[] a = {1, 3, 5};
    int[] b = {2, 4, 6};
    int maxLen = Math.max(a.length, b.length);

    int[] interleaved = IntStream.range(0, maxLen)
        .flatMap(i -> {
          IntStream.Builder builder = IntStream.builder();
          if (i < a.length) {
            builder.accept(a[i]);
          }
          if (i < b.length) {
            builder.accept(b[i]);
          }
          return builder.build();
        }).toArray();

    print("5.3 Interleaved", interleaved);
  }

  /**
   * Merges two arrays, removing duplicates, preserving sorted order.
   */
  static void mergeWithoutDuplicates() {
    int[] a = {1, 2, 3, 5, 7};
    int[] b = {2, 4, 5, 6, 8};

    int[] merged = IntStream.concat(Arrays.stream(a), Arrays.stream(b))
        .distinct()
        .sorted()
        .toArray();

    print("5.4 Merged without duplicates", merged);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 6 — COMPARING & DIFFERENCES
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Comparing arrays with {@code Arrays.equals()} and {@code ==}.
   *
   * <p><strong>Key point:</strong> {@code ==} compares references,
   * NOT contents. Always use {@code Arrays.equals()} for value
   * comparison. For nested arrays, use {@code deepEquals()}.</p>
   */
  static void compareArraysEquals() {
    int[] a = {1, 2, 3};
    int[] b = {1, 2, 3};
    int[] c = a;

    printExercise("6.1 a == b (same content, diff ref)", a == b);
    printExercise("    a == c (same reference)", a == c);
    printExercise("    Arrays.equals(a, b)", Arrays.equals(a, b));
  }

  /**
   * Deep comparison for nested/Object arrays.
   *
   * <p><strong>Key point:</strong> {@code Arrays.equals()} uses
   * {@code .equals()} on elements, which for arrays means reference
   * comparison. {@code Arrays.deepEquals()} recursively compares
   * nested array elements.</p>
   */
  static void compareArraysDeepEquals() {
    int[][] a = {{1, 2}, {3, 4}};
    int[][] b = {{1, 2}, {3, 4}};

    printExercise("6.2 Arrays.equals(2D, 2D)",
        Arrays.equals(a, b) + " (WRONG — compares refs)");
    printExercise("    Arrays.deepEquals(2D, 2D)", Arrays.deepEquals(a, b) + " (CORRECT)");
  }

  /**
   * Finding the first index where two arrays differ.
   *
   * <p><strong>Key point:</strong> {@code Arrays.mismatch()} (Java 9+)
   * returns the index of the first mismatch, or -1 if equal.</p>
   */
  static void findMismatchIndex() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {1, 2, 9, 4, 5};
    int[] c = {1, 2, 3, 4, 5};

    printExercise("6.3 mismatch(a, b)", Arrays.mismatch(a, b) + " (differ at index 2)");
    printExercise("    mismatch(a, c)", Arrays.mismatch(a, c) + " (-1 = identical)");
  }

  /**
   * Element-by-element comparison using streams.
   */
  static void compareWithStream() {
    int[] a = {10, 20, 30, 40, 50};
    int[] b = {10, 25, 30, 45, 50};

    // Find indices where elements differ
    int[] diffIndices = IntStream.range(0, Math.min(a.length, b.length))
        .filter(i -> a[i] != b[i])
        .toArray();

    // Create diff report
    String report = IntStream.range(0, Math.min(a.length, b.length))
        .filter(i -> a[i] != b[i])
        .mapToObj(i -> "index %d: %d vs %d".formatted(i, a[i], b[i]))
        .collect(Collectors.joining(", "));

    print("6.4 Diff indices", diffIndices);
    printExercise("    Diff report", report);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 7 — SET OPERATIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Union of two arrays (all elements from both, no duplicates).
   */
  static void unionOfArrays() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {3, 4, 5, 6, 7};

    int[] union = IntStream.concat(Arrays.stream(a), Arrays.stream(b))
        .distinct()
        .sorted()
        .toArray();

    print("7.1 Union", union);
  }

  /**
   * Intersection of two arrays (elements present in BOTH).
   */
  static void intersectionOfArrays() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {3, 4, 5, 6, 7};

    Set<Integer> setB = Arrays.stream(b).boxed().collect(Collectors.toSet());
    int[] intersection = Arrays.stream(a)
        .filter(setB::contains)
        .distinct()
        .sorted()
        .toArray();

    print("7.2 Intersection", intersection);
  }

  /**
   * Difference of two arrays (elements in A but NOT in B).
   */
  static void differenceOfArrays() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {3, 4, 5, 6, 7};

    Set<Integer> setB = Arrays.stream(b).boxed().collect(Collectors.toSet());
    int[] difference = Arrays.stream(a)
        .filter(x -> !setB.contains(x))
        .toArray();

    Set<Integer> setA = Arrays.stream(a).boxed().collect(Collectors.toSet());
    int[] bMinusA = Arrays.stream(b)
        .filter(x -> !setA.contains(x))
        .toArray();

    print("7.3 A − B (in A, not B)", difference);
    print("    B − A (in B, not A)", bMinusA);
  }

  /**
   * Symmetric difference: elements in A or B but NOT in both.
   */
  static void symmetricDifference() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {3, 4, 5, 6, 7};

    Set<Integer> setA = Arrays.stream(a).boxed().collect(Collectors.toSet());
    Set<Integer> setB = Arrays.stream(b).boxed().collect(Collectors.toSet());

    int[] symDiff = IntStream.concat(
        Arrays.stream(a).filter(x -> !setB.contains(x)),
        Arrays.stream(b).filter(x -> !setA.contains(x))
    ).sorted().toArray();

    print("7.4 Symmetric diff (A △ B)", symDiff);
  }

  /**
   * Finding common elements across three or more arrays.
   */
  static void commonElementsMultipleArrays() {
    int[] a = {1, 2, 3, 4, 5};
    int[] b = {2, 3, 4, 6, 7};
    int[] c = {3, 4, 5, 7, 8};

    Set<Integer> setB = Arrays.stream(b).boxed().collect(Collectors.toSet());
    Set<Integer> setC = Arrays.stream(c).boxed().collect(Collectors.toSet());

    int[] common = Arrays.stream(a)
        .filter(x -> setB.contains(x) && setC.contains(x))
        .toArray();

    // Generic approach for N arrays using reduce
    int[][] allArrays = {a, b, c};
    Optional<Set<Integer>> commonAll = Arrays.stream(allArrays)
        .map(arr -> Arrays.stream(arr).boxed().collect(Collectors.toSet()))
        .reduce((s1, s2) -> {
          s1.retainAll(s2);
          return s1;
        });

    print("7.5 Common across 3 arrays", common);
    printExercise("    Using reduce(retainAll)", commonAll.orElse(Set.of()));
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 8 — SEARCHING & FILTERING
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Linear search — O(n).
   */
  static void linearSearch() {
    int[] arr = {64, 25, 12, 22, 11};
    int target = 22;

    OptionalInt index = IntStream.range(0, arr.length)
        .filter(i -> arr[i] == target)
        .findFirst();

    printExercise("8.1 Linear search for " + target,
        index.isPresent() ? "found at index " + index.getAsInt() : "not found");
  }

  /**
   * Binary search — O(log n), requires sorted array.
   *
   * <p><strong>Key point:</strong> {@code Arrays.binarySearch()} returns
   * the index if found, or {@code -(insertion point) - 1} if not found.
   * The insertion point is where the element would be inserted to maintain
   * sorted order.</p>
   */
  static void binarySearchDemo() {
    int[] sorted = {10, 20, 30, 40, 50, 60, 70};

    int found = Arrays.binarySearch(sorted, 40);
    int notFound = Arrays.binarySearch(sorted, 35);

    printExercise("8.2 binarySearch(40)", "index " + found);
    printExercise("    binarySearch(35)",
        notFound + " (insertion point = " + (-(notFound) - 1) + ")");
  }

  /**
   * Filtering arrays with Stream API.
   */
  static void filterWithStream() {
    int[] arr = {15, 22, 8, 37, 42, 3, 58, 19, 66, 11};

    int[] evens = Arrays.stream(arr).filter(x -> x % 2 == 0).toArray();
    int[] greaterThan20 = Arrays.stream(arr).filter(x -> x > 20).toArray();
    int[] inRange = Arrays.stream(arr).filter(x -> x >= 10 && x <= 50).toArray();

    print("8.3 Evens only", evens);
    print("    Greater than 20", greaterThan20);
    print("    In range [10, 50]", inRange);
  }

  /**
   * Finding first and last occurrence of an element.
   */
  static void findFirstAndLast() {
    int[] arr = {5, 3, 7, 3, 8, 3, 9};
    int target = 3;

    int first = IntStream.range(0, arr.length)
        .filter(i -> arr[i] == target)
        .findFirst().orElse(-1);

    int last = IntStream.iterate(arr.length - 1, i -> i >= 0, i -> i - 1)
        .filter(i -> arr[i] == target)
        .findFirst().orElse(-1);

    printExercise("8.4 First occurrence of " + target, "index " + first);
    printExercise("    Last occurrence of " + target, "index " + last);
  }

  /**
   * Finding the Kth largest and Kth smallest elements.
   */
  static void findKthLargestSmallest() {
    int[] arr = {64, 25, 12, 22, 11, 90, 45};
    int k = 3;

    int kthSmallest = Arrays.stream(arr).sorted().skip(k - 1).findFirst().orElseThrow();
    int kthLargest = Arrays.stream(arr)
        .boxed()
        .sorted(Comparator.reverseOrder())
        .skip(k - 1)
        .findFirst().orElseThrow();

    printExercise("8.5 " + k + "rd smallest", kthSmallest);
    printExercise("    " + k + "rd largest", kthLargest);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 9 — MATH OPERATIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Sum and product of array elements.
   *
   * <p><strong>Key point:</strong> {@code IntStream.sum()} is the
   * most idiomatic way. For product, use {@code reduce(1, (a,b) -> a*b)}
   * with identity 1.</p>
   */
  static void sumAndProduct() {
    int[] arr = {2, 3, 5, 7, 11};

    int sum = Arrays.stream(arr).sum();
    long product = Arrays.stream(arr).asLongStream()
        .reduce(1L, (a, b) -> a * b);

    printExercise("9.1 Sum", sum);
    printExercise("    Product", product);
  }

  /**
   * Min, max, average.
   */
  static void minMaxAvg() {
    int[] arr = {15, 22, 8, 37, 42, 3, 58};
    int min = Arrays.stream(arr).min().orElseThrow();
    int max = Arrays.stream(arr).max().orElseThrow();
    double avg = Arrays.stream(arr).average().orElseThrow();

    printExercise("9.2 Min", min);
    printExercise("    Max", max);
    printExercise("    Average", "%.2f".formatted(avg));
  }

  /**
   * Full summary statistics in a single pass.
   */
  static void summaryStatisticsDemo() {
    int[] arr = {15, 22, 8, 37, 42, 3, 58};
    IntSummaryStatistics stats = Arrays.stream(arr).summaryStatistics();

    printExercise("9.3 SummaryStatistics",
        "count=%d, sum=%d, min=%d, max=%d, avg=%.2f"
            .formatted(stats.getCount(), (long) stats.getSum(),
                stats.getMin(), stats.getMax(), stats.getAverage()));
  }

  /**
   * Cumulative (prefix) sum of array elements.
   *
   * <p><strong>Key point:</strong> {@code Arrays.parallelPrefix()} applies
   * a binary operator cumulatively across the array in-place. For
   * addition, this produces a running total.</p>
   */
  static void cumulativeSum() {
    int[] arr = {1, 2, 3, 4, 5};

    // parallelPrefix — in-place cumulative operation
    int[] cumSum = arr.clone();
    Arrays.parallelPrefix(cumSum, Integer::sum);

    // Stream approach (not in-place)
    int[] streamCumSum = new int[arr.length];
    IntStream.range(0, arr.length).forEach(i ->
        streamCumSum[i] = (i == 0) ? arr[0] : streamCumSum[i - 1] + arr[i]);

    print("9.4 Cumulative sum (parallelPrefix)", cumSum);
    print("    Cumulative sum (stream)", streamCumSum);
  }

  /**
   * Dot product of two vectors.
   */
  static void dotProduct() {
    int[] a = {1, 2, 3, 4};
    int[] b = {5, 6, 7, 8};

    int dot = IntStream.range(0, Math.min(a.length, b.length))
        .map(i -> a[i] * b[i])
        .sum();

    printExercise("9.5 Dot product", dot + " (1×5 + 2×6 + 3×7 + 4×8)");
  }

  /**
   * Normalise array values to the range [0, 1].
   *
   * <p><strong>Formula:</strong> {@code normalised[i] = (arr[i] - min) / (max - min)}</p>
   */
  static void normalise() {
    int[] arr = {10, 50, 30, 80, 20};
    int min = Arrays.stream(arr).min().orElseThrow();
    int max = Arrays.stream(arr).max().orElseThrow();
    double range = max - min;

    double[] normalised = Arrays.stream(arr)
        .mapToDouble(x -> (x - min) / range)
        .toArray();

    printExercise("9.6 Normalised [0,1]", Arrays.toString(normalised));
  }

  /**
   * Element-wise operations between two arrays.
   */
  static void elementWiseOperations() {
    int[] a = {10, 20, 30, 40};
    int[] b = {1, 2, 3, 4};

    int[] addition = IntStream.range(0, a.length).map(i -> a[i] + b[i]).toArray();
    int[] subtraction = IntStream.range(0, a.length).map(i -> a[i] - b[i]).toArray();
    int[] multiplication = IntStream.range(0, a.length).map(i -> a[i] * b[i]).toArray();
    double[] division =
        IntStream.range(0, a.length).mapToDouble(i -> (double) a[i] / b[i]).toArray();

    print("9.7 a + b", addition);
    print("    a - b", subtraction);
    print("    a * b", multiplication);
    printExercise("    a / b", Arrays.toString(division));
  }

  /**
   * Range and variance.
   */
  static void rangeAndVariance() {
    int[] arr = {4, 8, 15, 16, 23, 42};

    int range = Arrays.stream(arr).max().orElse(0) - Arrays.stream(arr).min().orElse(0);
    double mean = Arrays.stream(arr).average().orElse(0);
    double variance = Arrays.stream(arr)
        .mapToDouble(x -> Math.pow(x - mean, 2))
        .average().orElse(0);
    double stdDev = Math.sqrt(variance);

    printExercise("9.8 Range", range);
    printExercise("    Mean", "%.2f".formatted(mean));
    printExercise("    Variance", "%.2f".formatted(variance));
    printExercise("    Std Deviation", "%.2f".formatted(stdDev));
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 10 — PREFIX SUMS & SLIDING WINDOWS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Prefix sum array for O(1) range sum queries.
   *
   * <p><strong>Key point:</strong> Build prefix once in O(n), then answer
   * any range sum query [i, j] in O(1) with
   * {@code prefix[j+1] - prefix[i]}.</p>
   */
  static void prefixSum() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};

    // prefix[i] = sum of arr[0..i-1]
    int[] prefix = new int[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      prefix[i + 1] = prefix[i] + arr[i];
    }

    print("10.1 Original", arr);
    print("     Prefix sum", prefix);
  }

  /**
   * Using prefix sum for O(1) range sum queries.
   */
  static void rangeSumQuery() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
    int[] prefix = new int[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
      prefix[i + 1] = prefix[i] + arr[i];
    }

    // Sum of arr[2..5] (indices inclusive)
    int from = 2, to = 5;
    int rangeSum = prefix[to + 1] - prefix[from];

    printExercise("10.2 Range sum [%d..%d]".formatted(from, to),
        "%d (=%s)".formatted(rangeSum,
            Arrays.stream(arr, from, to + 1)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("+"))));
  }

  /**
   * Sliding window maximum — find the max in each window of size K.
   *
   * <p><strong>Use case:</strong> Stock price analysis, signal processing.</p>
   */
  static void slidingWindowMax() {
    int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
    int k = 3;

    int[] windowMaxes = IntStream.rangeClosed(0, arr.length - k)
        .map(i -> Arrays.stream(arr, i, i + k).max().orElse(0))
        .toArray();

    print("10.3 Window max (k=%d)".formatted(k), windowMaxes);
    /**
     jshell> int[] windowMaxes2 = IntStream.iterate(0, i -> i < arr.length - k, i->  ++i).peek(System.out::print).map(i -> Arrays.stream(arr, i, i + k).max().orElse(0)).toArray();
     01234windowMaxes2 ==> int[5] { 4, 4, 5, 9, 9 }

     jshell> arr
     arr ==> int[8] { 3, 1, 4, 1, 5, 9, 2, 6 }

     jshell> int[] windowMaxes = IntStream.rangeClosed(0, arr.length - k).map(i -> Arrays.stream(arr, i, i + k).max().orElse(0)).toArray();
     windowMaxes ==> int[6] { 4, 4, 5, 9, 9, 9 }

     jshell> windowMaxes
     windowMaxes ==> int[6] { 4, 4, 5, 9, 9, 9 }

     jshell> windowMaxes2
     windowMaxes2 ==> int[5] { 4, 4, 5, 9, 9 }
     **/
  }

  /**
   * Sliding window average.
   */
  static void slidingWindowAverage() {
    int[] arr = {10, 20, 30, 40, 50, 60, 70};
    int k = 3;

    double[] windowAvgs = IntStream.rangeClosed(0, arr.length - k)
        .mapToDouble(i -> Arrays.stream(arr, i, i + k).average().orElse(0))
        .toArray();

    printExercise("10.4 Window avg (k=%d)".formatted(k), Arrays.toString(windowAvgs));
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 11 — FREQUENCY, DUPLICATES & DISTINCT
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Counting frequency of each element using streams.
   */
  static void countFrequencies() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};

    Map<Integer, Long> freq = Arrays.stream(arr)
        .boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

    // Sorted by value descending
    String sorted = freq.entrySet().stream()
        .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
        .map(e -> "%d→%d".formatted(e.getKey(), e.getValue()))
        .collect(Collectors.joining(", "));

    printExercise("11.1 Frequencies", sorted);
  }

  /**
   * Finding duplicate elements.
   */
  static void findDuplicates() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};

    Set<Integer> seen = new HashSet<>();
    int[] duplicates = Arrays.stream(arr)
        .filter(x -> !seen.add(x))  // add returns false if already present
        .distinct()
        .toArray();

    print("11.2 Duplicates", duplicates);
  }

  /**
   * Removing duplicates (keeping first occurrence).
   */
  static void removeDuplicates() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};

    int[] unique = Arrays.stream(arr).distinct().toArray();
    print("11.3 Distinct (order preserved)", unique);
  }

  /**
   * Removing duplicates from a SORTED array in-place.
   *
   * <p><strong>Algorithm:</strong> Two-pointer — slow pointer marks
   * the end of unique elements, fast pointer scans ahead.</p>
   */
  static void removeDuplicatesSorted() {
    int[] arr = {1, 1, 2, 2, 2, 3, 4, 4, 5, 5, 5};
    int slow = 0;
    for (int fast = 1; fast < arr.length; fast++) {
      if (arr[fast] != arr[slow]) {
        arr[++slow] = arr[fast];
      }
    }
    int[] result = Arrays.copyOf(arr, slow + 1);
    print("11.4 Dedup sorted (in-place)", result);
  }

  /**
   * Using distinct() with boxed streams for Object arrays.
   */
  static void distinctWithStream() {
    String[] words = {"apple", "banana", "apple", "cherry", "banana", "date"};

    String[] unique = Arrays.stream(words).distinct().toArray(String[]::new);
    long uniqueCount = Arrays.stream(words).distinct().count();

    printExercise("11.5 Distinct strings", Arrays.toString(unique));
    printExercise("    Unique count", uniqueCount);
  }

  /**
   * Finding the most frequent element.
   */
  static void mostFrequentElement() {
    int[] arr = {3, 1, 4, 1, 5, 1, 2, 6, 5, 3, 5};

    int mostFrequent = Arrays.stream(arr).boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElseThrow();

    printExercise("11.6 Most frequent element", mostFrequent);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 12 — PARTITIONING, GROUPING & CHUNKING
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Partitions array into two groups by a predicate.
   */
  static void partitionByPredicate() {
    int[] arr = {15, 22, 8, 37, 42, 3, 58, 19};

    Map<Boolean, List<Integer>> partitioned = Arrays.stream(arr).boxed()
        .collect(Collectors.partitioningBy(x -> x >= 20));

    printExercise("12.1 Partition ≥ 20", "true=" + partitioned.get(true));
    printExercise("    ", "false=" + partitioned.get(false));
  }

  /**
   * Groups elements by a computed key.
   */
  static void groupByModulo() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    Map<Integer, List<Integer>> grouped = Arrays.stream(arr).boxed()
        .collect(Collectors.groupingBy(x -> x % 3));

    printExercise("12.2 Group by mod 3", grouped);
  }

  /**
   * Splits an array into fixed-size chunks.
   *
   * <p><strong>Use case:</strong> Batch processing, pagination.</p>
   */
  static void chunkArray() {
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    int chunkSize = 3;

    List<int[]> chunks = IntStream.iterate(0, i -> i < arr.length, i -> i + chunkSize)
        .mapToObj(i -> Arrays.copyOfRange(arr, i, Math.min(i + chunkSize, arr.length)))
        .toList();

    //IntStream.iterate(0, i -> i + k < arr.length, i -> i++).mapToObj(i -> copyOfRange(arr, i, i + k)).max().orElse(-2)

    System.out.println("  ► 12.3 Chunks of " + chunkSize + ":");
    chunks.forEach(chunk -> System.out.println("    " + Arrays.toString(chunk)));
  }

  /**
   * Separates even and odd numbers into two arrays.
   */
  static void separateEvenOdd() {
    int[] arr = {12, 7, 15, 22, 9, 30, 11, 44};

    int[] evens = Arrays.stream(arr).filter(x -> x % 2 == 0).toArray();
    int[] odds = Arrays.stream(arr).filter(x -> x % 2 != 0).toArray();

    print("12.4 Evens", evens);
    print("     Odds", odds);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 13 — SORTING VARIATIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Sorting ascending and descending.
   *
   * <p><strong>Key point:</strong> Primitive arrays can only be sorted
   * ascending with {@code Arrays.sort()}. For descending, either
   * reverse after sorting, or use boxed arrays with a Comparator.</p>
   */
  static void sortAscDesc() {
    int[] arr = {5, 3, 8, 1, 9, 2, 7};

    int[] asc = Arrays.stream(arr).sorted().toArray();
    int[] desc = Arrays.stream(arr).boxed()
        .sorted(Comparator.reverseOrder())
        .mapToInt(Integer::intValue).toArray();

    print("13.1 Ascending", asc);
    print("     Descending", desc);
  }

  /**
   * Sorting by absolute value.
   */
  static void sortByAbsoluteValue() {
    int[] arr = {-5, 3, -8, 1, -9, 2, -7};

    int[] sorted = Arrays.stream(arr).boxed()
        .sorted(Comparator.comparingInt(Math::abs))
        .mapToInt(Integer::intValue).toArray();

    print("13.2 Sorted by |value|", sorted);
  }

  /**
   * Sorting by frequency (most frequent first, ties by value).
   */
  static void sortByFrequency() {
    int[] arr = {3, 1, 4, 1, 5, 1, 2, 5, 3};

    Map<Integer, Long> freq = Arrays.stream(arr).boxed()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

    int[] sorted = Arrays.stream(arr).boxed()
        .sorted(Comparator.<Integer, Long>comparing(freq::get).reversed()
            .thenComparing(Comparator.naturalOrder()))
        .mapToInt(Integer::intValue).toArray();

    print("13.3 Sorted by frequency", sorted);
  }

  /**
   * Dutch National Flag problem: sort array of 0s, 1s, and 2s in-place.
   *
   * <p><strong>Algorithm:</strong> Three-pointer partitioning.
   * {@code low} marks the boundary of 0s, {@code high} marks the
   * boundary of 2s, {@code mid} scans through.</p>
   *
   * <p><strong>Time:</strong> O(n), <strong>Space:</strong> O(1)</p>
   */
  static void dutchNationalFlag() {
    int[] arr = {2, 0, 1, 2, 1, 0, 0, 2, 1, 0};
    int low = 0, mid = 0, high = arr.length - 1;

    while (mid <= high) {
      switch (arr[mid]) {
        case 0 -> {
          swap(arr, low++, mid++);
        }
        case 1 -> mid++;
        case 2 -> {
          swap(arr, mid, high--);
        }
      }
    }

    print("13.4 Dutch National Flag (0,1,2 sorted)", arr);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 14 — TWO-POINTER & IN-PLACE
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Two-sum on a sorted array.
   *
   * <p><strong>Algorithm:</strong> Left pointer at start, right at end.
   * If sum too small, move left right. If too big, move right left.</p>
   *
   * <p><strong>Time:</strong> O(n), <strong>Space:</strong> O(1)</p>
   */
  static void twoSumSorted() {
    int[] arr = {2, 7, 11, 15, 20, 25};
    int target = 22;

    int left = 0, right = arr.length - 1;
    String result = "not found";
    while (left < right) {
      int sum = arr[left] + arr[right];
      if (sum == target) {
        result = "%d + %d = %d (indices %d, %d)".formatted(
            arr[left], arr[right], target, left, right);
        break;
      } else if (sum < target) {
        left++;
      } else {
        right--;
      }
    }
    printExercise("14.1 Two-sum (target=" + target + ")", result);

    // sub-array-sum

    int[] rst = new int[arr.length];
    int i = 0, j = 1, sum = 0;
    while (sum != target) {
      sum = Arrays.stream(arr, i, j).sum();
      if (sum == target) {
        rst = Arrays.copyOfRange(arr, i, j);
        break;
      }
      if (sum < target) {
        ++j;
        if (j >= arr.length) {
          rst = new int[] {};
          break;
        }
      }
      if (sum > target) {
        ++i;
        if (i > j) {
          rst = new int[] {};
          break;
        }
      }
    }
    System.out.println("14.1-B Two-sum (target=" + target + ") " + Arrays.toString(rst));
  }

  /**
   * Moves all zeroes to the end while preserving order of non-zero elements.
   *
   * <p><strong>Time:</strong> O(n), <strong>Space:</strong> O(1)</p>
   */
  static void moveZeroesToEnd() {
    int[] arr = {0, 1, 0, 3, 12, 0, 5};
    int insertPos = 0;

    for (int num : arr) {
      if (num != 0) {
        arr[insertPos++] = num;
      }
    }
    while (insertPos < arr.length) {
      arr[insertPos++] = 0;
    }

    print("14.2 Move zeroes to end", arr);
  }

  /**
   * Removes duplicates from a sorted array in-place, returns new length.
   */
  static void removeDuplicatesInPlace() {
    int[] arr = {1, 1, 2, 2, 2, 3, 4, 4, 5};
    int slow = 0;
    for (int fast = 1; fast < arr.length; fast++) {
      if (arr[fast] != arr[slow]) {
        arr[++slow] = arr[fast];
      }
    }
    int newLength = slow + 1;

    print("14.3 Dedup in-place (len=%d)".formatted(newLength),
        Arrays.copyOf(arr, newLength));
  }

  /**
   * Partitions an array around a pivot value.
   *
   * <p>Elements less than pivot come first, then equal, then greater.
   * This is the partition step of QuickSort.</p>
   */
  static void partitionAroundPivot() {
    int[] arr = {8, 3, 5, 7, 2, 9, 1, 6, 4};
    int pivot = 5;

    int[] partitioned = IntStream.concat(
        IntStream.concat(
            Arrays.stream(arr).filter(x -> x < pivot),
            Arrays.stream(arr).filter(x -> x == pivot)),
        Arrays.stream(arr).filter(x -> x > pivot)
    ).toArray();

    print("14.4 Partition around pivot=" + pivot, partitioned);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  SECTION 15 — CONVERSIONS
  // ═══════════════════════════════════════════════════════════════════

  /**
   * Array → List conversions.
   *
   * <p><strong>Key point:</strong> {@code Arrays.asList()} returns a
   * fixed-size list backed by the array (changes reflect both ways).
   * {@code List.of()} returns an unmodifiable list.
   * Neither works with primitive arrays — you need boxing.</p>
   */
  static void arrayToListConversions() {
    String[] strArr = {"alpha", "bravo", "charlie"};

    // Fixed-size, mutable elements but not resizable
    List<String> fixedList = Arrays.asList(strArr);

    // Truly unmodifiable
    List<String> unmodifiable = List.of(strArr);

    // Mutable copy
    List<String> mutable = new ArrayList<>(Arrays.asList(strArr));

    // Primitive → List (requires boxing)
    int[] intArr = {1, 2, 3, 4, 5};
    List<Integer> intList = Arrays.stream(intArr).boxed().toList();

    printExercise("15.1 Arrays.asList()", fixedList);
    printExercise("    List.of()", unmodifiable);
    printExercise("    Primitive int[] → List<Integer>", intList);
  }

  /**
   * List → Array conversions.
   */
  static void listToArrayConversions() {
    List<String> list = List.of("alpha", "bravo", "charlie");

    // Object array
    String[] strArr = list.toArray(new String[0]);

    // With method reference (Java 11+)
    String[] strArr2 = list.toArray(String[]::new);

    // List<Integer> → int[] (requires unboxing)
    List<Integer> intList = List.of(1, 2, 3, 4, 5);
    int[] intArr = intList.stream().mapToInt(Integer::intValue).toArray();

    printExercise("15.2 toArray(new String[0])", Arrays.toString(strArr));
    printExercise("    toArray(String[]::new)", Arrays.toString(strArr2));
    print("    List<Integer> → int[]", intArr);
  }

  /**
   * Primitive ↔ Boxed conversions.
   */
  static void primitiveBoxedConversions() {
    int[] primitive = {1, 2, 3, 4, 5};

    // int[] → Integer[]
    Integer[] boxed = Arrays.stream(primitive).boxed().toArray(Integer[]::new);

    // Integer[] → int[]
    int[] unboxed = Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();

    // double[] → Double[]
    double[] dPrimitive = {1.1, 2.2, 3.3};
    Double[] dBoxed = Arrays.stream(dPrimitive).boxed().toArray(Double[]::new);

    printExercise("15.3 int[] → Integer[]", Arrays.toString(boxed));
    print("    Integer[] → int[]", unboxed);
    printExercise("    double[] → Double[]", Arrays.toString(dBoxed));
  }

  /**
   * Array ↔ Stream conversions.
   */
  static void arrayToStreamAndBack() {
    int[] arr = {5, 3, 8, 1, 9};

    // Array → Stream
    IntStream stream = Arrays.stream(arr);
    // Can also specify range: Arrays.stream(arr, 1, 4)

    // Stream → Array
    int[] fromStream = IntStream.of(10, 20, 30).toArray();

    // Object array → Stream
    String[] words = {"hello", "world"};
    Stream<String> wordStream = Arrays.stream(words);

    print("15.4 Stream → array", fromStream);
    printExercise("    Stream from array", Arrays.stream(arr).sum() + " (sum via stream)");
  }

  /**
   * Array ↔ Set conversions.
   */
  static void arrayToSetAndBack() {
    int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5};

    // Array → Set
    Set<Integer> set = Arrays.stream(arr).boxed().collect(Collectors.toSet());

    // Ordered set
    Set<Integer> orderedSet = Arrays.stream(arr).boxed()
        .collect(Collectors.toCollection(TreeSet::new));

    // Set → Array
    int[] fromSet = set.stream().mapToInt(Integer::intValue).toArray();

    printExercise("15.5 int[] → Set", set);
    printExercise("    int[] → TreeSet", orderedSet);
    print("    Set → int[]", fromSet);
  }

  // ═══════════════════════════════════════════════════════════════════
  //  HELPERS
  // ═══════════════════════════════════════════════════════════════════

  private static void reverseRange(int[] arr, int start, int end) {
    while (start < end) {
      int temp = arr[start];
      arr[start++] = arr[end];
      arr[end--] = temp;
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  private static void print(String label, int[] arr) {
    System.out.println("  ► " + label + ": " + Arrays.toString(arr));
  }

  private static void print(String label, Object[] arr) {
    System.out.println("  ► " + label + ": " + Arrays.toString(arr));
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
