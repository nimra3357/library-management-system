package com.library;

/**
 * Represents a Member of the Library Management System.
 */
public class Member {

    private String memberId;
    private String name;
    private String email;
    private int borrowedCount;
    public static final int MAX_BORROW_LIMIT = 3;

    public Member(String memberId, String name, String email) {
        if (memberId == null || memberId.trim().isEmpty())
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email address");

        this.memberId     = memberId.trim();
        this.name         = name.trim();
        this.email        = email.trim();
        this.borrowedCount = 0;
    }

    public String getMemberId()    { return memberId; }
    public String getName()        { return name; }
    public String getEmail()       { return email; }
    public int    getBorrowedCount() { return borrowedCount; }

    public boolean canBorrow() {
        return borrowedCount < MAX_BORROW_LIMIT;
    }

    public void incrementBorrow() {
        if (!canBorrow())
            throw new IllegalStateException("Borrow limit already reached");
        borrowedCount++;
    }

    public void decrementBorrow() {
        if (borrowedCount <= 0)
            throw new IllegalStateException("No borrowed books to return");
        borrowedCount--;
    }

    @Override
    public String toString() {
        return "Member{id='" + memberId + "', name='" + name + "', borrowed=" + borrowedCount + "}";
    }
}
