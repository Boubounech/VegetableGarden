package model;

/**
 *  - waterLevel : int
 *  - lightLevel : int
 *  - temperature : int
 *  - waterSourceNumber : int
 *  - lightSourceNumber : int
 *  - temperatureSourceNumber : int
 *
 *  - waterSource : WaterSource
 *  - lightSource : LightSource
 *  - temperatureSource : TemperatureSource
 *
 *  + Plot()
 *
 *  + setWaterLevel(int) : void
 *  + setLightLevel(int) : void
 *  + setTemperatureLevel(int) : void
 *  + setWaterSource(WaterSource) : void
 *  + setLightSource(LightSource) : void
 *  + setTemperatureSource(TemperatureSource) : void
 *
 *  + getWaterLevel(void) : int
 *  + getLightLevel(void) : int
 *  + getTemperatureLevel(void) : int
 *  + getWaterSource(void) : WaterSource
 *  + getLightSource(void) : LightSource
 *  + getTemperatureSource(void) : TemperatureSource
 */
public abstract class Plot {
    private int waterLevel;
    private int lightLevel;
    private int temperature;
    private int waterSourceNumber;
    private int lightSourceNumber;
    private int temperatureSourceNumber;

    private WaterSource waterSource;
    private LightSource lightSource;
    private TemperatureSource temperatureSource;

    public Plot() {
        waterLevel = 0;
        lightLevel = 0;
        temperature = 0;
        waterSourceNumber = 0;
        lightSourceNumber = 0;
        temperatureSourceNumber = 0;
    }


    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setTemperatureLevel(int temperature) {
        this.temperature = temperature;
    }

    public void setWaterSource(WaterSource waterSource) {
        this.waterSource = waterSource;
    }

    public void setLightSource(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    public void setTemperatureSource(TemperatureSource temperatureSource) {
        this.temperatureSource = temperatureSource;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public int getTemperatureLevel() {
        return temperature;
    }

    public WaterSource getWaterSource() {
        return waterSource;
    }

    public LightSource getLightSource() {
        return lightSource;
    }

    public TemperatureSource getTemperatureSource() {
        return temperatureSource;
    }
}
