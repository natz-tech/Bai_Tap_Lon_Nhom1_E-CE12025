package model;

public class Art extends Item {
    private String artist;
    private int yearCreated;

    public Art(String id, String name, double startingPrice, String artist, int yearCreated) {
        super(id, name, startingPrice);
        this.artist = artist;
        this.yearCreated = yearCreated;
    }

    public Art(String id, String name, String description, double startingPrice, String artist, int yearCreated) {
        super(id, name, description, startingPrice);
        this.artist = artist;
        this.yearCreated = yearCreated;
    }

    @Override
    public void showItemDetails() {
        System.out.printf("[Art] Artist: %s | Year Created: %d%n", artist, yearCreated);
    }

    @Override
    public double getMinBidIncrement() {
        return getStartingPrice() * 0.10;
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getYearCreated() { return yearCreated; }
    public void setYearCreated(int yearCreated) { this.yearCreated = yearCreated; }
}
