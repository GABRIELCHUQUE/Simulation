package Organismos;

import Mundo.Tile;
import Mundo.World;
import Objetos.Gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Lifeform {
    /// Atributos
    protected Map<Gen, Integer> genes;
    protected double energy;
    protected double reserve;
    protected double health;
    protected int growth;
    protected int radius;
    protected int age;

    /// Constructor
    public Lifeform(Map<Gen, Integer> genes) {
        this.genes = genes;
        this.energy = genes.get(Gen.MAX_ENERGY);
        this.reserve = 0;
        this.health = genes.get(Gen.MAX_HEALTH);
        this.growth = 0;
        this.radius = 1;
        this.age = 0;
    }

    public Lifeform(Map<Gen, Integer> genes, int energy) {
        this (genes);
        this.energy = energy;
    }

    /// Funciones de energía
    protected boolean hasEnergy(double amount) {
        return energy + reserve >= amount;
    }

    protected void consumeEnergy(double amount) {
        if (hasEnergy(amount)) {
            amount = useEnergy(amount);
            if (amount > 0) useReserves(amount);
        } else {
            amount = useEnergy(amount);
            amount = useReserves(amount);
            health -= amount;
        }
    }

    protected void consumeReserves(double amount) {
        if (hasEnergy(amount)) {
            amount = useReserves(amount);
            if (amount > 0) useEnergy(amount);
        } else {
            amount = useReserves(amount);
            amount = useEnergy(amount);
            health -= amount;
        }
    }

    protected double useEnergy(double amount) {
        double consumed = Math.min(energy, amount);
        energy -= consumed;
        amount -= consumed;
        return amount;
    }

    protected double useReserves (double amount) {
        double consumed = Math.min(reserve,amount);
        reserve -= consumed;
        amount -= consumed;
        return amount;
    }

    protected void addEnergy(double amount) {
        double added = Math.min(genes.get(Gen.MAX_ENERGY) - energy,amount);
        energy += added;
        amount -= added;
        reserve += amount;
    }

    /// Funciones metabólicas
    protected void metabolism(boolean currentEnergy) {
        if (currentEnergy) consumeEnergy(getQuota());
        else consumeReserves(getQuota());
    }

    protected void curation() {
        double percent = energy / (double) genes.get(Gen.MAX_ENERGY);
        if (percent > 0.7) {
            double factor = Math.min(genes.get(Gen.CURATION_FACTOR),
                    genes.get(Gen.MAX_HEALTH) - health);

            if (!hasEnergy(factor)) return;
            consumeReserves(factor);
            health += factor;
        }
    }

    protected void grow() {
        int growFactor = genes.get(Gen.GROW_FACTOR);
        if (!hasEnergy(growFactor)) return;
        consumeReserves(growFactor);
        growth += growFactor;
    }

    protected double getQuota() {
        return genes.
                get(Gen.QUOTA_ENERGY);
    }

    /// Funciones de acción
    public abstract void turn();

    public abstract void reproduction();

    /// Otros
    public boolean isDead() {
        return health <= 0;
    }

    public boolean isGrowing() {
        return growth < genes.
                get(Gen.MATURITY);
    }

    public void growOld() {
        age++;
    }

    protected double getAgeFactor() {
        int oldAge = genes.get(Gen.OLD_AGE_START);
        if (age < oldAge) return 1.0;
        double yearsOld = age - oldAge;
        return Math.exp(-0.02 * yearsOld);
    }

    /// Getters
    public String getStatus() {
        return String.format(
                "Health: %.1f/%d | Energy: %.0f/%d | Reserve: %.0f | Growth: %d |Edad: %d | Quota: %.2f",
                health, genes.get(Gen.MAX_HEALTH),
                energy, genes.get(Gen.MAX_ENERGY),
                reserve, growth, age, getQuota()
        );
    }
}
