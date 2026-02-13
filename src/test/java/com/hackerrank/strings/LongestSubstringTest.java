package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LongestSubstringTest {

  private LongestSubstring longestSubstring;

  @BeforeEach
  void setUp() {
    longestSubstring = new LongestSubstring();
  }

  @Nested
  @DisplayName("lengthOfLongestSubstring")
  class LengthTests {

    @Test
    @DisplayName("should find longest substring length for 'abcabcbb'")
    void shouldFindLengthForAbcabcbb() {
      assertEquals(3, longestSubstring.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    @DisplayName("should return 1 for all same characters")
    void shouldReturnOneForAllSame() {
      assertEquals(1, longestSubstring.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    @DisplayName("should find longest for 'pwwkew'")
    void shouldFindLengthForPwwkew() {
      assertEquals(3, longestSubstring.lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    @DisplayName("should return 0 for null")
    void shouldReturnZeroForNull() {
      assertEquals(0, longestSubstring.lengthOfLongestSubstring(null));
    }

    @Test
    @DisplayName("should return 0 for empty string")
    void shouldReturnZeroForEmpty() {
      assertEquals(0, longestSubstring.lengthOfLongestSubstring(""));
    }

    @Test
    @DisplayName("should return full length when all unique")
    void shouldReturnFullLengthWhenAllUnique() {
      assertEquals(5, longestSubstring.lengthOfLongestSubstring("abcde"));
    }

    @Test
    @DisplayName("should handle single character")
    void shouldHandleSingleChar() {
      assertEquals(1, longestSubstring.lengthOfLongestSubstring("a"));
    }
  }

  @Nested
  @DisplayName("getLongestSubstring")
  class SubstringTests {

    @Test
    @DisplayName("should return actual longest substring for 'abcabcbb'")
    void shouldReturnSubstringForAbcabcbb() {
      assertEquals("abc", longestSubstring.getLongestSubstring("abcabcbb"));
    }

    @Test
    @DisplayName("should return single char for all same")
    void shouldReturnSingleCharForAllSame() {
      assertEquals("b", longestSubstring.getLongestSubstring("bbbbb"));
    }

    @Test
    @DisplayName("should return 'wke' for 'pwwkew'")
    void shouldReturnWkeForPwwkew() {
      assertEquals("wke", longestSubstring.getLongestSubstring("pwwkew"));
    }

    @Test
    @DisplayName("should return empty for null input")
    void shouldReturnEmptyForNull() {
      assertEquals("", longestSubstring.getLongestSubstring(null));
    }

    @Test
    @DisplayName("should return full string when all unique")
    void shouldReturnFullStringWhenAllUnique() {
      assertEquals("abcde", longestSubstring.getLongestSubstring("abcde"));
    }
  }
}
