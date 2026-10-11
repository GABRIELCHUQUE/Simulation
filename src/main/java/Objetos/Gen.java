package Objetos;

public enum Gen {
    MAX_ENERGY(1, 100),
    QUOTA_ENERGY(1, 10),
    MAX_HEALTH(1, 200),
    CURATION_FACTOR(0, 10),

    MATURITY(1, 500),
    GROW_FACTOR(1, 10),
    OLD_AGE_START(1, 1000),

    PHOTO_EFFICIENCY(0, 20),
    LIGHT_RESISTANCE(0, 200),
    RESISTANCE_WATER(0, 100),
    QUOTA_WATER(1, 10);

    private final int min;
    private final int max;

    Gen(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int clamp(int value) {
        return Math.max(min, Math.min(max, value));
    }
}
