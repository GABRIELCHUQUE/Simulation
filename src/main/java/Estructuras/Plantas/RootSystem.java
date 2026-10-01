package Estructuras.Plantas;

import Mundo.Tile;
import Mundo.World;

import java.util.*;

public class RootSystem {
    /// Atributos
    private final Map<Tile,Root> system;

    /// Constructor
    public RootSystem() {
        this.system = new HashMap<>();
    }

    /// Métodos fisiologicos
    public void lostRoots() {
        for (Tile tile: new HashSet<>(system.keySet())) {
            Root root = system.get(tile);
            if (root.getIntegrity() <= 0) retire(tile);
        }
    }

    public double collectWater(int waterResistance, int waterQuota) {
        double total = 0;
        for (Tile tile: system.keySet()) {
            total += system.get(tile).collectWater
                    (tile,waterResistance,waterQuota);
        } return total;
    }

    public int averageIntegrity() {
        if (system.isEmpty()) return 0;
        int total = 0;
        for (Root root: system.values()) {
            total+= root.getIntegrity();
        } return total / system.size();
    }

    public int takeFirstRoot(Tile tile) {
        expand(tile, new Root(100));
        return 10;
    }

    public int growRoots(double energy) {
        int used = 0;
        for (Root root: system.values()) {
            if (energy < 1) return used;
            if (root.getIntegrity() >= 100) continue;
            root.grow(); used++; energy--;
        } return used;
    }

    public int takeRoots(double energy, int waterResistance) {
        int used = 0;
        List<Tile> betters = bestNeighbors(waterResistance);
        if (betters.isEmpty()) return used;

        while (true) {
            if (energy < 1) return used;
            if (betters.isEmpty()) return used;
            int i = (int) (Math.random() * betters.size());
            Tile select = betters.get(i);
            expand(select,new Root(10));
            used++; energy--;
            betters.removeIf(t -> t == select);
        }
    }

    public List<Tile> bestNeighbors(int waterResistance) {
        List<Tile> tiles = new ArrayList<>();

        for (Tile tile: system.keySet()) {
            Tile[] neighbors = neighborsTiles(tile);
            Tile candidate = bestNeighborRoot(neighbors,waterResistance);
            if (candidate != null) tiles.add(candidate);
        } return tiles;
    }

    private Tile bestNeighborRoot(Tile[] tiles, int waterResistance) {
        Tile best = null;

        for (Tile tile : tiles) {
            if (tile.getHumidity() > waterResistance) continue;
            if (best == null || tile.getHumidity() > best.getHumidity())
                best = tile;
        } return best;
    }

    private Tile[] neighborsTiles(Tile tile) {
        World world = World.getWorld();
        int row = tile.getRow();
        int col = tile.getColumn();

        List<Tile> tiles = new ArrayList<>();
        for (int i = -1; i <= 1 ; i++) {
            for (int j = -1; j <= 1 ; j++) {
                int auxCol = col+i; int auxRow = row+j;
                Tile neighbor = world.getTile(auxCol,auxRow);
                if (neighbor == null) continue;
                if (system.containsKey(neighbor)) continue;
                if (neighbor.getUnderground() != null) continue;
                tiles.add(neighbor);
            }
        } return tiles.toArray(new Tile[0]);
    }

    private void expand(Tile tile, Root root) {
        system.put(tile,root);
        tile.setUnderground(root);
    }

    private void retire(Tile tile) {
        system.remove(tile);
        tile.setUnderground(null);
    }

    /// Métodos comunes
    public int size() {
        return system.size();
    }

    public String infrastructure() {
        return String.format("Tamaño: %d | Integridad: %d",
                system.size(),averageIntegrity());
    }
}
