package Mundo;

import Organismos.Lifeform;

public class World {
    /// Atributos
    static public final int hours = 24;

    private int light;
    private int currentHour;
    private final Square[][] squares;

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

    /// Funciones ambientales
    public void dayCycle() {
        if (currentHour < 6 || currentHour >= 18) {
            light = 0;
        }
        else if (currentHour < 9) {
            light = (currentHour - 5) * 30;
        }
        else if (currentHour < 11) {
            light = 100;
        }
        else if (currentHour < 13) {
            light = 120;
        }
        else if (currentHour < 15) {
            light = 100;
        }
        else {
            light = (18 - currentHour) * 40;
        }
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
        dayCycle(); evaporation();

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                Square current = squares[row][col];
                Object content = current.getContent();
                if (content instanceof Lifeform) {
                    Lifeform lifeform = (Lifeform) content;
                    if (lifeform.isDead()) continue;
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
        if (exists(col,row)) return squares[row][col];
        return null;
    }
}
