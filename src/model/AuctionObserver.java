package model;

public interface AuctionObserver {
    void onNewBid(Auction auction, Bid bid);

    void onAuctionFinished(Auction auction);
}
