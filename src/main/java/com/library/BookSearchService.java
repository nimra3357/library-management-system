package com.library;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides search functionality across the library book catalog.
 */
public class BookSearchService {

    private final LibraryService libraryService;

    public BookSearchService(LibraryService libraryService) {
        if (libraryService == null)
            throw new IllegalArgumentException("LibraryService cannot be null");
        this.libraryService = libraryService;
    }

    public List<Book> searchByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            throw new IllegalArgumentException("Search keyword cannot be empty");

        String kw = keyword.trim().toLowerCase();
        return libraryService.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Author name cannot be empty");

        String a = author.trim().toLowerCase();
        return libraryService.getAllBooks().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(a))
                .collect(Collectors.toList());
    }

    public List<Book> searchByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty())
            throw new IllegalArgumentException("Genre cannot be empty");

        return libraryService.getAllBooks().stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre.trim()))
                .collect(Collectors.toList());
    }

    public List<Book> searchAvailableByTitle(String keyword) {
        return searchByTitle(keyword).stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
}
