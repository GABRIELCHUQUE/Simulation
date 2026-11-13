package Estrategias.Reproduccion_P;

import Algoritmos.Randomizer;
import Estructuras.Generales.GraphNode;
import Estructuras.Plantas.Root;
import Estructuras.Plantas.RootSystem;
import Genes.Gen;
import Mundo.Tile;
import Mundo.World;
import Organismos.Plant;

import java.util.HashSet;
import java.util.Set;

public class Fragmentation implements ReproducePlStrategy {
    @Override
    public void reproduction(Plant plant) {
        RootSystem rootSystem = fragmentBranch(plant.getRootSystem());
        if (rootSystem == null) return;

        int energy = (int) plant.useReserve(2 *
                plant.getGenes().get(Gen.MAX_ENERGY));

        Plant descendant = new Plant(plant.replicateGenes()
                ,0,rootSystem);
        descendant.addEnergy(energy);

        Tile tile = rootSystem.getLocation();
        World.getWorld().insertLifeform(descendant,
                tile.getRow(),tile.getCol());
    }

    /// Métodos de fragmentación
    public RootSystem fragmentBranch(RootSystem system) {
        Root root = fragmentCandidate(system);
        if (root == null) return null;

        RootSystem newBranch = new RootSystem();
        Tile location = system.getLocation(root);
        newBranch.addCentralNode(root,location);
        system.removeNode(root);
        return newBranch;
    }

    private Root fragmentCandidate(RootSystem system) {
        if (system.size() <= 3) return null;
        Set<Root> roots = new HashSet<>();
        for (GraphNode node: system.keySet()) {
            if (!(node instanceof Root)) continue;
            Root aux = (Root) node;
            if (aux.getConnections().size() != 1) continue;
            if (aux == system.getRoot()) continue;
            roots.add(aux);
        } return Randomizer.choose(roots);
    }
}
