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
    public abstract void showItemDetails();

}
