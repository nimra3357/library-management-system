package com.library;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LoanService.
 * Tagged as integration since it involves multiple collaborating classes.
 */
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("LoanService Tests")
class LoanServiceTest {

    private LibraryService libraryService;
    private LoanService loanService;
    private Member member;
    private Book book;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();
        loanService    = new LoanService(libraryService);
        member         = new Member("M001", "Sara Khan", "sara@example.com");
        book           = new Book("B001", "Java Concurrency", "Goetz", "Technology", 2006);
        libraryService.addBook(book);
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should borrow book when member and book are valid")
    void should_BorrowBook_when_MemberAndBookAreValid() {
        loanService.borrowBook(member, "B001");
        assertFalse(book.isAvailable());
        assertEquals(1, member.getBorrowedCount());
    }

    @Test
    @Order(2)
    @DisplayName("Should return book when it was previously borrowed")
    void should_ReturnBook_when_BookWasPreviouslyBorrowed() {
        loanService.borrowBook(member, "B001");
        loanService.returnBook(member, "B001");
        assertTrue(book.isAvailable());
        assertEquals(0, member.getBorrowedCount());
    }

    @Test
    @Order(3)
    @DisplayName("Should show book on loan after borrowing")
    void should_ShowBookOnLoan_when_BookIsBorrowed() {
        loanService.borrowBook(member, "B001");
        assertTrue(loanService.isOnLoan("B001"));
    }

    @Test
    @Order(4)
    @DisplayName("Should count active loans correctly after multiple borrows")
    void should_CountActiveLoans_when_MultipleBooksAreBorrowed() {
        Book book2 = new Book("B002", "Effective Java", "Bloch", "Technology", 2018);
        libraryService.addBook(book2);
        loanService.borrowBook(member, "B001");
        loanService.borrowBook(member, "B002");
        assertEquals(2, loanService.getTotalActiveLoans());
    }

    @Test
    @Order(5)
    @DisplayName("Should decrease active loans after book return")
    void should_DecreaseActiveLoans_when_BookIsReturned() {
        loanService.borrowBook(member, "B001");
        loanService.returnBook(member, "B001");
        assertEquals(0, loanService.getTotalActiveLoans());
    }

    // ============================
    // NEGATIVE TESTS
    // ============================

    @Test
    @Order(6)
    @DisplayName("Should throw exception when borrowing a book that is not available")
    void should_ThrowException_when_BorrowingUnavailableBook() {
        loanService.borrowBook(member, "B001");
        Member member2 = new Member("M002", "Usman Ali", "usman@email.com");
        assertThrows(IllegalStateException.class, () -> loanService.borrowBook(member2, "B001"));
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when member exceeds borrow limit")
    void should_ThrowException_when_MemberExceedsBorrowLimit() {
        Book b2 = new Book("B002", "Book Two", "Author", "Genre", 2020);
        Book b3 = new Book("B003", "Book Three", "Author", "Genre", 2020);
        Book b4 = new Book("B004", "Book Four", "Author", "Genre", 2020);
        libraryService.addBook(b2);
        libraryService.addBook(b3);
        libraryService.addBook(b4);
        loanService.borrowBook(member, "B001");
        loanService.borrowBook(member, "B002");
        loanService.borrowBook(member, "B003");
        assertThrows(IllegalStateException.class, () -> loanService.borrowBook(member, "B004"));
    }

    @Test
    @Order(8)
    @DisplayName("Should throw exception when returning a book not on loan")
    void should_ThrowException_when_ReturningBookNotOnLoan() {
        assertThrows(IllegalStateException.class, () -> loanService.returnBook(member, "B001"));
    }

    @Test
    @Order(9)
    @DisplayName("Should throw exception when member is null on borrow")
    void should_ThrowException_when_MemberIsNullOnBorrow() {
        assertThrows(IllegalArgumentException.class, () -> loanService.borrowBook(null, "B001"));
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(10)
    @DisplayName("Should borrow exactly at the borrow limit successfully")
    void should_BorrowSuccess_when_BorrowingUpToExactLimit() {
        Book b2 = new Book("B002", "Book Two", "Author", "Genre", 2020);
        Book b3 = new Book("B003", "Book Three", "Author", "Genre", 2020);
        libraryService.addBook(b2);
        libraryService.addBook(b3);
        loanService.borrowBook(member, "B001");
        loanService.borrowBook(member, "B002");
        loanService.borrowBook(member, "B003");
        assertEquals(3, member.getBorrowedCount());
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(11)
    @DisplayName("Should throw exception when book ID is null on borrow")
    void should_ThrowException_when_BookIdIsNullOnBorrow() {
        assertThrows(IllegalArgumentException.class, () -> loanService.borrowBook(member, null));
    }

    @Test
    @Order(12)
    @DisplayName("Should throw exception when wrong member tries to return a book")
    void should_ThrowException_when_WrongMemberReturnsBook() {
        loanService.borrowBook(member, "B001");
        Member otherMember = new Member("M999", "Ghost User", "ghost@email.com");
        assertThrows(IllegalStateException.class, () -> loanService.returnBook(otherMember, "B001"));
    }
}
