package com.library;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles borrowing and returning of books for library members.
 */
public class LoanService {

    private final LibraryService libraryService;
    // Tracks which member has which book: bookId -> memberId
    private final Map<String, String> activeLoans = new HashMap<>();

    public LoanService(LibraryService libraryService) {
        if (libraryService == null)
            throw new IllegalArgumentException("LibraryService cannot be null");
        this.libraryService = libraryService;
    }

    public void borrowBook(Member member, String bookId) {
        if (member == null)
            throw new IllegalArgumentException("Member cannot be null");
        if (bookId == null || bookId.trim().isEmpty())
            throw new IllegalArgumentException("Book ID cannot be null or empty");

        Book book = libraryService.findById(bookId);
        if (book == null)
            throw new NoSuchFieldError("Book with ID '" + bookId + "' does not exist");
        if (!book.isAvailable())
            throw new IllegalStateException("Book is currently not available");
        if (!member.canBorrow())
            throw new IllegalStateException("Member has reached the maximum borrow limit");

        book.setAvailable(false);
        member.incrementBorrow();
        activeLoans.put(bookId, member.getMemberId());
    }

    public void returnBook(Member member, String bookId) {
        if (member == null)
            throw new IllegalArgumentException("Member cannot be null");
        if (bookId == null || bookId.trim().isEmpty())
            throw new IllegalArgumentException("Book ID cannot be null or empty");

        Book book = libraryService.findById(bookId);
        if (book == null)
            throw new NoSuchFieldError("Book with ID '" + bookId + "' does not exist");
        if (book.isAvailable())
            throw new IllegalStateException("Book was not on loan");
        if (!member.getMemberId().equals(activeLoans.get(bookId)))
            throw new IllegalStateException("This book was not borrowed by this member");

        book.setAvailable(true);
        member.decrementBorrow();
        activeLoans.remove(bookId);
    }

    public boolean isOnLoan(String bookId) {
        return activeLoans.containsKey(bookId);
    }

    public int getTotalActiveLoans() {
        return activeLoans.size();
    }
}
