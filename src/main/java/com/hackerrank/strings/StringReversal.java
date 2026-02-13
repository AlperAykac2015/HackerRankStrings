package com.hackerrank.strings;

public class StringReversal {

  // Approach 1: Using StringBuilder
  public String reverseWithStringBuilder(String str) {
    if (str == null) {
      return null;
    }
    return new StringBuilder(str).reverse().toString();
  }

  // Approach 2: Two-pointer technique
  public String reverseWithTwoPointers(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    char[] chars = str.toCharArray();
    int left = 0;
    int right = chars.length - 1;

    while (left < right) {
      char temp = chars[left];
      chars[left] = chars[right];
      chars[right] = temp;
      left++;
      right--;
    }

    return new String(chars);
  }

  // Approach 3: Recursive
  public String reverseRecursive(String str) {
    if (str == null || str.length() <= 1) {
      return str;
    }
    return reverseRecursive(str.substring(1)) + str.charAt(0);
  }
}
