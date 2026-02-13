package com.hackerrank.strings;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstNonRepeating {

  public char firstNonRepeatingChar(String str) {
    if (str == null || str.isEmpty()) {
      return '0';
    }

    Map<Character, Integer> charCount = new LinkedHashMap<>();

    for (char c : str.toCharArray()) {
      charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }

    return charCount.entrySet().stream().filter(entry -> entry.getValue() == 1)
        .map(Map.Entry::getKey).findFirst().orElse('0');
  }

  // Alternative: Two-pass approach
  public char firstNonRepeatingCharTwoPass(String str) {
    if (str == null || str.isEmpty()) {
      return '0';
    }

    int[] frequency = new int[256]; // ASCII characters

    for (char c : str.toCharArray()) {
      frequency[c]++;
    }

    for (char c : str.toCharArray()) {
      if (frequency[c] == 1) {
        return c;
      }
    }

    return '0';
  }

  public int firstNonRepeatingCharEfficientlyOLD(String s) {
    if (s == null || s.isEmpty()) {
      return '0';
    }

    char[] chars = s.toCharArray();
    Map<Character, Integer> charCount = new LinkedHashMap<>();

    for (char c : chars) {
      charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }
    int idx = 0;
    for (char c : s.toCharArray()) {
      if (charCount.get(c) == 1) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

  public int firstNonRepeatingCharEfficiently(String s) {
    if (s == null || s.isEmpty()) {
      return '0';
    }
    int[] chars = new int['z' - 'a' + 1];
    char[] sChars = s.toCharArray();

    for (int c : sChars) {
      chars[c - 'a'] = chars[c - 'a'] + 1;
    }

    int idx = 0;
    for (int c : sChars) {
      if (chars[c - 'a'] == 1) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

}
