package model;

public abstract class Item{
    private String itemId;
    private String name;
    private double startingPrice;

    public Item(String id, String name, double startingPrice){
        this.itemId = itemId;
        this.name = name;
        this.startingPrice = startingPrice;
    }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(double  startingPrice) { this.startingPrice = startingPrice; }
    
    public abstract void showItemDetails();
}
