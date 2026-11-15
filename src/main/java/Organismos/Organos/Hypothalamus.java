package Organismos.Organos;

public final class Hypothalamus {
    /// Estatico
    private static final int MAX = 1000;
    private static final int MIN = 0;

    /// Atributos
    private int hunger;
    private int thirst;
    private int sleepiness;

    /// Constructor
    public Hypothalamus() {
        this.hunger = MIN;
        this.thirst = MIN;
        this.sleepiness = MIN;
    }

    /// Métodos de satisfacción
    public void updateHunger(double energy, int maxEnergy) {
        double ratio = normalize(energy,maxEnergy);
        hunger = (int) Math.round(MAX * Math.pow(1.0 - ratio, 2));
    }

    public void updateThirst(double hydration, int maxHydration) {
        double ratio = normalize(hydration,maxHydration);
        thirst = (int) Math.round(MAX * Math.pow(1.0 - ratio, 2));
    }

    public void updateSleep(int awakeTime, int maxAwakeTime) {
        double ratio = normalize(awakeTime,maxAwakeTime);
        sleepiness = (int) Math.round(MAX * Math.pow(ratio, 3));
    }

    /// Otros
    private double normalize(double value, int maxValue) {
        return Math.max(0.0, Math.min(1.0, value / maxValue));
    }

    /// Getter
    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getSleep() {
        return sleepiness;
    }
}
