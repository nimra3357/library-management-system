# Library Management System - JUnit 5 Testing Framework

## CS – Software Testing | Assignment #03 | Spring 2026

---

## Project Overview

A complete **JUnit 5 automation testing framework** built on a Library Management System.  
Covers unit tests, data-driven testing (CSV + Excel), parallel execution, test suites, and JaCoCo coverage.

---

## System Under Test — 6 Classes

| Class | Responsibility |
|---|---|
| `Book` | Book entity with validation |
| `Member` | Library member with borrow limit logic |
| `LibraryService` | Manages book catalog (add/remove/find) |
| `LoanService` | Handles borrow and return of books |
| `FineCalculator` | Calculates overdue fines (capped at Rs. 500) |
| `BookSearchService` | Search books by title, author, genre |

---

## Project Structure

```
library-management-testing/
├── src/
│   ├── main/java/com/library/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   ├── LibraryService.java
│   │   ├── LoanService.java
│   │   ├── FineCalculator.java
│   │   └── BookSearchService.java
│   └── test/
│       ├── java/com/library/
│       │   ├── BookTest.java                  (10 tests - @Tag fast)
│       │   ├── MemberTest.java                (10 tests - @Tag fast)
│       │   ├── LibraryServiceTest.java        (12 tests - @Tag fast)
│       │   ├── LoanServiceTest.java           (12 tests - @Tag integration)
│       │   ├── FineCalculatorTest.java        (10 tests - @Tag fast)
│       │   ├── BookSearchServiceTest.java     (10 tests - @Tag fast)
│       │   ├── DataDrivenFineTest.java        (CSV + Dynamic parameterized)
│       │   ├── ExcelDrivenBookTest.java       (Excel parameterized)
│       │   ├── FastSuiteTest.java             (Suite: fast only)
│       │   ├── SlowSuiteTest.java             (Suite: slow only)
│       │   ├── IntegrationSuiteTest.java      (Suite: integration only)
│       │   └── FullSuiteTest.java             (Suite: all tests)
│       └── resources/
│           ├── fine_test_data.csv             (15 data rows)
│           ├── book_test_data.xlsx            (20 rows, 4 categories)
│           └── junit-platform.properties      (parallel config)
└── pom.xml
```

---

## How to Run

### Prerequisites
- Java 11+
- Maven 3.6+

### Run all tests
```bash
mvn clean test
```

### Run only fast tests
```bash
mvn test -Dtest=FastSuiteTest
```

### Run only integration tests
```bash
mvn test -Dtest=IntegrationSuiteTest
```

### Run only data-driven (slow) tests
```bash
mvn test -Dtest=SlowSuiteTest
```

### View JaCoCo Coverage Report
```bash
# After running tests:
open target/site/jacoco/index.html
```
*(On Windows: manually open the file in a browser)*

---

## Test Summary

| Test Class | Tests | Tag | Type |
|---|---|---|---|
| BookTest | 10 | fast | Unit |
| MemberTest | 10 | fast | Unit |
| LibraryServiceTest | 12 | fast | Unit |
| LoanServiceTest | 12 | integration | Integration |
| FineCalculatorTest | 10 | fast | Unit |
| BookSearchServiceTest | 10 | fast | Unit |
| DataDrivenFineTest | 18+ | slow | Parameterized (CSV + Dynamic) |
| ExcelDrivenBookTest | 20 | slow | Parameterized (Excel) |
| **Total** | **~102** | | |

---

## Test Types Covered

- ✅ Positive tests
- ✅ Negative tests
- ✅ Boundary tests
- ✅ Exception handling tests
- ✅ Data-driven (CSV)
- ✅ Data-driven (Excel)
- ✅ Dynamic method source
- ✅ Value source parameterized
- ✅ Inline CSV source

---

## Tools Used

| Tool | Purpose |
|---|---|
| JUnit 5 (Jupiter) | Core test engine |
| JUnit Platform Suite | Test suite grouping |
| Apache POI | Read Excel files in tests |
| OpenCSV | CSV file support |
| JaCoCo | Code coverage reporting |
| Maven Surefire | Build + parallel execution |

---

## Fine Calculation Rules

- Free period: **14 days**
- Daily fine: **Rs. 5/day**
- Maximum fine cap: **Rs. 500**

---

## Naming Convention Used

```
should_<ExpectedBehavior>_when_<Condition>
```
Example: `should_ThrowException_when_BookIdIsNull`

---

## Author

- **Course:** CS – Software Testing  
- **Semester:** Spring 2026  
- **University:** NUCES Chiniot-Faisalabad
