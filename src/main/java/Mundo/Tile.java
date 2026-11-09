package Mundo;

public class Tile {
    /// Atributos
    private final int row;
    private final int col;

    private Terrain typeofTerrain;
    private double humidityLv;
    private Object underground;
    private Object surface;

    /// Constructor
    public Tile(Terrain typeofTerrain, int row, int col) {
        this.row = row;
        this.col = col;
        this.typeofTerrain = typeofTerrain;
        this.humidityLv = typeofTerrain.getHumidityLv();
        this.underground = null;
        this.surface = null;
    }

    /// Métodos
    public String contentView() {
        boolean isNull = surface == null;
        return String.format("[%s]",
                isNull ? " " : surface.toString());
    }

    /// Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Terrain getTypeofTerrain() {
        return typeofTerrain;
    }

    public double getHumidityLv() {
        return humidityLv;
    }

    public Object getUnderground() {
        return underground;
    }

    public Object getSurface() {
        return surface;
    }

    /// Setters
    public void setTypeofTerrain(Terrain typeofTerrain) {
        this.typeofTerrain = typeofTerrain;
    }

    public void setHumidityLv(double humidityLv) {
        this.humidityLv = Math.max(0,humidityLv);
    }

    public void setUnderground(Object underground) {
        this.underground = underground;
    }

    public void setSurface(Object surface) {
        this.surface = surface;
    }
}
