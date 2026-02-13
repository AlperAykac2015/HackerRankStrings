package com.hackerrank.strings;

public class StringCompressor {

  public String compress2(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    StringBuilder compressed = new StringBuilder();
    int count = 1;

    for (int i = 1; i < str.length(); i++) {
      if (str.charAt(i) == str.charAt(i - 1)) {
        count++;
      } else {
        compressed.append(str.charAt(i - 1)).append(count);
        count = 1;
      }
    }

    // Add the last group
    compressed.append(str.charAt(str.length() - 1)).append(count);

    // Return original if compressed is longer
    return compressed.length() < str.length() ?
        compressed.toString() : str;
  }

  public String compress(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    StringBuilder compressed = new StringBuilder();
    int count = 1;

    for (int i = 1; i < str.length(); i++) {
      if (str.charAt(i) == str.charAt(i - 1)) {
        count++;
      } else {
        compressed.append(str.charAt(i - 1)).append(count);
        count = 1;
      }
    }

    // Add the last group
    compressed.append(str.charAt(str.length() - 1)).append(count);

    // Return original if compressed is longer
    return compressed.length() < str.length() ?
        compressed.toString() : str;
  }

  public int compressEfficiently(char[] chars) {
    int n = chars.length;
    int idx = 0;

    for (int i = 0; i < n; i++) {
      char ch = chars[i];
      int count = 0;

      while (i < n && ch == chars[i]) {
        count++;
        i++;
      }
      if (count == 1) {
        chars[idx++] = ch;
      } else {
        chars[idx++] = ch;
        for (char digit : Integer.toString(count).toCharArray()) {
          chars[idx++] = digit;
        }

      }

      i--;
    }

    return idx;
  }

}
