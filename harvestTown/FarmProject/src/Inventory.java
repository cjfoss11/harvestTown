import java.util.HashMap;

public class Inventory {
    private static final HashMap<String, InventoryItem> SEEDS = new HashMap<>();
    private static final HashMap<String, InventoryItem> CROPS = new HashMap<>();
    private static final HashMap<String, InventoryItem> PRODUCTS = new HashMap<>();
    private static final HashMap<String, InventoryItem> WHOLE_INVENTORY = new HashMap<>();
    private static int day = 1;

    public static HashMap<String, InventoryItem> getSeeds() {
        return SEEDS;
    }

    public static HashMap<String, InventoryItem> getCrops() {
        return CROPS;
    }

    public static HashMap<String, InventoryItem> getProducts() {
        return PRODUCTS;
    }

    public static HashMap<String, InventoryItem> getWholeInventory() {
        SEEDS.forEach(WHOLE_INVENTORY::put);
        CROPS.forEach(WHOLE_INVENTORY::put);
        PRODUCTS.forEach(WHOLE_INVENTORY::put);
        return WHOLE_INVENTORY;
    }

    public static int getDay() {
        return day;
    }
    public static void setDay(int newDay) {
        day = newDay;
    }

    public static void incDay() {
        day += 1;
    }

    public static String getInventoryString() {
        String inventoryString = "Seeds:\n";
        int numSeeds;
        int numCrops;
        int numProducts;
        for (String seed : SEEDS.keySet()) {
            InventoryItem seeds = SEEDS.get(seed);
            numSeeds = seeds.getQuantity();
            inventoryString += "  -" + seed + ": " + numSeeds + " seeds\n";
        }
        inventoryString += "Crops:\n";
        for (String crop : CROPS.keySet()) {
            InventoryItem crops = CROPS.get(crop);
            numCrops = crops.getQuantity();
            inventoryString += "  -" + crop + ": " + numCrops + " crops\n";
        }
        inventoryString += "Products:\n";
        for (String product: PRODUCTS.keySet()) {
            InventoryItem crops = PRODUCTS.get(product);
            numProducts = crops.getQuantity();
            inventoryString += " -" + product + ": " + numProducts + "\n";
        }
        return inventoryString;
    }

    public static int getNumOfInventory() {
        int numSeeds = 0;
        int numCrops = 0;
        int numProducts = 0;
        for (String seed: SEEDS.keySet()) {
            InventoryItem seeds = SEEDS.get(seed);
            numSeeds += seeds.getQuantity();
        }
        for (String crop: CROPS.keySet()) {
            InventoryItem crops = CROPS.get(crop);
            numCrops += crops.getQuantity();
        }
        for (String product: PRODUCTS.keySet()) {
            InventoryItem prods = PRODUCTS.get(product);
            numProducts += prods.getQuantity();
        }
        return numSeeds + numCrops + numProducts;
    }
}