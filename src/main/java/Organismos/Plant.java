package Organismos;

import Estados.Plantas.DayState;
import Estados.Plantas.NightState;
import Estados.Plantas.PlantState;
import Estrategias.Reproduccion_P.Fragmentation;
import Estrategias.Reproduccion_P.ReproducePlStrategy;
import Estructuras.Plantas.RootSystem;

import Mundo.World;
import Genes.Gen;
import Genes.GenesTemplates;

import java.util.Map;

public class Plant extends Lifeform {
    /// Estatico
    public static final PlantState DAY_STATE = new DayState();
    public static final PlantState NIGHT_STATE = new NightState();

    /// Atributos
    private final ReproducePlStrategy reproduction;
    private final RootSystem rootSystem;
    private PlantState state;

    /// Constructors
    public Plant() {
        super(GenesTemplates.plantTemplate());
        this.rootSystem = new RootSystem();
        this.state = new DayState();
        this.reproduction = new Fragmentation();
    }

    public Plant(Map<Gen,Integer> genes, int energy) {
        super(genes, energy);
        this.rootSystem = new RootSystem();
        this.state = new DayState();
        this.reproduction = new Fragmentation();
    }

    public Plant(Map<Gen,Integer> genes, int energy, RootSystem rootSystem) {
        super(genes, energy);
        this.rootSystem = rootSystem;
        this.state = new DayState();
        this.reproduction = new Fragmentation();
    }

    /// Turno
    @Override
    public void turn() {
        rootSystem.updateSystem();

        photosynthesis(); metabolism();

        if (health < genes.get(Gen.MAX_HEALTH)) curation();
        if ((isGrowing()) && (energy / genes.get(Gen.MAX_ENERGY) > 0.9)) grow();

        if (hasEnergy(genes.get(Gen.MAX_ENERGY))) {
            double budget = (energy + reserve)/10.0;
            budget -= useReserve(rootSystem.growRoots(budget));

            if (rootSystem.getIntegrity() > 80)
                useReserve(rootSystem.takeRoots(budget,
                        genes.get(Gen.RESISTANCE_WATER)));
        }

        if (hasEnergy(genes.get(Gen.MAX_ENERGY) * 2))
            reproduction();
    }

    /// Métodos vegetales
    public void photosynthesis() {
        state.photosynthesis(this);
    }

    public double photosynthesis(int light, double water) {
        int lightResistance = genes.get(Gen.LIGHT_RESISTANCE);
        if (light > lightResistance) health -= (light - lightResistance) /10.0;

        if (water <= 0 || light <= 0) return 0;

        double lightFactor = light/100.0;
        double waterFactor = water / genes.get(Gen.QUOTA_WATER);
        return genes.get(Gen.PHOTO_EFFICIENCY) * lightFactor *
                waterFactor * getAgeFactor();
    }

    public double collectWater() {
        return rootSystem.collectWater(genes.get(Gen.RESISTANCE_WATER),
                genes.get(Gen.QUOTA_WATER));
    }

    public void takeFirstRoot() {
        if (!rootSystem.isEmpty()) return;
        useEnergy(rootSystem.takeRoot(
                null, World.getWorld().getTile(this),10
        ));
    }

    /// Métodos metabólicos
    @Override
    protected void metabolism() {
        state.metabolism(this);
    }

    @Override
    public double getQuota() {
        return super.getQuota() +
                0.25*(rootSystem.size() - 1);
    }

    /// Métodos reproductivos
    @Override
    protected void reproduction() {
        reproduction.reproduction(this);
    }

    public RootSystem getRootSystem() {
        return rootSystem;
    }

    /// Otros
    public void setState(PlantState state) {
        this.state = state;
    }

    @Override
    public boolean isDead() {
        boolean death = super.isDead();
        if (death) rootSystem.destroySystem();
        return death;
    }

    @Override
    public String toString() {
        return "P";
    }

    public String getStatus() {
        return String.format("%s |%s",
                super.getStatus(),rootSystem.infrastructure());
    }
}
