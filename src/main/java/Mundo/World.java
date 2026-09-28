package Mundo;

import Organismos.Lifeform;

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
    private final Square[][] squares;
    private final Map<Lifeform,Square> lifeforms;

    /// Constructor
    private World(int cols, int rows) {
        this.light = 120;
        this.currentHour = 12;
        this.squares = new Square[rows][cols];
        this.lifeforms = new HashMap<>();
        initialize();
    }

    private void initialize() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squares[row][col] = new Square(col,row,20,null,
                        Terrain.EARTH);
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
        for (Square[] square : squares) {
            for (Square current : square) {
                current.setHumidity(current.getHumidity() + 10);
            }
        }
    }

    public void evaporation() {
        for (Square[] square : squares) {
            for (Square current : square) {
                current.setHumidity(current.getHumidity() - light/40);
            }
        }
    }

    /// Funciones técnicas
    public void insertLifeform(Lifeform lifeform) {
        int row = (int) (Math.random() * squares.length);
        int col = (int) (Math.random() * squares[0].length);
        insertLifeform(lifeform,row,col);
    }

    public void insertLifeform(Lifeform lifeform, int row, int col) {
        if (row < 0 || col < 0) return;
        if (row >= squares.length || col >= squares[0].length) return;

        Square square = getSquare(col,row);
        if (square.getContent() != null) return;
        square.setContent(lifeform);
        lifeforms.put(lifeform,square);
    }

    public void printWorld() {
        for (Square[] square : squares) {
            for (Square current : square) {
                System.out.print(current.contentView() + " ");
            } System.out.println(" ");
        }
    }

    public boolean exists(int col, int row) {
        return row >= 0 && row < squares.length
                && col >= 0 && col < squares[0].length;
    }

    /// Turno
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

    /// Getters
    public int getLight() {
        return light;
    }

    public Square getSquare(Lifeform lifeform) {
        if (!lifeforms.containsKey(lifeform)) return null;
        return lifeforms.get(lifeform);
    }

    public Square getSquare(int col, int row) {
        if (exists(col,row)) return squares[row][col];
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
