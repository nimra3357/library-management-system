package com.library;

import java.util.*;

/**
 * Core service for managing the book inventory in the library.
 */
public class LibraryService {

    private final Map<String, Book> bookCatalog = new HashMap<>();

    public void addBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book cannot be null");
        if (bookCatalog.containsKey(book.getId()))
            throw new IllegalStateException("Book with ID '" + book.getId() + "' already exists");
        bookCatalog.put(book.getId(), book);
    }

    public void removeBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty())
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        if (!bookCatalog.containsKey(bookId))
            throw new NoSuchElementException("Book with ID '" + bookId + "' not found");
        bookCatalog.remove(bookId);
    }

    public Book findById(String bookId) {
        if (bookId == null || bookId.trim().isEmpty())
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        return bookCatalog.get(bookId);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookCatalog.values());
    }

    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book b : bookCatalog.values()) {
            if (b.isAvailable()) available.add(b);
        }
        return available;
    }

    public int getTotalCount()     { return bookCatalog.size(); }
    public boolean isEmpty()       { return bookCatalog.isEmpty(); }
    public boolean exists(String id) { return bookCatalog.containsKey(id); }
}
