package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class LeftRotationTest {

  @Test
  void shouldReturnRotated_whenLeftRotate3Times() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5};
    int[] expected = new int[] {4, 5, 1, 2, 3};
    // When
    int[] actual = LeftRotation.rotateLeft(arr, 3);
    // Then
    assertArrayEquals(expected, actual);
  }

  @Test
  void shouldReturnRotated_whenLeftRotated4Times() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] expected = new int[] {5, 6, 7, 8, 9, 1, 2, 3, 4};
    // When
    int[] actual = LeftRotation.rotateLeft(arr, 4);
    // Then
    assertArrayEquals(expected, actual);
  }

  @Test
  void reversal_shouldReturnRotated_whenLeftRotate3Times() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5};
    int[] expected = new int[] {4, 5, 1, 2, 3};
    // When
    LeftRotation.rotateLeftReversal(arr, 3);
    // Then
    assertArrayEquals(expected, arr);
  }

  @Test
  void reversal_shouldReturnRotated_whenLeftRotated4Times() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] expected = new int[] {5, 6, 7, 8, 9, 1, 2, 3, 4};
    // When
    LeftRotation.rotateLeftReversal(arr, 4);
    // Then
    assertArrayEquals(expected, arr);
  }

  @Test
  void reversal_shouldHandleKGreaterThanLength() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5};
    int[] expected = new int[] {4, 5, 1, 2, 3};
    // When
    int[] actual = LeftRotation.rotateLeftEfficiently(arr, 3); // 8 % 5 = 3
    // Then
    assertArrayEquals(expected, actual);
  }

  @Test
  void reversal_shouldNotChange_whenKIsZero() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5};
    int[] expected = new int[] {1, 2, 3, 4, 5};
    // When
    LeftRotation.rotateLeftReversal(arr, 0);
    // Then
    assertArrayEquals(expected, arr);
  }

  @Test
  void reversal_shouldNotChange_whenKEqualsLength() {
    // Given
    int[] arr = new int[] {1, 2, 3, 4, 5};
    int[] expected = new int[] {1, 2, 3, 4, 5};
    // When
    LeftRotation.rotateLeftReversal(arr, 5);
    // Then
    assertArrayEquals(expected, arr);
  }

}
