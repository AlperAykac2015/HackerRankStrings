package com.hackerrank.strings;

public class DigitValidator {

  public boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }

    return str.matches("\\d+");
  }

  // Without regex
  public boolean isNumericManual(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }

    return str.chars().allMatch(Character::isDigit);
  }

  // Handle negative numbers and decimals
  public boolean isNumericValue(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }

    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
