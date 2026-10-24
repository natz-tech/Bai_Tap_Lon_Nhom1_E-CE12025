package model;

public class Seller extends User{
  private String storeName;

  public Seller(String id, String name, String email, String storeName){
    super(id, name, email);
    this.storeName = storeName;
  }

  public String getStoreName(){
    return storeName;
  }

  public void setStoreName(String storeName){
    this storeName = storeName;
  }

  @Override
  public void displayRole() {
        System.out.println("Role: Seller (Người bán) - Tên: " + getName() + " - Cửa hàng: " + storeName);
  }
}
