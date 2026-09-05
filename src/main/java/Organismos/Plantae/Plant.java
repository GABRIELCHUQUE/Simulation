package Organismos.Plantae;

import Mundo.Square;
import Mundo.World;
import Objetos.Gen;
import Organismos.Lifeform;

import java.util.Map;

public class Plant extends Lifeform {
    /// Constructors
    public Plant(int energy) {
        super(energy);
        genes.put(Gen.PHOTO_EFFICIENCY,2);
        genes.put(Gen.LIGHT_RESISTANCE,80);
    }

    public Plant(Map<Gen, Integer> genes, int energy) {
        super(genes, energy);
    }

    /// Funciones específicas
    protected void photosynthesis(int light, int water) {
        double lightFactor = light/100.0;
        double waterFactor = water/100.0;
        int energy = (int) Math.ceil(genes.get(Gen.PHOTO_EFFICIENCY)
                *lightFactor*waterFactor);
        addEnergy(energy);
        if (light > genes.get(Gen.LIGHT_RESISTANCE)) {
            health -= (light - genes.get(Gen.LIGHT_RESISTANCE))/10;
        }
    }

    /// Funciones globales
    @Override
    public void reproduction() {

    }

    @Override
    public void turn(World world, int col, int row) {
        metabolism();

        Square position = world.getSquare(col,row);
        photosynthesis(world.getLight(),position.getHumidity());
        if (health < genes.get(Gen.MAX_HEALTH))
            curation();
        if ((double) energy / genes.get(Gen.MAX_ENERGY) > 0.5)
            grow();
    }
}
