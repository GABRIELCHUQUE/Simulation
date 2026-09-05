package Mundo;

import Organismos.Lifeform;

public class World {
    /// Atributos
    static public final int hours = 24;

    private int light;
    private int currentHour;
    private Square[][] squares;

    /// Constructor
    public World(int cols, int rows) {
        this.light = 120;
        this.currentHour = 12;
        this.squares = new Square[rows][cols];
        initialize();
    }

    private void initialize() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squares[row][col] = new Square(20,null,
                        Terrain.EARTH);
            }
        }
    }

    /// Turno
    public void turn() {
        if (currentHour >= 6 && currentHour < 12)
            light = (currentHour - 5) * 20;
        else if (currentHour >= 12 && currentHour < 18)
            light = (18 - currentHour) * 20;
        else light = 0;

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                Square current = squares[row][col];
                Object content = current.getContent();
                if (content instanceof Lifeform) {
                    ((Lifeform) content).turn(this,col,row);
                }
            }
        }

        currentHour++; if (currentHour >= hours) currentHour = 0;
    }

    /// Getters
    public int getLight() {
        return light;
    }

    public Square getSquare(int col, int row) {
        return squares[row][col];
    }
}
