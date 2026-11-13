package Taxonomía;

import Algoritmos.NameGenerator;
import Genes.Gen;

import java.util.*;

public class Species {
    /// Estatico
    private static final List<Species> species = new ArrayList<>();

    /// Atributos
    private final Map<Gen,Integer> genes;
    private final Species parent;
    private String name;

    /// Constructor
    public Species(Map<Gen, Integer> genes, Species parent) {
        this.genes = new HashMap<>(genes);
        this.parent = parent;
        this.name = NameGenerator.generateName();

        species.add(this);
    }

    public Species(Map<Gen, Integer> genes) {
        this(genes,null);
    }

    /// Getters
    public List<Species> getSpecies() {
        return Collections.unmodifiableList(species);
    }

    public Map<Gen, Integer> getGenes() {
        return Collections.unmodifiableMap(genes);
    }

    public Species getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    /// Setters
    public void setName(String name) {
        this.name = name;
    }
}
