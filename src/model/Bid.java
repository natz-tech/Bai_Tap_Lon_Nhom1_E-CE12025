package model;
import java.time.LocalDateTime;

public class Bid {
    private User bidder;
    private double amount;
    private LocalDateTime bidTime;
    public Bid(User bidder, double amount){
        this.bidder = bidder;
        this.amount = amount;
        this.bidTime = LocalDateTime.now();
    }
}
