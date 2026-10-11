package Estructuras.Plantas;

import Algoritmos.Randomizer;
import Estructuras_.GraphNode;
import Estructuras_.GraphSystem;
import Mundo.Tile;
import Mundo.World;

import java.util.*;

public class RootSystem extends GraphSystem {
    /// Constructor
    public RootSystem() {}

    /// Funciones fisiologicas
    public double collectWater(int waterResistance, int waterQuota) {
        double total = 0;
        for (GraphNode node: system.keySet()) {
            if (!(node instanceof Root)) continue;
            total += ((Root) node).collectWater
                    (system.get(node),waterResistance,waterQuota);
        } return total;
    }

    public int takeRoot(Root parent, Tile tile, double energy) {
        int used = (int) Math.floor(Math.min(10,energy));
        Root root = new Root(used*10);
        if (parent == null) addCentralNode(root,tile);
        else {addNode(root, tile);
            linkNodes(parent,root);}
        return used;
    }

    public void destroyRoot(Root root) {
        system.get(root).setUnderground(null);
        removeNode(root);
    }

    public void destroySystem() {
        List<GraphNode> nodes = new ArrayList<>(system.keySet());
        for (GraphNode node: nodes) {
            Root root = (Root) node;
            destroyRoot(root);
        }
    }

    public void updateSystem() {
        List<GraphNode> nodes = new ArrayList<>(system.keySet());
        for (GraphNode node: nodes) {
            if (!(node instanceof Root)) continue;
            if (node.getIntegrity() <= 0)
                destroyRoot((Root) node);
        }
    }

    /// Funciones de expansión
    public int growRoots(double energy) {
        int used = 0;
        for (GraphNode node: system.keySet()) {
            if (!(node instanceof Root)) continue;
            if (energy < 1) return used;
            if (node.getIntegrity() >= 100) continue;
            ((Root) node).grow(); used++; energy--;
        } return used;
    }

    public int takeRoots(double energy, int waterResistance) {
        int used = 0;
        Map<Root,Tile> betters = bestNeighbors(waterResistance);
        if (betters.isEmpty()) return used;
        List<Root> keys = new ArrayList<>(betters.keySet());

        while (true) {
            if (energy < 1) return used;
            if (keys.isEmpty()) return used;
            int i = (int) (Math.random() * keys.size());
            Root parent = keys.get(i);
            Tile select = betters.get(parent);
            takeRoot(parent,select,1);
            used++; energy--; keys.removeIf
                    (r -> betters.get(r) == select);
        }
    }

    private Map<Root,Tile> bestNeighbors(int waterResistance) {
        Map<Root,Tile> betters = new HashMap<>();

        for (GraphNode node: system.keySet()) {
            if (!(node instanceof Root)) continue;
            Tile candidate = bestNeighborRoot((Root) node,waterResistance);
            if (candidate != null) betters.put((Root) node,candidate);
        } return betters;
    }

    private Tile bestNeighborRoot(Root root, int waterResistance) {
        Tile aux = system.get(root);
        Tile[] tiles = World.getWorld().neighborTiles
                (aux.getColumn(),aux.getRow(),1);

        aux = null;
        for (Tile tile : tiles) {
            if (tile.getUnderground() != null) continue;
            if (tile.getHumidity() > waterResistance) continue;
            if (aux == null) aux = tile;
            if (tile.getHumidity() > aux.getHumidity()) aux = tile;
        } return aux;
    }

    /// Fragmentación
    public RootSystem fragmentBranch() {
        Root root = fragmentCandidate();
        if (root == null) return null;

        RootSystem newBranch = new RootSystem();
        Tile location = system.get(root);
        if (location == null) System.out.println("ERROR ADD CENTRAL"); //
        newBranch.addCentralNode(root,location);
        removeNode(root);
        return newBranch;
    }

    private Root fragmentCandidate() {
        if (system.size() <= 3) return null;
        Set<Root> roots = new HashSet<>();
        for (GraphNode node: system.keySet()) {
            if (!(node instanceof Root)) continue;
            Root aux = (Root) node;
            if (aux.getConnections().size() != 1) continue;
            if (aux == root) continue;
            roots.add(aux);
        } return Randomizer.choose(roots);
    }
}
