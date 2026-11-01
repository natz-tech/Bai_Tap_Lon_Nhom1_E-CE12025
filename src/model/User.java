package model;

public abstract class User implements AuctionObserver {
    private String id;
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract void displayRole();

    @Override
    public void onNewBid(Auction auction, Bid bid) {
        System.out.println("[THÔNG BÁO - User " + name + "]: Phiên " + auction.getAuctionId() 
                + " vừa có giá mới " + bid.getAmount() + "!");
    }

    @Override
    public void onAuctionFinished(Auction auction) {
        System.out.println("[THÔNG BÁO - User " + name + "]: Phiên đấu giá " 
                + auction.getAuctionId() + " đã kết thúc!");
    }
}
