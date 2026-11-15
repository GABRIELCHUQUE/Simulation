package Organismos.Organos;

import Mundo.Tile;
import Mundo.World;
import Organismos.Animal;
import Organismos.Enums.Decision;
import Organismos.Lifeform;

import java.util.ArrayList;
import java.util.List;

public final class Brain {
    /// Atributos
    private final FrontalCortex frontalCortex;
    private final Hippocampus hippocampus;
    private final Hypothalamus hypothalamus;
    private final Adaptation adaptation;

    /// Constructor
    public Brain() {
        this.frontalCortex = new FrontalCortex();
        this.hippocampus = new Hippocampus();
        this.hypothalamus = new Hypothalamus();
        this.adaptation = new Adaptation();
    }

    /// Métodos de los organos
    public void updateNeeds(double energy, int maxEnergy, double hydration,
            int maxHydration, int awakeTime, int maxAwakeTime) {
        hypothalamus.updateHunger(energy,maxEnergy);
        hypothalamus.updateThirst(hydration,maxHydration);
        hypothalamus.updateSleep(awakeTime,maxAwakeTime);
    }

    public Decision takeDecision(double hydration, boolean danger, boolean canEat,
            boolean canDrink, boolean canReproduce) {
        return frontalCortex.takeDecision(hypothalamus,hydration,
                danger,canEat,canDrink,canReproduce);
    }

    public boolean recordFood(boolean success) {
        return adaptation.recordFood(success);
    }

    /// Métodos de percepción
    public Tile[] nearbyTiles(Animal me, int vision) {
        World world = World.getWorld();
        Tile myLocation = world.getTile(me);
        return world.getNeighborTiles(myLocation.getRow(), myLocation.getCol(), vision);
    }

    public List<Lifeform> nearbyLifeforms(Tile[] tiles) {
        List<Lifeform> lifeforms = new ArrayList<>();
        for (Tile t: tiles) {
            if (t.getSurface() instanceof Lifeform)
                lifeforms.add((Lifeform) t.getSurface());
        } return lifeforms;
    }

    /// Getters
    public double getHunger() {
        return (double) hypothalamus.getHunger() / 10;
    }

    public double getThirst() {
        return (double) hypothalamus.getThirst() / 10;
    }

    public double getSleep() {
        return (double) hypothalamus.getSleep() / 10;
    }
}
