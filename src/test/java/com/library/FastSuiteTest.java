package com.library;

import org.junit.platform.suite.api.*;

/**
 * Test Suite: Runs only tests tagged as "fast".
 * Task 4: Test Suite Configuration
 *
 * To run this specific suite:
 *   mvn test -Dtest=FastSuiteTest
 */
@Suite
@SelectClasses({
        BookTest.class,
        MemberTest.class,
        FineCalculatorTest.class,
        LibraryServiceTest.class,
        BookSearchServiceTest.class
})
@IncludeTags("fast")
@SuiteDisplayName("Fast Test Suite")
public class FastSuiteTest {
    // JUnit Platform Suite - no body needed
}
