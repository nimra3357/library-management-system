package com.library;

import org.junit.jupiter.api.*;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LibraryService.
 */
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("LibraryService Tests")
class LibraryServiceTest {

    private LibraryService libraryService;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();
        book1 = new Book("B001", "Clean Code", "Robert Martin", "Technology", 2008);
        book2 = new Book("B002", "The Pragmatic Programmer", "Andrew Hunt", "Technology", 1999);
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should add book when valid book is provided")
    void should_AddBook_when_ValidBookProvided() {
        libraryService.addBook(book1);
        assertEquals(1, libraryService.getTotalCount());
    }

    @Test
    @Order(2)
    @DisplayName("Should find book by ID when book exists")
    void should_FindBook_when_BookExistsById() {
        libraryService.addBook(book1);
        Book found = libraryService.findById("B001");
        assertNotNull(found);
        assertEquals("Clean Code", found.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("Should remove book when book exists")
    void should_RemoveBook_when_BookExists() {
        libraryService.addBook(book1);
        libraryService.removeBook("B001");
        assertEquals(0, libraryService.getTotalCount());
    }

    @Test
    @Order(4)
    @DisplayName("Should return all books when multiple books are added")
    void should_ReturnAllBooks_when_MultipleBooksAdded() {
        libraryService.addBook(book1);
        libraryService.addBook(book2);
        assertEquals(2, libraryService.getAllBooks().size());
    }

    @Test
    @Order(5)
    @DisplayName("Should return only available books from catalog")
    void should_ReturnOnlyAvailableBooks_when_SomeBooksCheckedOut() {
        libraryService.addBook(book1);
        libraryService.addBook(book2);
        book1.setAvailable(false);
        assertEquals(1, libraryService.getAvailableBooks().size());
    }

    @Test
    @Order(6)
    @DisplayName("Should be empty when no books have been added")
    void should_BeEmpty_when_NoBooksAdded() {
        assertTrue(libraryService.isEmpty());
    }

    @Test
    @Order(7)
    @DisplayName("Should confirm book exists after adding it")
    void should_ReturnTrue_when_BookExistsInCatalog() {
        libraryService.addBook(book1);
        assertTrue(libraryService.exists("B001"));
    }

    // ============================
    // NEGATIVE TESTS
    // ============================

    @Test
    @Order(8)
    @DisplayName("Should return null when book ID does not exist")
    void should_ReturnNull_when_BookIdDoesNotExist() {
        assertNull(libraryService.findById("NOTEXIST"));
    }

    @Test
    @Order(9)
    @DisplayName("Should throw exception when adding null book")
    void should_ThrowException_when_NullBookAdded() {
        assertThrows(IllegalArgumentException.class, () -> libraryService.addBook(null));
    }

    @Test
    @Order(10)
    @DisplayName("Should throw exception when removing non-existent book")
    void should_ThrowException_when_RemovingNonExistentBook() {
        assertThrows(NoSuchElementException.class, () -> libraryService.removeBook("GHOST"));
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(11)
    @DisplayName("Should throw exception when duplicate book is added")
    void should_ThrowException_when_DuplicateBookAdded() {
        libraryService.addBook(book1);
        Book duplicate = new Book("B001", "Another Title", "Author", "Genre", 2020);
        assertThrows(IllegalStateException.class, () -> libraryService.addBook(duplicate));
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(12)
    @DisplayName("Should throw exception when finding book with null ID")
    void should_ThrowException_when_FindByNullId() {
        assertThrows(IllegalArgumentException.class, () -> libraryService.findById(null));
    }
}
