package com.library;

/**
 * Calculates overdue fines for borrowed books.
 */
public class FineCalculator {

    private static final int    FREE_DAYS  = 14;    // 2 weeks free
    private static final double DAILY_FINE = 5.0;   // Rs. 5 per day
    private static final double MAX_FINE   = 500.0; // Cap at Rs. 500

    /**
     * Returns the fine for a given number of days the book was kept.
     */
    public double calculateFine(int daysKept) {
        if (daysKept < 0)
            throw new IllegalArgumentException("Days kept cannot be negative");

        if (daysKept <= FREE_DAYS) return 0.0;

        double fine = (daysKept - FREE_DAYS) * DAILY_FINE;
        return Math.min(fine, MAX_FINE);
    }

    public boolean isOverdue(int daysKept) {
        if (daysKept < 0)
            throw new IllegalArgumentException("Days kept cannot be negative");
        return daysKept > FREE_DAYS;
    }

    public int getFreeDays()    { return FREE_DAYS; }
    public double getDailyFine(){ return DAILY_FINE; }
    public double getMaxFine()  { return MAX_FINE; }
}
