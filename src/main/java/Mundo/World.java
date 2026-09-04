package Mundo;

public class World {
    /// Atributos
    static public final int hours = 12;

    private boolean day;
    private int currentHour;
    private Square[][] squares;

    /// Constructor
    public World(int cols, int rows) {
        this.day = true;
        this.currentHour = 0;
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
        currentHour++; if (currentHour >= hours) day = !day;

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                Square current = squares[row][col];
                Object content = current.getContent();

            }
        }
    }
}
