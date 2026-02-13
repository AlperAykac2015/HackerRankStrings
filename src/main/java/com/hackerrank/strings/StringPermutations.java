package com.hackerrank.strings;

import java.util.ArrayList;
import java.util.List;

public class StringPermutations {

  public List<String> findPermutations(String str) {
    List<String> result = new ArrayList<>();
    if (str == null) {
      return result;
    }
    permute(str.toCharArray(), 0, result);
    return result;
  }

  private void permute(char[] chars, int start, List<String> result) {
    if (start == chars.length - 1) {
      result.add(new String(chars));
      return;
    }

    for (int i = start; i < chars.length; i++) {
      swap(chars, start, i);
      permute(chars, start + 1, result);
      swap(chars, start, i); // backtrack
    }
  }

  private void swap(char[] chars, int i, int j) {
    char temp = chars[i];
    chars[i] = chars[j];
    chars[j] = temp;
  }
}
