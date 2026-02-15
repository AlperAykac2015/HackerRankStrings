package com.hackerrank.character;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharIntOperationsTest {

  private CharIntOperations ops;

  @BeforeEach
  void setUp() {
    ops = new CharIntOperations();
  }

  @Nested
  @DisplayName("isSameCharDifferentCase")
  class IsSameCharDifferentCaseTests {

    @Test
    @DisplayName("should return true for upper and lower case of same letter")
    void shouldReturnTrueForDifferentCase() {
      assertTrue(ops.isSameCharDifferentCase('a', 'A'));
    }

    @Test
    @DisplayName("should return true for lower and upper case of same letter")
    void shouldReturnTrueForReversedCase() {
      assertTrue(ops.isSameCharDifferentCase('Z', 'z'));
    }

    @Test
    @DisplayName("should return false for same case same letter")
    void shouldReturnFalseForSameCase() {
      assertFalse(ops.isSameCharDifferentCase('a', 'a'));
    }

    @Test
    @DisplayName("should return false for different letters")
    void shouldReturnFalseForDifferentLetters() {
      assertFalse(ops.isSameCharDifferentCase('a', 'B'));
    }

    @Test
    @DisplayName("should return false for digits")
    void shouldReturnFalseForDigits() {
      assertFalse(ops.isSameCharDifferentCase('1', '1'));
    }

    @Test
    @DisplayName("should return false for special characters")
    void shouldReturnFalseForSpecialChars() {
      assertFalse(ops.isSameCharDifferentCase('@', '@'));
    }
  }

  @Nested
  @DisplayName("areConsecutive")
  class AreConsecutiveTests {

    @Test
    @DisplayName("should return true for consecutive lowercase letters")
    void shouldReturnTrueForConsecutiveLower() {
      assertTrue(ops.areConsecutive('a', 'b'));
    }

    @Test
    @DisplayName("should return true for consecutive mixed case")
    void shouldReturnTrueForConsecutiveMixedCase() {
      assertTrue(ops.areConsecutive('a', 'B'));
    }

    @Test
    @DisplayName("should return true for reversed consecutive letters")
    void shouldReturnTrueForReversedConsecutive() {
      assertTrue(ops.areConsecutive('D', 'c'));
    }

    @Test
    @DisplayName("should return false for non-consecutive letters")
    void shouldReturnFalseForNonConsecutive() {
      assertFalse(ops.areConsecutive('a', 'c'));
    }

    @Test
    @DisplayName("should return false for same letter different case")
    void shouldReturnFalseForSameLetterDifferentCase() {
      assertFalse(ops.areConsecutive('a', 'A'));
    }

    @Test
    @DisplayName("should return false for digits")
    void shouldReturnFalseForDigits() {
      assertFalse(ops.areConsecutive('1', '2'));
    }

    @Test
    @DisplayName("should return true for y and z")
    void shouldReturnTrueForYZ() {
      assertTrue(ops.areConsecutive('y', 'Z'));
    }
  }

  @Nested
  @DisplayName("countMatchingCharacters")
  class CountMatchingCharactersTests {

    @Test
    @DisplayName("should count matching chars case insensitively")
    void shouldCountMatchingCaseInsensitive() {
      assertEquals(3, ops.countMatchingCharacters("Hello", "hELLO"));
    }

    @Test
    @DisplayName("should return full length for identical strings")
    void shouldReturnFullLengthForIdentical() {
      assertEquals(5, ops.countMatchingCharacters("hello", "hello"));
    }

    @Test
    @DisplayName("should return 0 for completely different strings")
    void shouldReturnZeroForDifferent() {
      assertEquals(0, ops.countMatchingCharacters("abc", "xyz"));
    }

    @Test
    @DisplayName("should compare up to shorter string length")
    void shouldCompareUpToShorterLength() {
      assertEquals(3, ops.countMatchingCharacters("abc", "abcdef"));
    }

    @Test
    @DisplayName("should return 0 for null first string")
    void shouldReturnZeroForNullFirst() {
      assertEquals(0, ops.countMatchingCharacters(null, "abc"));
    }

    @Test
    @DisplayName("should return 0 for null second string")
    void shouldReturnZeroForNullSecond() {
      assertEquals(0, ops.countMatchingCharacters("abc", null));
    }

    @Test
    @DisplayName("should return 0 for empty strings")
    void shouldReturnZeroForEmpty() {
      assertEquals(0, ops.countMatchingCharacters("", ""));
    }

    @Test
    @DisplayName("should match case insensitively at each position")
    void shouldMatchPositionByPosition() {
      // "Java" vs "jAvA" -> j=j, a=A, v=v, a=A -> all 4 match
      assertEquals(4, ops.countMatchingCharacters("Java", "jAvA"));
    }
  }

  @Nested
  @DisplayName("markPositionsSimple")
  class MarkPositionsSimpleTests {

    @Test
    @DisplayName("should mark positions corresponding to char int values")
    void shouldMarkPositions() {
      char[] chars = {'!', 'a', 'c'}; // 'a' = 97, 'c' = 99
      boolean[] marked = new boolean[100];
      ops.markPositionsSimple(chars, marked);
      assertTrue(marked[97]);
      assertFalse(marked[98]);
      assertTrue(marked[99]);
    }

    @Test
    @DisplayName("should skip chars with values out of bounds")
    void shouldSkipOutOfBounds() {
      char[] chars = {'a'}; // 'a' = 97
      boolean[] marked = new boolean[50]; // too small for 'a'
      ops.markPositionsSimple(chars, marked);
      // no exception, all remain false
      for (boolean b : marked) {
        assertFalse(b);
      }
    }

    @Test
    @DisplayName("should handle empty char array")
    void shouldHandleEmptyArray() {
      char[] chars = {};
      boolean[] marked = new boolean[100];
      ops.markPositionsSimple(chars, marked);
      boolean[] expected = new boolean[100];
      assertArrayEquals(expected, marked);
    }

    @Test
    @DisplayName("should mark digit char positions")
    void shouldMarkDigitPositions() {
      char[] chars = {'0', '9'}; // '0' = 48, '9' = 57
      boolean[] marked = new boolean[100];
      ops.markPositionsSimple(chars, marked);
      assertTrue(marked[48]);
      assertTrue(marked[57]);
      assertFalse(marked[49]);
    }
  }
}
