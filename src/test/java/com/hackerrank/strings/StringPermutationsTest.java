package com.hackerrank.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringPermutationsTest {

    private StringPermutations permutations;

    @BeforeEach
    void setUp() {
        permutations = new StringPermutations();
    }

    @Test
    @DisplayName("should generate all permutations for 'abc'")
    void shouldGenerateAllPermutations() {
        List<String> result = permutations.findPermutations("abc");
        assertEquals(6, result.size()); // 3! = 6
        assertTrue(result.contains("abc"));
        assertTrue(result.contains("acb"));
        assertTrue(result.contains("bac"));
        assertTrue(result.contains("bca"));
        assertTrue(result.contains("cab"));
        assertTrue(result.contains("cba"));
    }

    @Test
    @DisplayName("should generate single permutation for single char")
    void shouldHandleSingleChar() {
        List<String> result = permutations.findPermutations("a");
        assertEquals(1, result.size());
        assertEquals("a", result.getFirst());
    }

    @Test
    @DisplayName("should generate two permutations for two chars")
    void shouldHandleTwoChars() {
        List<String> result = permutations.findPermutations("ab");
        assertEquals(2, result.size());
        assertTrue(result.contains("ab"));
        assertTrue(result.contains("ba"));
    }

    @Test
    @DisplayName("should return empty list for null")
    void shouldReturnEmptyForNull() {
        List<String> result = permutations.findPermutations(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should generate 24 permutations for 4 characters")
    void shouldGenerate24Permutations() {
        List<String> result = permutations.findPermutations("abcd");
        assertEquals(24, result.size()); // 4! = 24
    }
}
