package com.library;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Book class.
 * Tests: positive, negative, boundary, and exception handling scenarios.
 */
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Book Class Tests")
class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("B001", "Clean Code", "Robert Martin", "Technology", 2008);
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should create book when all valid details are provided")
    void should_CreateBook_when_ValidDetailsProvided() {
        assertNotNull(book);
        assertEquals("B001", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAuthor());
        assertEquals("Technology", book.getGenre());
        assertEquals(2008, book.getYear());
    }

    @Test
    @Order(2)
    @DisplayName("Should be available when a new book is created")
    void should_BeAvailable_when_NewBookCreated() {
        assertTrue(book.isAvailable());
    }

    @Test
    @Order(3)
    @DisplayName("Should update availability when set to false")
    void should_BeUnavailable_when_AvailabilitySetToFalse() {
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    @Order(4)
    @DisplayName("Should restore availability when set back to true")
    void should_BeAvailable_when_AvailabilityRestoredToTrue() {
        book.setAvailable(false);
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    // ============================
    // NEGATIVE TESTS
    // ============================

    @Test
    @Order(5)
    @DisplayName("Should throw exception when book ID is null")
    void should_ThrowException_when_BookIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(null, "Clean Code", "Robert Martin", "Technology", 2008));
    }

    @Test
    @Order(6)
    @DisplayName("Should throw exception when book title is empty")
    void should_ThrowException_when_TitleIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("B002", "", "Robert Martin", "Technology", 2008));
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when author is blank")
    void should_ThrowException_when_AuthorIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("B003", "Some Title", "   ", "Technology", 2008));
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(8)
    @DisplayName("Should create book when year is at minimum boundary (0)")
    void should_CreateBook_when_YearIsAtMinimumBoundary() {
        Book ancientBook = new Book("B004", "Ancient Scroll", "Unknown", "History", 0);
        assertEquals(0, ancientBook.getYear());
    }

    @Test
    @Order(9)
    @DisplayName("Should create book when year is at maximum boundary (2100)")
    void should_CreateBook_when_YearIsAtMaximumBoundary() {
        Book futureBook = new Book("B005", "Future Book", "AI Author", "SciFi", 2100);
        assertEquals(2100, futureBook.getYear());
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(10)
    @DisplayName("Should throw exception when year is out of valid range")
    void should_ThrowException_when_YearIsOutOfRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("B006", "Bad Year Book", "Author", "Genre", -1));
    }
}
