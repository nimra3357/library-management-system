package com.library;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FineCalculator.
 */
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("FineCalculator Tests")
class FineCalculatorTest {

    private FineCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FineCalculator();
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should return zero fine when book returned within free period")
    void should_ReturnZeroFine_when_BookReturnedWithinFreePeriod() {
        assertEquals(0.0, calculator.calculateFine(10));
    }

    @Test
    @Order(2)
    @DisplayName("Should return correct fine when book is one day overdue")
    void should_ReturnCorrectFine_when_BookIsOneDayOverdue() {
        assertEquals(5.0, calculator.calculateFine(15));
    }

    @Test
    @Order(3)
    @DisplayName("Should return correct fine when book is ten days overdue")
    void should_ReturnCorrectFine_when_BookIsTenDaysOverdue() {
        assertEquals(50.0, calculator.calculateFine(24));
    }

    @Test
    @Order(4)
    @DisplayName("Should cap fine at maximum when days are very high")
    void should_CapFine_when_DaysKeptAreVeryHigh() {
        assertEquals(500.0, calculator.calculateFine(200));
    }

    @Test
    @Order(5)
    @DisplayName("Should return true when book is overdue")
    void should_ReturnTrue_when_BookIsOverdue() {
        assertTrue(calculator.isOverdue(20));
    }

    @Test
    @Order(6)
    @DisplayName("Should return false when book is not overdue")
    void should_ReturnFalse_when_BookIsNotOverdue() {
        assertFalse(calculator.isOverdue(7));
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(7)
    @DisplayName("Should return zero fine when book returned on exactly day 14")
    void should_ReturnZeroFine_when_BookReturnedOnExactDueDate() {
        assertEquals(0.0, calculator.calculateFine(14));
        assertFalse(calculator.isOverdue(14));
    }

    @Test
    @Order(8)
    @DisplayName("Should start charging fine at day 15 (one day past due)")
    void should_ChargeFine_when_BookReturnedOneDayAfterDueDate() {
        assertEquals(5.0, calculator.calculateFine(15));
        assertTrue(calculator.isOverdue(15));
    }

    @Test
    @Order(9)
    @DisplayName("Should return zero fine when book returned same day (day 0)")
    void should_ReturnZeroFine_when_BookReturnedSameDay() {
        assertEquals(0.0, calculator.calculateFine(0));
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(10)
    @DisplayName("Should throw exception when days kept is negative")
    void should_ThrowException_when_DaysKeptIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateFine(-1));
    }
}
