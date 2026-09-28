package Mundo;

public class Square {
    private final int row;
    private final int column;
    private int humidity;
    private Object content;
    private Terrain typeOfTerrain;

    /// Constructor
    public Square(int row, int column, int humidity, Object content, Terrain typeOfTerrain) {
        this.row = row;
        this.column = column;
        this.humidity = humidity;
        this.content = content;
        this.typeOfTerrain = typeOfTerrain;
    }

    /// Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getHumidity() {
        return humidity;
    }

    public Object getContent() {
        return content;
    }

    public Terrain getTypeOfTerrain() {
        return typeOfTerrain;
    }

    /// Setters
    public void setHumidity(int humidity) {
        this.humidity = Math.max(0,humidity);
    }

    public void setContent(Object content) {
        this.content = content;
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
