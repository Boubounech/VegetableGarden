package garden.model;

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
    private int temperatureLevel;
    private int waterSourceNumber;
    private int lightSourceNumber;
    private int temperatureSourceNumber;

    private int x;
    private int y;

    private WaterSource waterSource;
    private LightSource lightSource;
    private TemperatureSource temperatureSource;

    public Plot(int x, int y) {
        this.x = x;
        this.y = y;
        waterLevel = 0;
        lightLevel = 0;
        temperatureLevel = 0;
        waterSourceNumber = 0;
        lightSourceNumber = 0;
        temperatureSourceNumber = 0;
    }

    public abstract void update();

    public abstract String getItem();


    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setTemperatureLevel(int temperatureLevel) {
        this.temperatureLevel = temperatureLevel;
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
        return temperatureLevel;
    }

    public int getWaterSourceNumber() { return this.waterSourceNumber; }

    public void setWaterSourceNumber(int waterSourceNumber) { this.waterSourceNumber = waterSourceNumber; }

    public int getLightSourceNumber() { return this.lightSourceNumber; }

    public void setLightSourceNumber(int lightSourceNumber) { this.lightSourceNumber = lightSourceNumber; }

    public int getTemperatureSourceNumber() { return this.temperatureSourceNumber; }

    public void setTemperatureSourceNumber(int temperatureSourceNumber) { this.temperatureSourceNumber = temperatureSourceNumber; }

    public boolean hasWaterSource() {return this.waterSource != null;}
    public boolean hasLightSource() {return this.lightSource != null;}
    public boolean hasTemperatureSource() {return this.temperatureSource != null;}

    public WaterSource getWaterSource() {
        return waterSource;
    }

    public LightSource getLightSource() {
        return lightSource;
    }

    public TemperatureSource getTemperatureSource() {
        return temperatureSource;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}

    public void initSources(int waterLevel, int temperatureLevel, int lightLevel, int waterSourceNumber, int temperatureSourceNumber, int lightSourceNumber){
        this.waterLevel = waterLevel;
        this.temperatureLevel = temperatureLevel;
        this.lightLevel = lightLevel;
        this.waterSourceNumber = waterSourceNumber;
        this.temperatureSourceNumber = temperatureSourceNumber;
        this.lightSourceNumber = lightSourceNumber;
    }

    @Override
    public abstract String toString();
}
