package model;
import java.util.ArrayList;
import java.util.List;
public class Auction {
    public Item item;
    private List<Bid> bids;
    private boolean isActivate;
    public Auction(Item item) {
        this.item = item;
        this.bids = new ArrayList<>();
        this.isActivate = true;
    }
    public void addBid(Bid bid) {
    }


}