public class FarmWorker {
    private final String name;
    private final double baseWage;
    private final String skillLevel;
    private double efficiency;
    private double wage;
    private double multiplier;

    public FarmWorker(String inN, double inW, String skillLevel) {
        name = inN;
        baseWage = inW;
        this.skillLevel = skillLevel;
        multiplier = 3 - SetupScreen.getGameDifficulty();
    }

    public double calculateEfficiency() {
        if (skillLevel.equals("Poor")) {
            efficiency = 0.3;
        } else if (skillLevel.equals("Skilled")) {
            efficiency = 0.5;
        } else if (skillLevel.equals("Highly Skilled")) {
            efficiency = 0.8;
        }
        return efficiency;
    }
    public String getName() {
        return name;
    }

    public double calculateWage() {
        wage = (multiplier * baseWage) + baseWage + (calculateEfficiency() * baseWage);
        return wage;
    }
    public double getWage() {
        return wage;
    }

    public String toString() {
        return this.name + " --- Skill Level: " + this.skillLevel
                + " --- Daily Wage: " + calculateWage() + " coins";
    }

}
