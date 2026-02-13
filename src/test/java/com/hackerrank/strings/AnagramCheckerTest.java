package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnagramCheckerTest {

  private AnagramChecker checker;

  @BeforeEach
  void setUp() {
    checker = new AnagramChecker();
  }

  @Nested
  @DisplayName("isAnagram - sorting approach")
  class SortingApproach {

    @Test
    @DisplayName("should return true for valid anagrams")
    void shouldDetectAnagram() {
      assertTrue(checker.isAnagram("listen", "silent"));
    }

    @Test
    @DisplayName("should return true ignoring case")
    void shouldIgnoreCase() {
      assertTrue(checker.isAnagram("Triangle", "Integral"));
    }

    @Test
    @DisplayName("should return false for non-anagrams")
    void shouldReturnFalseForNonAnagram() {
      assertFalse(checker.isAnagram("hello", "world"));
    }

    @Test
    @DisplayName("should return false for different lengths")
    void shouldReturnFalseForDifferentLengths() {
      assertFalse(checker.isAnagram("abc", "abcd"));
    }

    @Test
    @DisplayName("should return false for null input")
    void shouldReturnFalseForNull() {
      assertFalse(checker.isAnagram(null, "test"));
      assertFalse(checker.isAnagram("test", null));
    }
  }

  @Nested
  @DisplayName("isAnagramWithMap - frequency map approach")
  class FrequencyMapApproach {

    @Test
    @DisplayName("should return true for valid anagrams")
    void shouldDetectAnagram() {
      assertTrue(checker.isAnagramWithMap("anagram", "nagaram"));
    }

    @Test
    @DisplayName("should return false for non-anagrams")
    void shouldReturnFalseForNonAnagram() {
      assertFalse(checker.isAnagramWithMap("rat", "car"));
    }

    @Test
    @DisplayName("should handle single character anagrams")
    void shouldHandleSingleChar() {
      assertTrue(checker.isAnagramWithMap("a", "a"));
    }
  }

  @Nested
  @DisplayName("isAnagramWithArray - array approach")
  class ArrayApproach {

    @Test
    @DisplayName("should return true for valid anagrams")
    void shouldDetectAnagram() {
      assertTrue(checker.isAnagramWithArray("listen", "silent"));
    }

    @Test
    @DisplayName("should return true ignoring case")
    void shouldIgnoreCase() {
      assertTrue(checker.isAnagramWithArray("Triangle", "Integral"));
    }

    @Test
    @DisplayName("should return false for non-anagrams")
    void shouldReturnFalseForNonAnagram() {
      assertFalse(checker.isAnagramWithArray("hello", "world"));
    }

    @Test
    @DisplayName("should return false for same-length non-anagrams with same char sum")
    void shouldReturnFalseWhenSumMatchesButCharsDoNot() {
      // "ac" and "bb" have different chars but a+c == b+b (97+99 == 98+98)
      assertFalse(checker.isAnagramWithArray("ac", "bb"));
    }

    @Test
    @DisplayName("should return false for different lengths")
    void shouldReturnFalseForDifferentLengths() {
      assertFalse(checker.isAnagramWithArray("abc", "abcd"));
    }

    @Test
    @DisplayName("should return false for null input")
    void shouldReturnFalseForNull() {
      assertFalse(checker.isAnagramWithArray(null, "test"));
      assertFalse(checker.isAnagramWithArray("test", null));
    }

    @Test
    @DisplayName("should handle single character anagrams")
    void shouldHandleSingleChar() {
      assertTrue(checker.isAnagramWithArray("a", "a"));
    }

    @Test
    @DisplayName("should handle empty strings")
    void shouldHandleEmptyStrings() {
      assertTrue(checker.isAnagramWithArray("", ""));
    }

    @Test
    @DisplayName("should handle repeated characters")
    void shouldHandleRepeatedChars() {
      assertTrue(checker.isAnagramWithArray("aabb", "baba"));
      assertFalse(checker.isAnagramWithArray("aabb", "abcc"));
    }
  }
}
