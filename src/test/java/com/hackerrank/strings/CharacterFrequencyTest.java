package com.hackerrank.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharacterFrequencyTest {

  private CharacterFrequency frequency;

  @BeforeEach
  void setUp() {
    frequency = new CharacterFrequency();
  }

  @Nested
  @DisplayName("countCharacters - Stream approach")
  class StreamApproach {

    @Test
    @DisplayName("should count character frequencies")
    void shouldCountFrequencies() {
      Map<Character, Long> result = frequency.countCharacters("hello");
      assertEquals(1L, result.get('h'));
      assertEquals(1L, result.get('e'));
      assertEquals(2L, result.get('l'));
      assertEquals(1L, result.get('o'));
    }

    @Test
    @DisplayName("should return empty map for null")
    void shouldReturnEmptyForNull() {
      assertTrue(frequency.countCharacters(null).isEmpty());
    }

    @Test
    @DisplayName("should handle single character")
    void shouldHandleSingleChar() {
      Map<Character, Long> result = frequency.countCharacters("a");
      assertEquals(1, result.size());
      assertEquals(1L, result.get('a'));
    }

    @Test
    @DisplayName("should count all same characters")
    void shouldCountAllSame() {
      Map<Character, Long> result = frequency.countCharacters("aaaa");
      assertEquals(1, result.size());
      assertEquals(4L, result.get('a'));
    }
  }

  @Nested
  @DisplayName("countCharactersEfficiently - boolean[] approach")
  class EfficientApproach {

    @Test
    @DisplayName("should count character frequencies")
    void shouldCountFrequencies() {
      Map<Character, Integer> result = frequency.countCharactersEfficiently("hello");
      assertEquals(1, result.get('h'));
      assertEquals(1, result.get('e'));
      assertEquals(2, result.get('l'));
      assertEquals(1, result.get('o'));
    }

    @Test
    @DisplayName("should count all same characters")
    void shouldCountAllSame() {
      Map<Character, Integer> result = frequency.countCharactersEfficiently("aaaa");
      assertEquals(1, result.size());
      assertEquals(4, result.get('a'));
    }

    @Test
    @DisplayName("should handle single character")
    void shouldHandleSingleChar() {
      Map<Character, Integer> result = frequency.countCharactersEfficiently("a");
      assertEquals(1, result.size());
      assertEquals(1, result.get('a'));
    }

    @Test
    @DisplayName("should handle string with spaces")
    void shouldHandleSpaces() {
      Map<Character, Integer> result = frequency.countCharactersEfficiently("a b");
      assertEquals(1, result.get('a'));
      assertEquals(1, result.get('b'));
      assertEquals(1, result.get(' '));
    }

    @Test
    @DisplayName("should handle null input")
    void shouldHandleNull() {
      assertThrows(NullPointerException.class, () ->
          frequency.countCharactersEfficiently(null));
    }

    @Test
    @DisplayName("should handle empty string")
    void shouldHandleEmpty() {
      Map<Character, Integer> result = frequency.countCharactersEfficiently("");
      assertTrue(result.isEmpty());
    }
  }

  @Nested
  @DisplayName("countCharactersTraditional - HashMap approach")
  class TraditionalApproach {

    @Test
    @DisplayName("should count character frequencies")
    void shouldCountFrequencies() {
      Map<Character, Integer> result = frequency.countCharactersTraditional("banana");
      assertEquals(1, result.get('b'));
      assertEquals(3, result.get('a'));
      assertEquals(2, result.get('n'));
    }

    @Test
    @DisplayName("should return empty map for null")
    void shouldReturnEmptyForNull() {
      assertTrue(frequency.countCharactersTraditional(null).isEmpty());
    }
  }
}
