import java.util.Random;

public class InventoryItem {
    private final String name;
    private final String type;
    private final boolean pesticide;
    private int quantity;
    private int basePrice = 100;
    private double sellPrice;

    public InventoryItem(String n, String t, int q, int b, boolean p) {
        name = n;
        type = t;
        pesticide = p;
        quantity = q;
        basePrice = b;
        sellPrice = calculateSellPrice();
    }

    public InventoryItem(String n, String t, int q) {
        this(n, t, q, 100, false);
    }

    public InventoryItem(String n, String t, int q, int b) {
        this(n, t, q, b, false);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getPesticide() {
        return pesticide;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public double calculateSellPrice() {
        double difficultyMultiplier = SetupScreen.getGameDifficulty();
        if (difficultyMultiplier == 2.5) {
            sellPrice = basePrice * 2.5 + (new Random().nextInt(200) + 50);
        } else if (difficultyMultiplier == 1.0) {
            sellPrice = basePrice * 1.0 + (new Random().nextInt(200) + 50);
        } else {
            sellPrice = basePrice * 0.5 + (new Random().nextInt(200) + 50);
        }

        if (pesticide) {
            return sellPrice * .5;
        }
        return sellPrice;
    }

    public String toString() {
        if (this.type.equals("seed")) {
            return name + ": " + quantity + " seeds";
        } else if (this.type.equals("crop")) {
            if (pesticide) {
                return name + " (p): " + quantity + " crops";
            }
            return name + ": " + quantity + " crops";
        }
        return name + ": " + quantity;
    }
}
