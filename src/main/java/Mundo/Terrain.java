package Mundo;

public enum Terrain {
    EARTH(20),
    SHALLOW_WATER(1000);

    /// Atributos
    private final int humidityLv;

    /// Constructor
    Terrain(int humidityLv) {
        this.humidityLv = humidityLv;
    }

    /// Getter
    public int getHumidityLv() {
        return humidityLv;
    }
}
