package com.hackerrank.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnagramChecker {

  // Approach 1: Sorting
  public boolean isAnagram(String s1, String s2) {
    if (s1 == null || s2 == null || s1.length() != s2.length()) {
      return false;
    }

    char[] chars1 = s1.toLowerCase().toCharArray();
    char[] chars2 = s2.toLowerCase().toCharArray();

    Arrays.sort(chars1);
    Arrays.sort(chars2);

    return Arrays.equals(chars1, chars2);
  }

  // Approach 2: Character frequency map
  public boolean isAnagramWithMap(String s, String t) {
    if (s == null || t == null || s.length() != t.length()) {
      return false;
    }

    Map<Character, Integer> charCount = new HashMap<>();

    for (char c : s.toLowerCase().toCharArray()) {
      charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }

    for (char c : t.toLowerCase().toCharArray()) {
      int count = charCount.getOrDefault(c, 0);
      if (count == 0) {
        return false;
      }
      charCount.put(c, count - 1);
    }

    return true;
  }

  // Approach 2: Character frequency map
  public boolean isAnagramWithArray(String s, String t) {
    if (s == null || t == null || s.length() != t.length()) {
      return false;
    }

    int[] charCount = new int[65536];

    for (char c : s.toLowerCase().toCharArray()) {
      charCount[c] += 1;
    }

    for (char c : t.toLowerCase().toCharArray()) {
      if (charCount[c] == 0) {
        return false;
      }
//      charCount[c] -= 1;
//      if (charCount[c] < 0) {
//        return false;
//      }
    }
    return true;
  }

}
