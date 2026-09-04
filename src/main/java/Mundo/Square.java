package Mundo;

public class Square {
    private int humidity;
    private Object content;
    private Terrain typeOfTerrain;

    /// Constructor
    public Square(int humidity, Object content, Terrain typeOfTerrain) {
        this.humidity = humidity;
        this.content = content;
        this.typeOfTerrain = typeOfTerrain;
    }

    /// Getters
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
        this.humidity = humidity;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setTypeOfTerrain(Terrain typeOfTerrain) {
        this.typeOfTerrain = typeOfTerrain;
    }
}
