package com.library;

import org.junit.platform.suite.api.*;

/**
 * Test Suite: Runs ALL tests across the entire library package.
 * Task 4: Test Suite Configuration
 *
 * To run the full suite:
 *   mvn test -Dtest=FullSuiteTest
 *
 * Or simply:
 *   mvn clean test
 */
@Suite
@SelectClasses({
        BookTest.class,
        MemberTest.class,
        FineCalculatorTest.class,
        LibraryServiceTest.class,
        BookSearchServiceTest.class,
        LoanServiceTest.class,
        ExcelDrivenBookTest.class,
        DataDrivenFineTest.class
})
@SuiteDisplayName("Full Test Suite - All Tests")
public class FullSuiteTest {
    // JUnit Platform Suite - no body needed
}
