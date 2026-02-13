package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalindromeCheckerTest {

  private PalindromeChecker checker;

  @BeforeEach
  void setUp() {
    checker = new PalindromeChecker();
  }

  @Nested
  @DisplayName("isPalindrome")
  class IsPalindromeTests {

    @Test
    @DisplayName("should return true for simple palindrome")
    void shouldDetectSimplePalindrome() {
      assertTrue(checker.isPalindrome("madam"));
    }

    @Test
    @DisplayName("should return true for palindrome with spaces")
    void shouldIgnoreSpaces() {
      assertTrue(checker.isPalindrome("nurses run"));
    }

    @Test
    @DisplayName("should return true for palindrome ignoring case")
    void shouldIgnoreCase() {
      assertTrue(checker.isPalindrome("RaceCar"));
    }

    @Test
    @DisplayName("should return false for non-palindrome")
    void shouldReturnFalseForNonPalindrome() {
      assertFalse(checker.isPalindrome("hello"));
    }

    @Test
    @DisplayName("should return false for null")
    void shouldReturnFalseForNull() {
      assertFalse(checker.isPalindrome(null));
    }

    @Test
    @DisplayName("should return true for single character")
    void shouldReturnTrueForSingleChar() {
      assertTrue(checker.isPalindrome("a"));
    }

    @Test
    @DisplayName("should return true for empty string")
    void shouldReturnTrueForEmpty() {
      assertTrue(checker.isPalindrome(""));
    }
  }

  @Nested
  @DisplayName("isPalindromeWithReverse")
  class IsPalindromeWithReverseTests {

    @Test
    @DisplayName("should detect palindrome using reverse approach")
    void shouldDetectPalindrome() {
      assertTrue(checker.isPalindromeWithReverse("level"));
    }

    @Test
    @DisplayName("should return false for non-palindrome")
    void shouldReturnFalseForNonPalindrome() {
      assertFalse(checker.isPalindromeWithReverse("java"));
    }
  }
}
