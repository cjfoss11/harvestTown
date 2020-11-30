import java.util.Random;

public class MarketItem {
    private final String itemType;
    private final String itemName;
    private final double basePrice;
    private final double rarity;
    private final double multiplier;
    private double price;

    public String getItemName() {
        return itemName;
    }
    public double getPrice() {
        return price;
    }
    public String getItemType() {
        return itemType;
    }
    private double getRarity() {
        return rarity;
    }

    public MarketItem(String itemType, String itemName, double basePrice, double rarity) {
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.itemType = itemType;
        this.rarity = rarity;
        this.multiplier = 3 - SetupScreen.getGameDifficulty();
    }

    public double calculatePrice(String itemType) {
        Random rand = new Random();
        if (itemType.equals("Seed")) {
            price = (rarity * 10) + (basePrice * multiplier) + (rand.nextInt(100) + 50);
        } else {
            price = (rarity * 100) + (basePrice * multiplier) + (rand.nextInt(1000) + 100);
        }
        return price;
    }

    @Override
    public String toString() {
        if (itemType.equals("Seed")) {
            return (this.itemName + " --- " + itemType + " Price: "
                    + calculatePrice(getItemType()) + " coins");
        }
        return this.itemName + " --- Price: " + calculatePrice(getItemType()) + " coins";
    }
}