package model;

public class Bidder extends User{
  private double balance;

  public Bidder(String id, String name, String email, double balance){
    super(id, name, email);
    this.balance = balance;
  }

  public double getBalance(){
    return balance;
  }

  public void setBalance(double balance){
    this.balance = balance;
  }

  @Override
  public void displayRole(){
    System.out.println("Role: Bidder (Người mua/Đấu giá) - Tên: " + getName());
  }
}
