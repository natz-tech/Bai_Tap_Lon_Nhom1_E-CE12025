package model;

public class Vehicle extends Item {
    private String modelName;
    private int mileage;

    public Vehicle(String id, String name, double startingPrice, String modelName, int mileage) {
        super(id, name, startingPrice);
        this.modelName = modelName;
        this.mileage = mileage;
    }

    public Vehicle(String id, String name, String description, double startingPrice, String modelName, int mileage) {
        super(id, name, description, startingPrice);
        this.modelName = modelName;
        this.mileage = mileage;
    }

    @Override
    public void showItemDetails() {
        System.out.printf("[Phuong tien] Dong xe: %s | ODO da di: %,d km%n", modelName, mileage);
    }

    @Override
    public double getMinBidIncrement() {
        return Math.max(500000, getStartingPrice() * 0.02);
    }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
}