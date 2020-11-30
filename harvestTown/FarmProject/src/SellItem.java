public class SellItem extends InventoryItem {
    public SellItem(String n, String t, int q, int b, boolean p) {
        super(n, t, q, b, p);
    }

    @Override
    public String toString() {
        if (getType().equals("seed")) {
            return getName() + ": " + getQuantity() + " seeds --- Sell Price: "
                    + getSellPrice() + " coins";
        } else {
            return getName() + ": " + getQuantity() + " crops --- Sell Price: "
                    + getSellPrice() + " coins";
        }
    }
}
