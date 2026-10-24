package model;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String itemId;
    private String name;
    private String description;
    private double startingPrice;
    private double currentHighestBid;

    public Item(String id, String name, double startingPrice) {
        this(id, name, "No description", startingPrice);
    }

    public Item(String id, String name, String description, double startingPrice) {
        if (startingPrice < 0) {
            throw new IllegalArgumentException("Starting price cannot be negative!");
        }
        this.itemId = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentHighestBid = startingPrice;
    }

    public abstract void showItemDetails();

    public abstract double getMinBidIncrement();

    public void printInfo() {
        System.out.println("=========================================");
        System.out.printf("Item ID: %s | Name: %s%n", itemId, name);
        System.out.println("Description: " + description);
        System.out.printf("Starting Price: %,.2f | Current Highest Bid: %,.2f%n", startingPrice, currentHighestBid);
        System.out.printf("Min Bid Increment: %,.2f%n", getMinBidIncrement());
        showItemDetails();
        System.out.println("=========================================");
    }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(double startingPrice) { this.startingPrice = startingPrice; }

    public double getCurrentHighestBid() { return currentHighestBid; }
    public void setCurrentHighestBid(double currentHighestBid) { this.currentHighestBid = currentHighestBid; }
}
