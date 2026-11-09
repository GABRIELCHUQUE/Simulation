package Estructuras.Plantas;

import Estructuras.Generales.GraphNode;
import Mundo.Tile;

public class Root extends GraphNode {
    /// Constructor
    public Root(int integrity) {
        super(integrity);
    }

    /// Funciones
    public void grow() {
        integrity = Math.min(100,integrity+10);
    }

    public double collectWater(Tile tile, int waterResistance, int waterQuota) {
        double water = tile.getHumidityLv();
        if (water > waterResistance)
            integrity -= (int) Math.ceil ((water - waterResistance) / 10.0);

        double capacity = waterQuota * (integrity / 100.0);

        double collected = Math.min(capacity,water);
        tile.setHumidityLv(water - collected);
        return collected;
    }
}
