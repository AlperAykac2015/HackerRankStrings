package com.hackerrank.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateRemoverTest {

    private DuplicateRemover remover;

    @BeforeEach
    void setUp() {
        remover = new DuplicateRemover();
    }

    @Nested
    @DisplayName("removeDuplicates - LinkedHashSet approach")
    class LinkedHashSetApproach {

        @Test
        @DisplayName("should remove duplicate characters")
        void shouldRemoveDuplicates() {
            assertEquals("progamin", remover.removeDuplicates("programming"));
        }

        @Test
        @DisplayName("should return same string when no duplicates")
        void shouldReturnSameWhenNoDuplicates() {
            assertEquals("abcdef", remover.removeDuplicates("abcdef"));
        }

        @Test
        @DisplayName("should handle all same characters")
        void shouldHandleAllSame() {
            assertEquals("a", remover.removeDuplicates("aaaa"));
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNull() {
            assertNull(remover.removeDuplicates(null));
        }

        @Test
        @DisplayName("should return empty for empty input")
        void shouldReturnEmptyForEmpty() {
            assertEquals("", remover.removeDuplicates(""));
        }
    }

    @Nested
    @DisplayName("removeDuplicatesWithBuilder - StringBuilder approach")
    class StringBuilderApproach {

        @Test
        @DisplayName("should remove duplicate characters")
        void shouldRemoveDuplicates() {
            assertEquals("helo wrd", remover.removeDuplicatesWithBuilder("hello world"));
        }

        @Test
        @DisplayName("should maintain first occurrence order")
        void shouldMaintainOrder() {
            assertEquals("abcd", remover.removeDuplicatesWithBuilder("abcdabcd"));
        }
    }
}
