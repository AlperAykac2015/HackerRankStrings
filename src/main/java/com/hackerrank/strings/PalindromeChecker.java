package com.hackerrank.strings;

public class PalindromeChecker {

  public boolean isPalindrome(String s) {
    if (s == null) {
      return false;
    }
    s = s.toLowerCase();
    char[] chars = s.toCharArray();
    StringBuilder result = new StringBuilder(s.length());
    for (int i = 0; i < s.length(); i++) {
      char c = chars[i];
      if (Character.isLetterOrDigit(c)) {
        result.append(c);
      }
    }

    int left = 0;
    int right = result.length() - 1;

    while (left < right) {
      if (result.charAt(left) != result.charAt(right)) {
        return false;
      }
      left++;
      right--;
    }

    return true;
  }

  // Alternative: Using StringBuilder
  public boolean isPalindromeWithReverse(String str) {
    if (str == null) {
      return false;
    }

    String cleaned = str.replaceAll("\\s+", "").toLowerCase();
    String reversed = new StringBuilder(cleaned).reverse().toString();

    return cleaned.equals(reversed);
  }
}
