package model;

public class Electronics extends Item{
  private int warrantyPeriod;

  public Electronics (String itemId, String name, double startingPrice, int warrantyPeriod){
    super(itemId, name, startingPrice);
    this.warrantyPeriod = warrantyPeriod;
  }

  public int getWarrantyPeriod(){
    return warrantyPeriod;
  }

  public void setWarrantyPeriod(int warrantyPeriod){
    this.warrantyPeriod = warrantyPeriod;
  }
  
  @Override
  public void showItemDetails() {
    System.out.println("[Electronics] ID: " + getItemId() + " | Tên: " + getName() + " | Giá khởi điểm: " + getStartingPrice() + " | Bảo hành: " + warrantyPeriod + " tháng");
  }
}
