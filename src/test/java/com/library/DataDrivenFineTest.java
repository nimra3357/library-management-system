package com.library;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Data-Driven tests for FineCalculator using CSV and dynamic sources.
 * Task 3: Parameterized Testing
 */
@Tag("slow")
@DisplayName("Data-Driven Fine Calculator Tests")
class DataDrivenFineTest {

    // ============================
    // CSV-BASED PARAMETERIZED TESTS
    // ============================

    @ParameterizedTest(name = "Days={0}, Expected Fine={1}")
    @CsvFileSource(resources = "/fine_test_data.csv", numLinesToSkip = 1)
    @DisplayName("Should calculate correct fine from CSV data")
    void should_CalculateCorrectFine_when_UsingCSVData(int daysKept, double expectedFine) {
        FineCalculator calc = new FineCalculator();
        assertEquals(expectedFine, calc.calculateFine(daysKept), 0.001);
    }

    // ============================
    // DYNAMIC METHOD SOURCE TESTS
    // ============================

    @ParameterizedTest(name = "Days={0}, Expected Fine={1}")
    @MethodSource("provideBoundaryFineData")
    @DisplayName("Should handle boundary fine values from dynamic source")
    void should_HandleBoundaryFines_when_UsingDynamicSource(int days, double expectedFine) {
        FineCalculator calc = new FineCalculator();
        assertEquals(expectedFine, calc.calculateFine(days), 0.001);
    }

    static Stream<Arguments> provideBoundaryFineData() {
        return Stream.of(
                Arguments.of(0,   0.0),    // No days: free
                Arguments.of(13,  0.0),    // Just before due date
                Arguments.of(14,  0.0),    // Exactly on due date
                Arguments.of(15,  5.0),    // One day overdue
                Arguments.of(24,  50.0),   // Ten days overdue
                Arguments.of(114, 500.0),  // Hits the cap
                Arguments.of(200, 500.0)   // Far over cap
        );
    }

    // ============================
    // VALUE SOURCE TESTS
    // ============================

    @ParameterizedTest(name = "Days={0} should NOT be overdue")
    @ValueSource(ints = {0, 1, 7, 13, 14})
    @DisplayName("Should not be overdue when days are within free period")
    void should_NotBeOverdue_when_DaysWithinFreePeriod(int days) {
        FineCalculator calc = new FineCalculator();
        assertFalse(calc.isOverdue(days));
    }

    @ParameterizedTest(name = "Days={0} SHOULD be overdue")
    @ValueSource(ints = {15, 16, 20, 30, 100})
    @DisplayName("Should be overdue when days exceed free period")
    void should_BeOverdue_when_DaysExceedFreePeriod(int days) {
        FineCalculator calc = new FineCalculator();
        assertTrue(calc.isOverdue(days));
    }

    // ============================
    // CSV INLINE TESTS (No file)
    // ============================

    @ParameterizedTest(name = "Days={0}, Overdue={1}")
    @CsvSource({
            "14, false",
            "15, true",
            "0,  false",
            "100, true",
            "13, false"
    })
    @DisplayName("Should correctly determine overdue status using inline CSV")
    void should_CorrectlyDetermineOverdueStatus_when_UsingInlineCSV(int days, boolean expectedOverdue) {
        FineCalculator calc = new FineCalculator();
        assertEquals(expectedOverdue, calc.isOverdue(days));
    }
}
