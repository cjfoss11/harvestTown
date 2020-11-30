import java.util.Random;

public class RandomEvent {
    private Random rand = new Random();
    private int randomNum;

    public int[] randomEvent(int n) {
        randomNum = rand.nextInt(n);
        if (randomNum == 0) {
            return this.rain();
        } else if (randomNum == 1) {
            return this.drought();
        } else if (randomNum == 2) {
            return this.locusts();
        } else {
            //no random event occurs
            return null;
        }
    }

    public int[] rain() {
        int[] rainWaterInc = new int[16];
        for (int i = 0; i < rainWaterInc.length; i++) {
            int random = rand.nextInt(3);
            rainWaterInc[i] = random;
        }
        return rainWaterInc;
    }
    public int[] drought() {
        int[] droughtWaterDec = new int[16];
        for (int i = 0; i < droughtWaterDec.length; i++) {
            int random = rand.nextInt(3);
            droughtWaterDec[i] = random;
        }
        return droughtWaterDec;
    }
    public int[] locusts() {
        int[] deadOrAlive = new int[16];
        for (int i = 0; i < deadOrAlive.length; i++) {
            int random = rand.nextInt(2);
            deadOrAlive[i] = random;
        }
        return deadOrAlive;
    }

    public int getRandomNum() {
        return this.randomNum;
    }
}