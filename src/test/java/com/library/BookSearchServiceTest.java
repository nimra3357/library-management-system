package com.library;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BookSearchService.
 */
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("BookSearchService Tests")
class BookSearchServiceTest {

    private LibraryService libraryService;
    private BookSearchService searchService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();
        searchService  = new BookSearchService(libraryService);

        libraryService.addBook(new Book("B001", "Clean Code",             "Robert Martin", "Technology", 2008));
        libraryService.addBook(new Book("B002", "Code Complete",          "Steve McConnell","Technology", 2004));
        libraryService.addBook(new Book("B003", "The Pragmatic Programmer","Andrew Hunt",   "Technology", 1999));
        libraryService.addBook(new Book("B004", "Design Patterns",        "Gang of Four",  "Engineering",1994));
        libraryService.addBook(new Book("B005", "Refactoring",            "Robert Martin", "Technology", 2018));
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should find books when keyword matches part of title")
    void should_FindBooks_when_KeywordMatchesPartOfTitle() {
        List<Book> result = searchService.searchByTitle("Code");
        assertEquals(2, result.size()); // "Clean Code" and "Code Complete"
    }

    @Test
    @Order(2)
    @DisplayName("Should find books when searching by author name")
    void should_FindBooks_when_SearchingByAuthorName() {
        List<Book> result = searchService.searchByAuthor("Robert Martin");
        assertEquals(2, result.size()); // Clean Code + Refactoring
    }

    @Test
    @Order(3)
    @DisplayName("Should find books when searching by genre")
    void should_FindBooks_when_SearchingByGenre() {
        List<Book> result = searchService.searchByGenre("Technology");
        assertEquals(4, result.size());
    }

    @Test
    @Order(4)
    @DisplayName("Should find book with case-insensitive title search")
    void should_FindBook_when_TitleSearchIsCaseInsensitive() {
        List<Book> result = searchService.searchByTitle("clean code");
        assertFalse(result.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Should return only available books when searching available by title")
    void should_ReturnOnlyAvailableBooks_when_SearchingAvailableByTitle() {
        libraryService.findById("B001").setAvailable(false);
        List<Book> result = searchService.searchAvailableByTitle("Code");
        assertEquals(1, result.size()); // Only "Code Complete"
    }

    // ============================
    // NEGATIVE TESTS
    // ============================

    @Test
    @Order(6)
    @DisplayName("Should return empty list when no books match the title")
    void should_ReturnEmptyList_when_NoBooksMatchTitle() {
        List<Book> result = searchService.searchByTitle("XYZ_NOT_EXIST");
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(7)
    @DisplayName("Should return empty list when genre has no matching books")
    void should_ReturnEmptyList_when_GenreHasNoMatchingBooks() {
        List<Book> result = searchService.searchByGenre("Fantasy");
        assertTrue(result.isEmpty());
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(8)
    @DisplayName("Should find single character keyword match in title")
    void should_FindBook_when_KeywordIsSingleCharacter() {
        List<Book> result = searchService.searchByTitle("C");
        assertFalse(result.isEmpty()); // "Clean Code", "Code Complete"
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(9)
    @DisplayName("Should throw exception when search keyword is null")
    void should_ThrowException_when_TitleSearchKeywordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> searchService.searchByTitle(null));
    }

    @Test
    @Order(10)
    @DisplayName("Should throw exception when author search keyword is empty")
    void should_ThrowException_when_AuthorSearchKeywordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> searchService.searchByAuthor(""));
    }
}
