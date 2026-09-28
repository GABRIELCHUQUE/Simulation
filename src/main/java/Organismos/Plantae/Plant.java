package Organismos.Plantae;

import Mundo.Square;
import Mundo.World;
import Objetos.Gen;
import Objetos.GenesTemplates;
import Organismos.Lifeform;

import java.util.Map;

public class Plant extends Lifeform {
    /// Constructors
    public Plant() {
        super(GenesTemplates.plantTemplate());
    }

    public Plant(Map<Gen, Integer> genes) {
        super(genes);
    }

    public Plant(Map<Gen, Integer> genes, int energy) {
        super(genes, energy);
    }

    /// Funciones específicas
    protected int photosynthesis(int light, int water) {
        // Posible daño por luz
        int lightResistance = genes.get(Gen.LIGHT_RESISTANCE);
        if (light > lightResistance) health -= (double) (light - lightResistance) /10;

        // Posible daño por agua
        int waterResistance = genes.get(Gen.RESISTANCE_WATER);
        if (water > waterResistance) health -= (double) (water - waterResistance)/10;

        // Sin recursos
        int usable = Math.min(genes.get(Gen.QUOTA_WATER),water);
        if (usable <= 0 || light <= 0) return 0;

        // Obtención de energía
        double lightFactor = light/100.0;
        double waterFactor = Math.min(1,(double) usable/genes.get(Gen.QUOTA_WATER));
        int energy = (int) Math.ceil(genes.get(Gen.PHOTO_EFFICIENCY)*
                lightFactor*waterFactor*getAgeFactor());

        addEnergy(energy);
        return usable;
    }

    /// Funciones globales
    @Override
    public void reproduction() {

    }

    @Override
    public void turn() {
        World world = World.getWorld();
        Square position = world.getSquare(this);

        metabolism(world.getLight() > 0);
        int waterUsed = photosynthesis(world.getLight(),position.getHumidity());
        if (waterUsed > 0) position.setHumidity(position.getHumidity() - waterUsed);

        if (health < genes.get(Gen.MAX_HEALTH)) curation();
        if ((isGrowing()) && ((double) energy / genes.get(Gen.MAX_ENERGY) > 0.9)) grow();
    }

    /// toString
    @Override
    public String toString() {
        return "Plant";
    }
}
