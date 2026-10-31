package manager;

import model.Auction;
import java.util.HashMap;
import java.util.Map;

public class AuctionManager {
    private static AuctionManager instance;
    private Map<String, Auction> auctionRegistry;

    // Private constructor prevents external instantiation
    private AuctionManager() {
        auctionRegistry = new HashMap<>();
    }

    // Global thread-safe access point (Singleton Pattern)[cite: 11]
    public static synchronized AuctionManager getInstance() {
        if (instance == null) {
            instance = new AuctionManager();
        }
        return instance;
    }

    public void addAuction(Auction auction) {
        auctionRegistry.put(auction.getAuctionId(), auction);
    }

    public Auction getAuction(String auctionId) {
        return auctionRegistry.get(auctionId);
    }

    public Map<String, Auction> getAllAuctions() {
        return auctionRegistry;
    }
}
