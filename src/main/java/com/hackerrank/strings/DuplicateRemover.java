package com.hackerrank.strings;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DuplicateRemover {

  // Using LinkedHashSet to maintain order
  public String removeDuplicates(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    Set<Character> seen = new LinkedHashSet<>();

    for (char c : str.toCharArray()) {
      seen.add(c);
    }

    return seen.stream()
        .map(String::valueOf)
        .collect(Collectors.joining());
  }

  // Using StringBuilder
  public String removeDuplicatesWithBuilder(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    StringBuilder result = new StringBuilder();
    Set<Character> seen = new HashSet<>();

    for (char c : str.toCharArray()) {
      if (seen.add(c)) { // TRUE if c is not already added to set
        result.append(c);
      }
    }

    return result.toString();
  }

  public void findUniqueCharsEfficiently(String s) {
    StringBuilder sb = new StringBuilder();
    boolean[] seen = new boolean[65536]; // Support for basic multilingual plane

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c != ' ' && !seen[c]) {
        seen[c] = true;
        sb.append(c);
      }
    }
    System.out.println("Unique characters (Efficient & Ordered): " + sb);
  }

}
