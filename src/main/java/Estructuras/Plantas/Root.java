package Estructuras.Plantas;

import Mundo.Tile;

public class Root {
    /// Atributos
    private int integrity;

    /// Constructor
    public Root(int integrity) {
        this.integrity = integrity;
    }

    /// Getter
    public int getIntegrity() {
        return integrity;
    }

    /// Métodos
    public void grow() {
        integrity = Math.min(100,integrity+10);
    }

    public double collectWater(Tile tile, int waterResistance, int waterQuota) {
        // Posible daño por agua
        double water = tile.getHumidity();
        if (water > waterResistance)
            integrity -= (int) Math.ceil ((water - waterResistance) / 10.0);

        // Potencial de agua a absorber
        double capacity = waterQuota * (integrity / 100.0);

        // Cantidad de agua realmente absorbida
        double collected = Math.min(capacity,water);
        tile.setHumidity(water - collected);
        return collected;
    }
}
