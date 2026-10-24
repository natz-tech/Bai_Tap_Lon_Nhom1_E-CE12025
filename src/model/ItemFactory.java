package model;

public class ItemFactory {

    public enum ItemType {
        ELECTRONICS,
        ART,
        VEHICLE
    }

    public static Item createItem(String type, String id, String name, double startingPrice,
                                  String extraInfo, int extraNumber) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Item type cannot be null or empty!");
        }
        return createItem(ItemType.valueOf(type.trim().toUpperCase()), id, name,
                          "No description", startingPrice, extraInfo, extraNumber);
    }

    public static Item createItem(ItemType type, String id, String name, String description,
                                  double startingPrice, String extraInfo, int extraNumber) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid item type!");
        }

        switch (type) {
            case ELECTRONICS:
                return new Electronics(id, name, description, startingPrice, extraInfo, extraNumber);
            case ART:
                return new Art(id, name, description, startingPrice, extraInfo, extraNumber);
            case VEHICLE:
                return new Vehicle(id, name, description, startingPrice, extraInfo, extraNumber);
            default:
                throw new IllegalArgumentException("Unsupported item type: " + type);
        }
    }
}
