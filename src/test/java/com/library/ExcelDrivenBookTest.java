package com.library;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Data-Driven tests using Excel file.
 * Task 3: Excel-based Parameterized Testing
 *
 * Excel sheet columns:
 *   Category | BookId | Title | Author | Genre | Year | ExpectedValid
 */
@Tag("slow")
@DisplayName("Excel Data-Driven Book Tests")
class ExcelDrivenBookTest {

    /**
     * Loads test data from the Excel file in test resources.
     * Returns rows as: category, bookId, title, author, genre, year, expectedValid
     */
    static Stream<Arguments> loadBookDataFromExcel() throws Exception {
        List<Arguments> args = new ArrayList<>();

        try (InputStream is = ExcelDrivenBookTest.class.getResourceAsStream("/book_test_data.xlsx")) {
            assertNotNull(is, "book_test_data.xlsx not found in test resources");

            try (Workbook workbook = new XSSFWorkbook(is)) {
                Sheet sheet = workbook.getSheetAt(0);

                // Skip header row (row 0), read from row 1 onwards
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String category     = getCellValue(row.getCell(0));
                    String bookId       = getCellValue(row.getCell(1));
                    String title        = getCellValue(row.getCell(2));
                    String author       = getCellValue(row.getCell(3));
                    String genre        = getCellValue(row.getCell(4));
                    int    year         = (int) row.getCell(5).getNumericCellValue();
                    boolean expectedValid = row.getCell(6).getBooleanCellValue();

                    args.add(Arguments.of(category, bookId, title, author, genre, year, expectedValid));
                }
            }
        }
        return args.stream();
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.NUMERIC)
            return String.valueOf((int) cell.getNumericCellValue());
        return cell.getStringCellValue().trim();
    }

    // ============================
    // EXCEL PARAMETERIZED TEST
    // ============================

    @ParameterizedTest(name = "[{index}] Category={0} | Book={2} | Valid={6}")
    @MethodSource("loadBookDataFromExcel")
    @DisplayName("Should create or reject book based on Excel test data")
    void should_CreateOrRejectBook_when_UsingExcelTestData(
            String category,
            String bookId,
            String title,
            String author,
            String genre,
            int year,
            boolean shouldBeValid) {

        if (shouldBeValid) {
            // Valid cases: book should be created without exception
            assertDoesNotThrow(() -> {
                Book b = new Book(bookId, title, author, genre, year);
                assertNotNull(b);
                assertEquals(bookId, b.getId());
            }, "Category [" + category + "] should be valid but threw exception");
        } else {
            // Invalid cases: book creation should throw an exception
            assertThrows(IllegalArgumentException.class,
                    () -> new Book(bookId, title, author, genre, year),
                    "Category [" + category + "] should be invalid but did not throw");
        }
    }
}
