package com.hackerrank.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringReversalTest {

    private StringReversal reversal;

    @BeforeEach
    void setUp() {
        reversal = new StringReversal();
    }

    @Nested
    @DisplayName("reverseWithStringBuilder")
    class ReverseWithStringBuilderTests {

        @Test
        @DisplayName("should reverse a normal string")
        void shouldReverseNormalString() {
            assertEquals("olleh", reversal.reverseWithStringBuilder("hello"));
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNull() {
            assertNull(reversal.reverseWithStringBuilder(null));
        }

        @Test
        @DisplayName("should return empty string for empty input")
        void shouldReturnEmptyForEmpty() {
            assertEquals("", reversal.reverseWithStringBuilder(""));
        }

        @Test
        @DisplayName("should handle single character")
        void shouldHandleSingleChar() {
            assertEquals("a", reversal.reverseWithStringBuilder("a"));
        }

        @Test
        @DisplayName("should reverse palindrome to itself")
        void shouldReversePalindrome() {
            assertEquals("madam", reversal.reverseWithStringBuilder("madam"));
        }
    }

    @Nested
    @DisplayName("reverseWithTwoPointers")
    class ReverseWithTwoPointersTests {

        @Test
        @DisplayName("should reverse a normal string")
        void shouldReverseNormalString() {
            assertEquals("olleh", reversal.reverseWithTwoPointers("hello"));
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNull() {
            assertNull(reversal.reverseWithTwoPointers(null));
        }

        @Test
        @DisplayName("should handle string with spaces")
        void shouldHandleSpaces() {
            assertEquals("dlrow olleh", reversal.reverseWithTwoPointers("hello world"));
        }
    }

    @Nested
    @DisplayName("reverseRecursive")
    class ReverseRecursiveTests {

        @Test
        @DisplayName("should reverse recursively")
        void shouldReverseRecursively() {
            assertEquals("dcba", reversal.reverseRecursive("abcd"));
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNull() {
            assertNull(reversal.reverseRecursive(null));
        }

        @Test
        @DisplayName("should handle single character")
        void shouldHandleSingleChar() {
            assertEquals("x", reversal.reverseRecursive("x"));
        }
    }
}
