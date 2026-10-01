package Organismos.Plantas;

import Estructuras.Plantas.RootSystem;
import Mundo.World;
import Objetos.Gen;
import Objetos.GenesTemplates;
import Organismos.Lifeform;

import java.util.Map;

public class Plant extends Lifeform {
    /// Atributos
    private final RootSystem rootSystem;

    /// Constructors
    public Plant() {
        super(GenesTemplates.plantTemplate());
        this.rootSystem = new RootSystem();
    }

    public Plant(Map<Gen, Integer> genes) {
        super(genes);
        this.rootSystem = new RootSystem();
    }

    public Plant(Map<Gen, Integer> genes, int energy) {
        super(genes, energy);
        this.rootSystem = new RootSystem();
    }

    /// Funciones específicas
    protected double photosynthesis(int light, double water) {
        // Posible daño por luz
        int lightResistance = genes.get(Gen.LIGHT_RESISTANCE);
        if (light > lightResistance) health -= (double) (light - lightResistance) /10;

        // Sin recursos
        if (water <= 0 || light <= 0) return 0;

        // Obtención de energía
        double lightFactor = light/100.0;
        double waterFactor = water/ genes.get(Gen.QUOTA_WATER);
        return genes.get(Gen.PHOTO_EFFICIENCY)* lightFactor*
                waterFactor*getAgeFactor();
    }

    private double collectWater() {
        return rootSystem.collectWater(genes.get(Gen.RESISTANCE_WATER),
                genes.get(Gen.QUOTA_WATER));
    }

    public void takeFirstRoot() {
        consumeEnergy(rootSystem.takeFirstRoot
                (World.getWorld().getTile(this)));
    }

    /// Alteraciones
    @Override
    protected double getQuota() {
        return super.getQuota() +
                0.25*(rootSystem.size() - 1);
    }

    /// Funciones globales
    @Override
    public void reproduction() {

    }

    @Override
    public void turn() {
        // ELIMINAR RAICES MUERTAS
        rootSystem.lostRoots();

        // FOTOSINTESIS
        World world = World.getWorld();
        boolean hasLight = world.getLight() > 0;
        metabolism(hasLight);
        if (hasLight) {
            double energy = photosynthesis(world.getLight(),collectWater());
            addEnergy(energy);
        }

        // REPARACION Y CRECIMIENTO
        if (health < genes.get(Gen.MAX_HEALTH)) curation();
        if ((isGrowing()) && (energy / genes.get(Gen.MAX_ENERGY) > 0.9)) grow();

        if (energy + reserve > genes.get(Gen.MAX_ENERGY)) {
            double budget = (energy + reserve)/10.0;
            int used = rootSystem.growRoots(budget);
            budget -= used;

            if (rootSystem.averageIntegrity() > 80) {
                used += rootSystem.takeRoots(budget,
                        genes.get(Gen.RESISTANCE_WATER));
            } consumeReserves(used);
        }
    }

    /// toString
    @Override
    public String getStatus() {
        return String.format("%s |%s",
                super.getStatus(),rootSystem.infrastructure());
    }

    ///
    @Override
    public String toString() {
        return "Plant";
    }
}
