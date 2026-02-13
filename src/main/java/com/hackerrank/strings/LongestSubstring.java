package com.hackerrank.strings;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstring {

  public int lengthOfLongestSubstring(String input) {
    if (input == null || input.isEmpty()) {
      return 0;
    }
    Map<Character, Integer> charIndex = new HashMap<>();
    int maxLength = 0;
    int start = 0;
    int len = input.length();
    char[] chars = input.toCharArray();
    char currentChar = 0;
    int idx = 0;
    for (int end = 0; end < len; end++) {
      currentChar = chars[end];
      idx = charIndex.getOrDefault(currentChar, -1);
      if (idx != -1) {
        start = Math.max(start, idx + 1);
      }
      charIndex.put(currentChar, end);
      maxLength = Math.max(maxLength, end - start + 1);
    }
    return maxLength;
  }

  // Return the actual substring
  public String getLongestSubstring(String s) {
    if (s == null || s.isEmpty()) {
      return "";
    }

    Map<Character, Integer> charIndex = new HashMap<>();
    int maxLength = 0;
    int start = 0;
    int resultStart = 0;

    for (int end = 0; end < s.length(); end++) {
      char currentChar = s.charAt(end);

      if (charIndex.containsKey(currentChar)) {
        start = Math.max(start, charIndex.get(currentChar) + 1);
      }

      charIndex.put(currentChar, end);

      if (end - start + 1 > maxLength) {
        maxLength = end - start + 1;
        resultStart = start;
      }
    }

    return s.substring(resultStart, resultStart + maxLength);
  }
}
