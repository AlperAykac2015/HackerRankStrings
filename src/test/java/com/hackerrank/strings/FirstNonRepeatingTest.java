package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FirstNonRepeatingTest {

  private FirstNonRepeating finder;

  @BeforeEach
  void setUp() {
    finder = new FirstNonRepeating();
  }

  @Nested
  @DisplayName("firstNonRepeatingChar - LinkedHashMap approach")
  class LinkedHashMapApproach {

    @Test
    @DisplayName("should find first non-repeating character")
    void shouldFindFirstNonRepeating() {
      assertEquals('w', finder.firstNonRepeatingChar("swiss"));
    }

    @Test
    @DisplayName("should return first char when all unique")
    void shouldReturnFirstWhenAllUnique() {
      assertEquals('a', finder.firstNonRepeatingChar("abcdef"));
    }

    @Test
    @DisplayName("should return '0' when all characters repeat")
    void shouldReturnZeroWhenAllRepeat() {
      assertEquals('0', finder.firstNonRepeatingChar("aabbcc"));
    }

    @Test
    @DisplayName("should return '0' for null input")
    void shouldReturnZeroForNull() {
      assertEquals('0', finder.firstNonRepeatingChar(null));
    }

    @Test
    @DisplayName("should return '0' for empty input")
    void shouldReturnZeroForEmpty() {
      assertEquals('0', finder.firstNonRepeatingChar(""));
    }

    @Test
    @DisplayName("should handle single character string")
    void shouldHandleSingleChar() {
      assertEquals('z', finder.firstNonRepeatingChar("z"));
    }
  }

  @Nested
  @DisplayName("firstNonRepeatingCharTwoPass - frequency array approach")
  class TwoPassApproach {

    @Test
    @DisplayName("should find first non-repeating character")
    void shouldFindFirstNonRepeating() {
      assertEquals('w', finder.firstNonRepeatingCharTwoPass("swiss"));
    }

    @Test
    @DisplayName("should return '0' when all characters repeat")
    void shouldReturnZeroWhenAllRepeat() {
      assertEquals('0', finder.firstNonRepeatingCharTwoPass("aabb"));
    }

    @Test
    @DisplayName("should find non-repeating in complex string")
    void shouldFindInComplexString() {
      assertEquals('r', finder.firstNonRepeatingCharTwoPass("stress"));
    }
  }

  @Nested
  @DisplayName("firstNonRepeatingCharEfficiently - LinkedHashMap index approach")
  class EfficientApproach {

    @Test
    @DisplayName("should return index of first non-repeating character")
    void shouldReturnIndexOfFirstNonRepeating() {
      // "swiss" -> 's' repeats, 'w' is first non-repeating at index 1
      assertEquals(1, finder.firstNonRepeatingCharEfficiently("swiss"));
    }

    @Test
    @DisplayName("should return 0 when first char is non-repeating")
    void shouldReturnZeroWhenFirstCharIsUnique() {
      assertEquals(0, finder.firstNonRepeatingCharEfficiently("abcdef"));
    }

    @Test
    @DisplayName("should return -1 when all characters repeat")
    void shouldReturnNegativeOneWhenAllRepeat() {
      assertEquals(-1, finder.firstNonRepeatingCharEfficiently("aabbcc"));
    }

    @Test
    @DisplayName("should return '0' for null input")
    void shouldReturnZeroCharForNull() {
      assertEquals('0', finder.firstNonRepeatingCharEfficiently(null));
    }

    @Test
    @DisplayName("should return '0' for empty input")
    void shouldReturnZeroCharForEmpty() {
      assertEquals('0', finder.firstNonRepeatingCharEfficiently(""));
    }

    @Test
    @DisplayName("should return 0 for single character string")
    void shouldHandleSingleChar() {
      assertEquals(0, finder.firstNonRepeatingCharEfficiently("z"));
    }

    @Test
    @DisplayName("should find correct index in complex string")
    void shouldFindInComplexString() {
      assertEquals(0, finder.firstNonRepeatingCharEfficiently("leetcode"));
    }

    @Test
    @DisplayName("should return last index when only last char is unique")
    void shouldReturnLastIndex() {
      // "aabbc" -> 'c' is first non-repeating at index 4
      assertEquals(4, finder.firstNonRepeatingCharEfficiently("aabbc"));
    }
  }
}
