package model;

public class Admin extends User{
  private String level;

  public Admin(String id, String name, String email, String level){
    super(id, name, email);
    this.level = level;
  }

  public String getLevel(){
    return level;
  }

  public void setLevel(String level){
    this.level = level;
  }

  @Override
  public void displayRole(){
    System.out.println("Role: Admin (Quản trị viên) - Tên: " + getName() + " - Cấp độ: " + level);
  }
}
