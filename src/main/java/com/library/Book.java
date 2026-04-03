package com.library;

/**
 * Represents a Book entity in the Library Management System.
 */
public class Book {

    private String id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private boolean available;

    public Book(String id, String title, String author, String genre, int year) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty");
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Author cannot be null or empty");
        if (year < 0 || year > 2100)
            throw new IllegalArgumentException("Year must be between 0 and 2100");

        this.id = id.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.genre = genre;
        this.year = year;
        this.available = true;
    }

    public String getId()      { return id; }
    public String getTitle()   { return title; }
    public String getAuthor()  { return author; }
    public String getGenre()   { return genre; }
    public int    getYear()    { return year; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{id='" + id + "', title='" + title + "', author='" + author
                + "', available=" + available + "}";
    }
}
