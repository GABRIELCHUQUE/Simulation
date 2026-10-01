package Mundo;

public class Tile {
    /// Atributos
    private final int row;
    private final int column;

    private double humidity;
    private Terrain typeOfTerrain;

    private Object content;
    private Object underground;

    /// Constructor
    public Tile(int row, int column, double humidity, Terrain typeOfTerrain, Object content, Object underground) {
        this.row = row;
        this.column = column;
        this.humidity = humidity;
        this.typeOfTerrain = typeOfTerrain;
        this.content = content;
        this.underground = underground;
    }

    /// Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getHumidity() {
        return humidity;
    }

    public Object getContent() {
        return content;
    }

    public Object getUnderground() {
        return underground;
    }

    public Terrain getTypeOfTerrain() {
        return typeOfTerrain;
    }

    /// Setters
    public void setHumidity(double humidity) {
        this.humidity = Math.max(0,humidity);
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setUnderground(Object underground) {
        this.underground = underground;
    }

    public void setTypeOfTerrain(Terrain typeOfTerrain) {
        this.typeOfTerrain = typeOfTerrain;
    }

    /// toString
    public String contentView() {
        boolean isNull = content == null;
        return String.format("[%s]",
                isNull ? " " : content.toString());
    }
}
