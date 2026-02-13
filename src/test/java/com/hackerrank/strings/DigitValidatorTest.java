package com.hackerrank.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitValidatorTest {

    private DigitValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DigitValidator();
    }

    @Nested
    @DisplayName("isNumeric - regex approach")
    class RegexApproach {

        @Test
        @DisplayName("should return true for digits only")
        void shouldReturnTrueForDigits() {
            assertTrue(validator.isNumeric("12345"));
        }

        @Test
        @DisplayName("should return false for letters")
        void shouldReturnFalseForLetters() {
            assertFalse(validator.isNumeric("abc"));
        }

        @Test
        @DisplayName("should return false for mixed input")
        void shouldReturnFalseForMixed() {
            assertFalse(validator.isNumeric("12a34"));
        }

        @Test
        @DisplayName("should return false for null")
        void shouldReturnFalseForNull() {
            assertFalse(validator.isNumeric(null));
        }

        @Test
        @DisplayName("should return false for empty")
        void shouldReturnFalseForEmpty() {
            assertFalse(validator.isNumeric(""));
        }

        @Test
        @DisplayName("should return false for negative number string")
        void shouldReturnFalseForNegative() {
            assertFalse(validator.isNumeric("-123"));
        }
    }

    @Nested
    @DisplayName("isNumericManual - Character.isDigit approach")
    class ManualApproach {

        @Test
        @DisplayName("should return true for digits")
        void shouldReturnTrueForDigits() {
            assertTrue(validator.isNumericManual("9876543210"));
        }

        @Test
        @DisplayName("should return false for decimal")
        void shouldReturnFalseForDecimal() {
            assertFalse(validator.isNumericManual("3.14"));
        }
    }

    @Nested
    @DisplayName("isNumericValue - parseDouble approach")
    class ParseApproach {

        @Test
        @DisplayName("should return true for negative number")
        void shouldHandleNegative() {
            assertTrue(validator.isNumericValue("-42"));
        }

        @Test
        @DisplayName("should return true for decimal number")
        void shouldHandleDecimal() {
            assertTrue(validator.isNumericValue("3.14159"));
        }

        @Test
        @DisplayName("should return false for non-numeric")
        void shouldReturnFalseForNonNumeric() {
            assertFalse(validator.isNumericValue("abc"));
        }

        @Test
        @DisplayName("should return false for empty string")
        void shouldReturnFalseForEmpty() {
            assertFalse(validator.isNumericValue(""));
        }
    }
}
