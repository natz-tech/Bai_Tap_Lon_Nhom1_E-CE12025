package model;

import java.time.LocalDateTime;

public class Bid {
    private User bidder;
    private double amount;
    private LocalDateTime bidTime;

    public Bid(User bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
        this.bidTime = LocalDateTime.now();
    }

    // Add these getters so Auction and UI can read the data
    public User getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }
}
