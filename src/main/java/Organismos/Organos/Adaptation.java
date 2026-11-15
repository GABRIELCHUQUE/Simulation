package Organismos.Organos;

public final class Adaptation {
    /// Atributos
    private int successfulFeeding;
    private int failedFeeding;
    private int dangerEvents;

    /// Constructor
    public Adaptation() {
        this.successfulFeeding = 0;
        this.failedFeeding = 0;
        this.dangerEvents = 0;
    }

    /// Métodos
    public boolean recordFood(boolean success) {
        if (success) successfulFeeding++;
        else failedFeeding++;
        return success;
    }

    public void recordDanger() {
        dangerEvents++;
    }

    public double feedSelfConfidence() {
        int value = successfulFeeding + failedFeeding;
        return value == 0 ? 0.5 : (double) successfulFeeding / value;
    }

    /// Getters
    public int getDangerEvents() {
        return dangerEvents;
    }
}

