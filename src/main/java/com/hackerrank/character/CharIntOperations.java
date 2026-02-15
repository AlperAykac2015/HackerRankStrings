package com.hackerrank.character;

public class CharIntOperations {

  // Compares upper and lower case of the same character using their int values
  public boolean isSameCharDifferentCase(char a, char b) {
    if (a == b) {
      return false; // same case, not different
    }
    return Character.toLowerCase(a) == Character.toLowerCase(b)
        && Character.isLetter(a) && Character.isLetter(b);
  }

  // Checks if two characters are consecutive in the alphabet (case insensitive)
  // e.g. ('a','B') -> true, ('C','d') -> true, ('a','c') -> false
  public boolean areConsecutive(char a, char b) {
    if (!Character.isLetter(a) || !Character.isLetter(b)) {
      return false;
    }
    int diff = Math.abs(Character.toLowerCase(a) - Character.toLowerCase(b));
    return diff == 1;
  }

  // Compares two strings and counts how many characters at the same index match (case insensitive)
  public int countMatchingCharacters(String s1, String s2) {
    if (s1 == null || s2 == null) {
      return 0;
    }

    int count = 0;
    int minLength = Math.min(s1.length(), s2.length());

    for (int i = 0; i < minLength; i++) {
      if (Character.toLowerCase(s1.charAt(i)) == Character.toLowerCase(s2.charAt(i))) {
        count++;
      }
    }

    return count;
  }

  /**
   * Method 1: Simple iteration with bounds checking
   * Time Complexity: O(n) where n = 100 (char array size)
   * Space Complexity: O(1) - no extra space used
   * <p>
   * This is the FASTEST approach - direct array access, minimal overhead
   */
  public void markPositionsSimple(char[] chars, boolean[] marked) {
    for (char aChar : chars) {
      if (!Character.isLetter(aChar)) {
        continue;
      }
      int value = aChar;

      // Bounds check - only mark if within array bounds
      if (value < marked.length) {
        marked[value] = true;
      }
    }
  }
}
