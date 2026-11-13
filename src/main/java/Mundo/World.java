package Mundo;

import Algoritmos.Randomizer;
import Organismos.Lifeform;
import Organismos.Plant;

import java.util.*;

public class World {
    /// Estatico
    static private World world = null;
    static private final int hours = 23;
    static private final byte precipitation = 15;

    /// Atributos
    private int lightLv;
    private int currentHour;

    private final Tile[][] map;
    private final Map<Lifeform,Tile> lifeforms;

    /// Constructor
    private World(int rows, int cols) {
        this.lightLv = 120;
        this.currentHour = 12;
        this.map = new Tile[rows][cols];
        this.lifeforms = new HashMap<>();
        generateWorld();
    }

    /// Turno
    public void turn() {
        boolean endDay = (currentHour >= hours);
        dayCycle(); environmentalEffects();

        List<Lifeform> auxLifeforms = new ArrayList<>(lifeforms.keySet());
        for (Lifeform lifeform : auxLifeforms) {
            lifeform.turn();
            if (endDay) lifeform.growOld();
            if (lifeform.isDead()) killLifeform(lifeform);
        }

        currentHour++; if (endDay) currentHour = 0;
    }

    /// Métodos de generación
    public void generateWorld() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Tile(Terrain.EARTH,i,j);
            }
        }
    }

    /// Métodos de población
    public void insertLifeform(Lifeform lifeform) {
        int row = (int) (Math.random() * map.length);
        int col = (int) (Math.random() * map[0].length);
        insertLifeform(lifeform,row,col);
    }

    public void insertLifeform(Lifeform lifeform, int row, int col) {
        if (!exists(row,col)) return;

        Tile tile = getTile(row,col);
        if (tile.getSurface() != null) return;
        tile.setSurface(lifeform);
        lifeforms.put(lifeform, tile);

        if (lifeform instanceof Plant) ((Plant) lifeform).takeFirstRoot();
    }

    public void killLifeform(Lifeform lifeform) {
        Tile tile = lifeforms.get(lifeform);
        tile.setSurface(null);
        lifeforms.remove(lifeform);
    }

    public void updateLocation(Lifeform lifeform, Tile tile) {
        if (lifeform == null || tile == null) return;
        lifeforms.put(lifeform, tile);
    }

    /// Métodos ambientales
    private void environmentalEffects() {
        evaporation();

        if (Randomizer.probability(precipitation /100.0)) {
            rain();
        }
    }

    private void rain() {
        for (Tile[] tiles: map) {
            for (Tile tile: tiles) {
                double aux = tile.getHumidityLv();
                tile.setHumidityLv(aux + 10);
            }
        }
    }

    private void evaporation() {
        for (Tile[] tiles: map) {
            for (Tile tile: tiles) {
                double aux = tile.getHumidityLv();
                tile.setHumidityLv(aux - lightLv/40.0);
            }
        }
    }

    /// Métodos del tiempo
    public void dayCycle() {
        if (currentHour < 6 || currentHour >= 18) {lightLv = 0;}
        else if (currentHour < 9) {lightLv = (currentHour - 5) * 30;}
        else if (currentHour < 11) {lightLv = 100;}
        else if (currentHour < 13) {lightLv = 120;}
        else if (currentHour < 15) {lightLv = 100;}
        else {lightLv = (18 - currentHour) * 40;}
    }

    /// Getters
    public Tile[] getNeighborTiles(int row, int col, int radius) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int auxRow = row + i;
                int auxCol = col + j;
                Tile aux = getTile(auxRow,auxCol);
                if (aux != null) tiles.add(aux);
            }
        } return tiles.toArray(new Tile[0]);


    }

    public Tile getTile(int row, int col) {
        if (exists(row,col)) return map[row][col];
        return null;
    }

    public Tile getTile(Lifeform lifeform) {
        return lifeforms
                .get(lifeform);
    }

    public int getLightLv() {
        return
                lightLv;
    }

    public int getPopulation() {
        return lifeforms
                .size();
    }

    public Set<Lifeform> getLifeforms() {
        return lifeforms
                .keySet();
    }

    public double getHumidityLv() {
        int size = map.length * map[0].length;
        double total = 0;

        for (Tile[] tiles: map) {
            for (Tile tile: tiles) {
                total += tile.getHumidityLv();
            }
        } return total / size;
    }

    /// Otros
    public boolean hasLight() {
        return lightLv > 0;
    }

    public boolean exists(int row, int col) {
        return row >= 0 && row < map.length
                && col >= 0 && col < map[0].length;
    }

    public void printWorld() {
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                System.out.print(tile.contentView() + " ");
            } System.out.println(" ");
        }
    }

    /// Singleton
    public static World getWorld() {
        return getWorld(100,80);
    }

    public static World getWorld(int rows, int cols) {
        if (world == null) world = new World(rows,cols);
        return world;
    }
}
