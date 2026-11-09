package Organismos;

import Algoritmos.Randomizer;
import Genes.Gen;

import java.util.HashMap;
import java.util.Map;

public abstract class Lifeform {
    /// Atributos
    protected Map<Gen,Integer> genes;

    protected double health;
    protected double energy;
    protected double reserve;

    private int growthProgress;
    private int age;

    /// Constructor
    public Lifeform(Map<Gen, Integer> genes) {
        this.genes = genes;
        this.energy = genes.get(Gen.MAX_ENERGY);
        this.reserve = 0;
        this.health = genes.get(Gen.MAX_HEALTH);
        this.growthProgress = 0;
        this.age = 0;
    }

    public Lifeform(Map<Gen, Integer> genes, double energy) {
        this(genes);
        this.energy = energy;
    }

    /// Turno
    public abstract void turn();

    /// Métodos energéticos
    protected boolean hasEnergy(double amount) {
        return energy + reserve >= amount;
    }

    public double useEnergy(double amount) {
        return useEnergy(amount,true);
    }

    public double useReserve(double amount) {
        return useReserve(amount,true);
    }

    private double useEnergy(double amount, boolean callback) {
        double used = amount;

        double consumed = Math.min(energy,amount);
        energy -= consumed; amount -= consumed;
        if (amount == 0) return used;

        if (callback) amount -= useReserve(amount,false);
        if (amount > 0) health -= amount;
        return used;
    }

    private double useReserve(double amount, boolean callback) {
        double used = amount;

        double consumed = Math.min(reserve,amount);
        reserve -= consumed; amount -= consumed;
        if (amount == 0) return used;

        if (callback) amount -= useEnergy(amount,false);
        if (amount > 0) health -= amount;
        return used;
    }

    public void addEnergy(double amount) {
        double added = Math.min(genes.get(Gen.MAX_ENERGY) - energy,amount);
        energy += added; amount -= added; reserve += amount;
    }

    /// Métodos metabólicos
    protected abstract void metabolism();

    public double getQuota() {
        return genes
                .get(Gen.QUOTA_ENERGY);
    }

    protected void curation() {
        double percent = energy / genes.get(Gen.MAX_ENERGY);
        if (percent <= 0.7) return;

        double factor = Math.min(genes.get(Gen.CURATION_FACTOR),
                genes.get(Gen.MAX_HEALTH) - health);
        if (!hasEnergy(factor)) return;

        useReserve(factor);
        health += factor;
    }

    protected void grow() {
        int growFactor = genes.get(Gen.GROW_FACTOR);
        if (!hasEnergy(growFactor)) return;
        useReserve(growFactor);
        growthProgress += growFactor;
    }

    public void growOld() {
        age++;
    }

    /// Métodos reproductivos
    protected Map<Gen,Integer> replicateGenes() {
        Map<Gen,Integer> copy = new HashMap<>();
        for (Gen gen: genes.keySet()) {
            int value = genes.get(gen);
            if (Randomizer.probability(0.15))
                value += Randomizer.randomRange(-5,5);
            copy.put(gen,gen.clamp(value));
        } return copy;
    }

    protected abstract void reproduction();

    /// Otros
    public boolean isDead() {
        return health <= 0;
    }

    public boolean isGrowing() {
        return growthProgress < genes.get(Gen.MATURITY);
    }

    protected double getAgeFactor() {
        int oldAge = genes.get(Gen.OLD_AGE_START);
        if (age < oldAge) return 1.0;
        double yearsOld = age - oldAge;
        return Math.exp(-0.02 * yearsOld);
    }

    public String getStatus() {
        return String.format(
                "Health: %.1f/%d | Energy: %.0f/%d | Reserve: %.0f | Growth: %d |Edad: %d | Quota: %.2f",
                health, genes.get(Gen.MAX_HEALTH),
                energy, genes.get(Gen.MAX_ENERGY),
                reserve, growthProgress, age, getQuota()
        );
    }
}
