package com.library;

import org.junit.platform.suite.api.*;

/**
 * Test Suite: Runs only tests tagged as "integration".
 * Task 4: Test Suite Configuration
 *
 * To run this specific suite:
 *   mvn test -Dtest=IntegrationSuiteTest
 */
@Suite
@SelectClasses(LoanServiceTest.class)
@IncludeTags("integration")
@SuiteDisplayName("Integration Test Suite")
public class IntegrationSuiteTest {
    // JUnit Platform Suite - no body needed
}
