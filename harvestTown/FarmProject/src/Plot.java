import java.util.HashMap;

public class Plot {
    private int row;
    private int col;
    private String crop;
    private int maturity;
    private int water;
    private boolean pesticide;
    private int fertilizer;
    private static HashMap<Integer, Plot> plots = new HashMap<>();

    public Plot(int row, int col, String crop, int maturity, int water,
                boolean pesticide, int fertilizer) {
        this.row = row;
        this.col = col;
        this.crop = crop;
        this.maturity = maturity;
        this.water = water;
        this.pesticide = pesticide;
        this.fertilizer = fertilizer;
        plots.put(4 * row + col, this);
    }

    public Plot(int row, int col, String crop, int maturity, int water) {
        this(row, col, crop, maturity, water, false, 0);
    }

    public int getPlotNum() {
        return 4 * row + col;
    }

    public String getCrop() {
        return crop;
    }

    public int getMaturity() {
        return maturity;
    }

    public int getWater() {
        return water;
    }

    public boolean getPesticide() {
        return pesticide;
    }

    public int getFertilizer() {
        return fertilizer;
    }

    public static HashMap<Integer, Plot> getPlots() {
        return plots;
    }

    public void setCrop(String c) {
        this.crop = c;
        plots.replace(4 * row + col, this);
    }

    public void setMaturity(int m) {
        this.maturity = m;
        plots.replace(4 * row + col, this);
    }

    public void setWater(int w) {
        this.water = w;
        plots.replace(4 * row + col, this);
    }

    public void setPesticide(boolean p) {
        this.pesticide = p;
        plots.replace(4 * row + col, this);
    }

    public void setFertilizer(int f) {
        this.fertilizer = f;
        plots.replace(4 * row + col, this);
    }
}
