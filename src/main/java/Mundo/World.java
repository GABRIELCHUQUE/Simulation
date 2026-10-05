package Mundo;

import Organismos.Lifeform;
import Organismos.Plantas.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    /// Mundo
    static private World world = null;
    static private final int hours = 24;

    private int light;
    private int currentHour;
    private final Tile[][] tiles;
    private final Map<Lifeform, Tile> lifeforms;

    /// Constructor
    private World(int cols, int rows) {
        this.light = 120;
        this.currentHour = 12;
        this.tiles = new Tile[rows][cols];
        this.lifeforms = new HashMap<>();
        initialize();
    }

    private void initialize() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                tiles[row][col] = new Tile(row,col,20,
                        Terrain.EARTH,null,null);
            }
        }
    }

    /// Funciones ambientales
    public void dayCycle() {
        if (currentHour < 6 || currentHour >= 18) {light = 0;}
        else if (currentHour < 9) {light = (currentHour - 5) * 30;}
        else if (currentHour < 11) {light = 100;}
        else if (currentHour < 13) {light = 120;}
        else if (currentHour < 15) {light = 100;}
        else {light = (18 - currentHour) * 40;}
    }

    public void rain() {
        for (Tile[] tile : tiles) {
            for (Tile current : tile) {
                current.setHumidity(current.getHumidity() + 10.0);
            }
        }
    }

    public void evaporation() {
        for (Tile[] tile : tiles) {
            for (Tile current : tile) {
                current.setHumidity(current.getHumidity() - light/40.0);
            }
        }
    }

    /// Funciones técnicas
    public void insertLifeform(Lifeform lifeform) {
        int row = (int) (Math.random() * tiles.length);
        int col = (int) (Math.random() * tiles[0].length);
        insertLifeform(lifeform,row,col);
    }

    public void insertLifeform(Lifeform lifeform, int row, int col) {
        if (row < 0 || col < 0) return;
        if (row >= tiles.length || col >= tiles[0].length) return;

        Tile tile = getTile(col,row);
        if (tile.getContent() != null) return;
        tile.setContent(lifeform);
        lifeforms.put(lifeform, tile);

        if (lifeform instanceof Plant) ((Plant) lifeform).takeFirstRoot();
    }

    public void printWorld() {
        for (Tile[] tile : tiles) {
            for (Tile current : tile) {
                System.out.print(current.contentView() + " ");
            } System.out.println(" ");
        }
    }

    public boolean exists(int col, int row) {
        return row >= 0 && row < tiles.length
                && col >= 0 && col < tiles[0].length;
    }

    /// Funciones de los turnos
    public void turn() {
        boolean endDay = (currentHour >= hours);
        dayCycle(); evaporation();

        List<Lifeform> auxLifeforms = new ArrayList<>(lifeforms.keySet());
        for (Lifeform lifeform : auxLifeforms) {
            lifeform.turn();
            if (endDay) lifeform.growOld();
        }

        currentHour++; if (endDay) currentHour = 0;
    }

    public Tile[] neighborTiles(int col, int row, int radius) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = -1*radius; i <= radius ; i++) {
            for (int j = -1*radius; j <= radius ; j++) {
                int auxCol = col+i; int auxRow = row+j;
                Tile neighbor = getTile(auxCol,auxRow);
                if (neighbor == null) continue;
                tiles.add(neighbor);
            }
        } return tiles.toArray(new Tile[0]);
    }

    /// Getters
    public double averageHumidity() {
        double total = 0; int size = 0;
        for (Tile[] tile : tiles) {
            for (Tile current : tile) {
                total += current.getHumidity(); size++;
            }
        } return total/size;
    }

    public int getLight() {
        return light;
    }

    public Tile getTile(Lifeform lifeform) {
        if (!lifeforms.containsKey(lifeform)) return null;
        return lifeforms.get(lifeform);
    }

    public Tile getTile(int col, int row) {
        if (exists(col,row)) return tiles[row][col];
        return null;
    }

    public static World getWorld() {
        return getWorld(100,70);
    }

    public static World getWorld(int cols, int rows) {
        if (world == null) {world = new World(cols,rows);}
        return world;
    }
}
