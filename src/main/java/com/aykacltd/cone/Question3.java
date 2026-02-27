package com.aykacltd.cone;

import java.util.HashMap;
import java.util.Map;

public class Question3 {

  public static int romanToInt(String s) {
    Map<Character, Integer> map = new HashMap<>();
    map.put('I', 1);
    map.put('V', 5);
    map.put('X', 10);
    map.put('L', 50);
    map.put('C', 100);
    map.put('D', 500);
    map.put('M', 1000);

    int result = 0;
    int n = s.length();

    for (int i = 0; i < n; i++) {
      int curr = map.get(s.charAt(i));
      int next = (i + 1 < n) ? map.get(s.charAt(i + 1)) : 0;

      // Subtractive notation: if current value < next, subtract it
      if (curr < next) {
        result -= curr;
      } else {
        result += curr;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    String[] tests = {"III", "IV", "IX", "XIV", "LVIII", "MCMXCIV"};
    int[] expected = {3, 4, 9, 14, 58, 1994};

    for (int i = 0; i < tests.length; i++) {
      int result = romanToInt(tests[i]);
      System.out.printf("%-10s -> %4d  (expected %d) %s%n",
          tests[i], result, expected[i], result == expected[i] ? "✓" : "✗");
    }
  }
}
