package Organismos;

import Objetos.Gen;

import java.util.HashMap;
import java.util.Map;

public abstract class Lifeform {
    /// Atributos
    protected Map<Gen, Integer> genes;
    protected int energy;
    protected int reserve;
    protected int health;
    protected int growth;

    /// Constructor
    public Lifeform(int energy) {
        this.genes = new HashMap<>();
        this.genes.put(Gen.MAX_ENERGY,20);
        this.genes.put(Gen.QUOTA_ENERGY,1);
        this.genes.put(Gen.MAX_HEALTH,50);
        this.genes.put(Gen.CURATION_FACTOR,1);
        this.genes.put(Gen.GROW_FACTOR,1);
        this.genes.put(Gen.OLD_AGE_START,50);

        this.health = genes.get(Gen.MAX_HEALTH);
        this.energy = energy;
        this.reserve = 0;
        this.growth = 0;
    }

    public Lifeform(Map<Gen, Integer> genes, int energy) {
        this.genes = genes;
        this.health = genes.get(Gen.MAX_HEALTH);
        this.energy = energy;
        this.reserve = 0;
        this.growth = 0;
    }

    /// Funciones globales
    public boolean isDead() {
        return health <= 0;
    }

    protected int getQuota() {
        double factor = Math.max(1,(double) growth / (double) genes.get(Gen.OLD_AGE_START));
        return (int) (genes.get(Gen.QUOTA_ENERGY) * factor);
    }

    protected void useEnergy(int amount) {
        int consumed = Math.min(energy, amount);
        energy -= consumed;
        amount -= consumed;
        consumed = Math.min(reserve, amount);
        reserve -= consumed;
        amount -= consumed;

        if (amount > 0) {
            health -= amount;
        }
    }

    protected boolean hasEnergy(int amount) {
        return energy + reserve >= amount;
    }

    protected void metabolism() {
        useEnergy(getQuota());
    }

    protected void curation() {
        double percent = (double) energy / genes.get(Gen.MAX_ENERGY);
        if (percent > 0.7 ) {
            int factor = Math.min(genes.get(Gen.CURATION_FACTOR),
                    genes.get(Gen.MAX_HEALTH) - health);
            if (!hasEnergy(factor)) return;
            useEnergy(factor); health += factor;
        }
    }

    protected void grow() {
        growth += genes.get(Gen.GROW_FACTOR);
    }

    public abstract void reproduction();

    public abstract void turn();

    /// Getters
    public Map<Gen, Integer> getGenes() {
        return genes;
    }

    public int getEnergy() {
        return energy;
    }

    public int getReserve() {
        return reserve;
    }

    public int getHealth() {
        return health;
    }

    public int getGrowth() {
        return growth;
    }
}
