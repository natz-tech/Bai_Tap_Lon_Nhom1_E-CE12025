package model;

public class Electronics extends Item {
    private String brand;
    private int warrantyMonths;

    public Electronics(String id, String name, double startingPrice, String brand, int warrantyMonths) {
        super(id, name, startingPrice);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    public Electronics(String id, String name, String description, double startingPrice, String brand, int warrantyMonths) {
        super(id, name, description, startingPrice);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void showItemDetails() {
        System.out.printf("[Do dien tu] Thuong hieu: %s | Bao hanh: %d thang%n", brand, warrantyMonths);
    }

    @Override
    public double getMinBidIncrement() {
        return getStartingPrice() * 0.05;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }
}