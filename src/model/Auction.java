package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auction {
    
    // Enum matching your project specification (Section 3.1.4)[cite: 6]
    public enum AuctionStatus 
    {
        OPEN, RUNNING, FINISHED, PAID, CANCELED
    }

    private String auctionId;
    private Item item;
    private double currentHighestBid;
    private List<Bid> bids;
    private AuctionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Auction(String auctionId, Item item, double startingPrice, LocalDateTime startTime, LocalDateTime endTime) 
    {
        this.auctionId = auctionId;
        this.item = item;
        this.currentHighestBid = startingPrice;
        this.bids = new ArrayList<>();
        this.status = AuctionStatus.OPEN;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Thread-safe method to validate and add a bid
    public synchronized boolean addBid(Bid bid) 
    {
        // Can only bid if the auction is running
        if (status != AuctionStatus.RUNNING) 
        {
            return false; 
        }
        
        if (bid.getAmount() <= currentHighestBid) 
        {
            return false; // Bid must be strictly higher than the current price[cite: 6]
        }
        
        currentHighestBid = bid.getAmount();
        bids.add(bid);
        return true;
    }

    // Automatically check and close auction based on time (Section 3.1.4)[cite: 6]
    public void checkAndUpdateStatus() 
    {
        LocalDateTime now = LocalDateTime.now();
        if (status == AuctionStatus.RUNNING && now.isAfter(endTime)) 
        {
            this.status = AuctionStatus.FINISHED;
        }
    }

    public void setStatus(AuctionStatus status) 
    {
        this.status = status;
    }

    // Getters
    public String getAuctionId() 
    {
        return auctionId;
    }

    public Item getItem() 
    {
        return item;
    }

    public double getCurrentHighestBid() 
    {
        return currentHighestBid;[cite: 6]
    }

    public List<Bid> getBids() 
    {
        return bids;
    }

    public AuctionStatus getStatus() 
    {
        return status;
    }
}
