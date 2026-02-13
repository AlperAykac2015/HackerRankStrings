package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringCompressorTest {

  private StringCompressor compressor;

  @BeforeEach
  void setUp() {
    compressor = new StringCompressor();
  }

  @Test
  @DisplayName("should compress string with repeated characters")
  void shouldCompressRepeatedChars() {
    assertEquals("a3b2c1", compressor.compress("aaabbc"));
  }

  @Test
  @DisplayName("should compress long runs")
  void shouldCompressLongRuns() {
    assertEquals("a5b3c2", compressor.compress("aaaaabbbcc"));
  }

  @Test
  @DisplayName("should return original if compressed is not shorter")
  void shouldReturnOriginalWhenNotShorter() {
    assertEquals("abc", compressor.compress("abc"));
  }

  @Test
  @DisplayName("should handle single character")
  void shouldHandleSingleChar() {
    assertEquals("a", compressor.compress("a"));
  }

  @Test
  @DisplayName("should handle all same characters")
  void shouldHandleAllSame() {
    assertEquals("a5", compressor.compress("aaaaa"));
  }

  @Test
  @DisplayName("should return null for null input")
  void shouldReturnNullForNull() {
    assertNull(compressor.compress(null));
  }

  @Test
  @DisplayName("should return empty for empty input")
  void shouldReturnEmptyForEmpty() {
    assertEquals("", compressor.compress(""));
  }

  @Test
  @DisplayName("should handle two character string")
  void shouldHandleTwoChars() {
    assertEquals("aa", compressor.compress("aa"));
  }

  @Nested
  @DisplayName("compress2 - StringBuilder approach (duplicate)")
  class Compress2Approach {

    @Test
    @DisplayName("should compress string with repeated characters")
    void shouldCompressRepeatedChars() {
      assertEquals("a3b2c1", compressor.compress2("aaabbc"));
    }

    @Test
    @DisplayName("should compress long runs")
    void shouldCompressLongRuns() {
      assertEquals("a5b3c2", compressor.compress2("aaaaabbbcc"));
    }

    @Test
    @DisplayName("should return original if compressed is not shorter")
    void shouldReturnOriginalWhenNotShorter() {
      assertEquals("abc", compressor.compress2("abc"));
    }

    @Test
    @DisplayName("should handle single character")
    void shouldHandleSingleChar() {
      assertEquals("a", compressor.compress2("a"));
    }

    @Test
    @DisplayName("should handle all same characters")
    void shouldHandleAllSame() {
      assertEquals("a5", compressor.compress2("aaaaa"));
    }

    @Test
    @DisplayName("should return null for null input")
    void shouldReturnNullForNull() {
      assertNull(compressor.compress2(null));
    }

    @Test
    @DisplayName("should return empty for empty input")
    void shouldReturnEmptyForEmpty() {
      assertEquals("", compressor.compress2(""));
    }

    @Test
    @DisplayName("should handle two same characters")
    void shouldHandleTwoSameChars() {
      assertEquals("aa", compressor.compress2("aa"));
    }

    @Test
    @DisplayName("should handle alternating characters")
    void shouldHandleAlternating() {
      assertEquals("ababab", compressor.compress2("ababab"));
    }
  }

  @Nested
  @DisplayName("compressEfficiently - in-place char[] approach")
  class EfficientApproach {

    @Test
    @DisplayName("should compress repeated characters in-place")
    void shouldCompressRepeatedChars() {
      char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
      int len = compressor.compressEfficiently(chars);
      assertEquals(6, len);
      assertArrayEquals(new char[]{'a', '2', 'b', '2', 'c', '3'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should not add count for single characters")
    void shouldNotAddCountForSingles() {
      char[] chars = {'a', 'b', 'c'};
      int len = compressor.compressEfficiently(chars);
      assertEquals(3, len);
      assertArrayEquals(new char[]{'a', 'b', 'c'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should handle single character array")
    void shouldHandleSingleChar() {
      char[] chars = {'a'};
      int len = compressor.compressEfficiently(chars);
      assertEquals(1, len);
      assertArrayEquals(new char[]{'a'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should handle all same characters")
    void shouldHandleAllSame() {
      char[] chars = {'a', 'a', 'a', 'a', 'a'};
      int len = compressor.compressEfficiently(chars);
      assertEquals(2, len);
      assertArrayEquals(new char[]{'a', '5'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should handle double-digit counts")
    void shouldHandleDoubleDigitCounts() {
      char[] chars = new char[12];
      Arrays.fill(chars, 'a');
      int len = compressor.compressEfficiently(chars);
      assertEquals(3, len);
      assertArrayEquals(new char[]{'a', '1', '2'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should handle mixed single and repeated characters")
    void shouldHandleMixed() {
      char[] chars = {'a', 'b', 'b', 'b', 'c'};
      int len = compressor.compressEfficiently(chars);
      assertEquals(4, len);
      assertArrayEquals(new char[]{'a', 'b', '3', 'c'}, Arrays.copyOf(chars, len));
    }

    @Test
    @DisplayName("should handle empty array")
    void shouldHandleEmpty() {
      char[] chars = {};
      int len = compressor.compressEfficiently(chars);
      assertEquals(0, len);
    }
  }
}
