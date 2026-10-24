package model;

public class Vehicle extends Item{
  private String brand;
  private double mileage;

  public Vehicle(String itemId, String name, double startingPrice, String brand, double mileage){
    super(itemId, name, startingPrice);
    this.brand = brand;
    this.mileage = mileage;
  }

  public String getBrand(){
    return brand;
  }
  public void setBrand(String brand){
    this.brand = brand;
  }

  public double getMileage(){
    return mileage;
  }
  public void setMileage(double mileage){
    this.mileage = mileage;
  }
  
  @Override
  public void showItemDetails() {
    System.out.println("[Vehicle] ID: " + getItemId() + " | Tên xe: " + getName() + " | Giá khởi điểm: " + getStartingPrice() + " | Hãng: " + brand + " | Số km đã đi: " + mileage);
  }
}
  
