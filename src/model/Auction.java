package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auction {

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

    private List<AuctionObserver> observers = new ArrayList<>();

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

    public void addObserver(AuctionObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(AuctionObserver observer) {
        observers.remove(observer);
    }

    public void notifyObserversNewBid(Bid bid) {
        for (AuctionObserver observer : observers) {
            observer.onNewBid(this, bid);
        }
    }

    public void notifyObserversAuctionFinished() {
        for (AuctionObserver observer : observers) {
            observer.onAuctionFinished(this);
        }
    }

    public synchronized boolean addBid(Bid bid)
    {
        if (status != AuctionStatus.RUNNING)
        {
            return false;
        }

        if (bid.getAmount() <= currentHighestBid)
        {
            return false;
        }

        currentHighestBid = bid.getAmount();
        bids.add(bid);

        notifyObserversNewBid(bid);

        return true;
    }

    public void checkAndUpdateStatus()
    {
        LocalDateTime now = LocalDateTime.now();
        if (status == AuctionStatus.RUNNING && now.isAfter(endTime))
        {
            this.status = AuctionStatus.FINISHED;
            notifyObserversAuctionFinished();
        }
    }

    public void finishAuction()
    {
        this.status = AuctionStatus.FINISHED;
        notifyObserversAuctionFinished();
    }

    public void setStatus(AuctionStatus status)
    {
        this.status = status;
        if (status == AuctionStatus.FINISHED) {
            notifyObserversAuctionFinished();
        }
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Item getItem() {
        return item;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public AuctionStatus getStatus() {
        return status;
    }
}
