package com.library;

import org.junit.platform.suite.api.*;

/**
 * Test Suite: Runs only tests tagged as "slow" (data-driven parameterized tests).
 * Task 4: Test Suite Configuration
 *
 * To run this specific suite:
 *   mvn test -Dtest=SlowSuiteTest
 */
@Suite
@SelectClasses({
        ExcelDrivenBookTest.class,
        DataDrivenFineTest.class
})
@IncludeTags("slow")
@SuiteDisplayName("Slow / Data-Driven Test Suite")
public class SlowSuiteTest {
    // JUnit Platform Suite - no body needed
}
