package model;

public class Art extends Item{
  private String artist;
  private int year;

  public Art(String itemId, String name, double startingPrice, String artist, int year){
    super(itemId, name, startingPrice);
    this.artist = artist;
    this.year = year;
  }

  public String getArtist(){
    return artist;
  }
  public void setArtist(String Artist){
    this.artist = artist;
  }

  public int getYear(){
    return year;
  }
  public void setYear(int year){
    this.year = year;
  }

  @Override
  public void showItemDetails() {
    System.out.println("[Art] ID: " + getItemId() + " | Tên tác phẩm: " + getName() + " | Giá khởi điểm: " + getStartingPrice() + " | Tác giả: " + artist + " | Năm: " + year);
  } 
}
