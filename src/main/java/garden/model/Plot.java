package garden.model;

import java.util.ArrayList;

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

    private final int x;
    private final int y;

    private WaterSource waterSource;
    private LightSource lightSource;
    private TemperatureSource temperatureSource;

    private final PipeHolder pipeHolder;


    public Plot(int x, int y) {
        this.x = x;
        this.y = y;
        waterLevel = 0;
        lightLevel = 0;
        temperatureLevel = 0;
        waterSourceNumber = 0;
        lightSourceNumber = 0;
        temperatureSourceNumber = 0;
        this.pipeHolder = new PipeHolder();
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
        this.pipeHolder.setWaterSource(this.waterSource);
    }

    public void setLightSource(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    public void setTemperatureSource(TemperatureSource temperatureSource) {
        this.temperatureSource = temperatureSource;
    }

    public int getWaterLevel() {
        return Math.min(waterLevel + Scheduler.getScheduler().getWeather().getHumidity(), 100);
    }

    public int getRawWaterLevel() {
        return this.waterLevel;
    }

    public int getLightLevel() {
        return (lightLevel * lightSourceNumber + Scheduler.getScheduler().getWeather().getLight()) / (lightSourceNumber + 1);
    }

    public int getRawLightLevel() {
        return this.lightLevel;
    }

    public int getTemperatureLevel() {
        return (temperatureLevel * temperatureSourceNumber + Scheduler.getScheduler().getWeather().getTemperature()) / (temperatureSourceNumber + 1);
    }

    public int getRawTemperatureLevel() {
        return this.temperatureLevel;
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

    public PipeHolder addPipe(PipeType pt){
        Pipe p = this.pipeHolder.setPipe(pt);

        // Look for near water
        calcWaterSourceFromPipe(-1);

        return this.pipeHolder;
    }

    private void calcWaterSourceFromPipe(int indexToIgnore) {

        // If he has any nearby pipe :
        boolean[] neighbourPipes = this.pipeHolder.hasNeighboursPipes();
        boolean hasAnyNbP = false;
        for (boolean neighbourPipe : neighbourPipes) {
            if (neighbourPipe) {
                hasAnyNbP = true;
                break;
            }
        }

        if (hasAnyNbP) {
            ArrayList<WaterSource> wss = new ArrayList<>();

            // There is a pipe here
            for (int i = 0; i < neighbourPipes.length; i++) {
                if (neighbourPipes[i] && i != indexToIgnore) {
                    // The pipe has water
                    if (this.pipeHolder.getNeighbourPipe(i).hasWaterSource()) {
                        // We get this water
                        wss.add(this.pipeHolder.getNeighbourPipe(i).getWaterSource());
                    }
                }
            }

            // If we did find water :
            if (!wss.isEmpty()) {
                // Taking them all ;) (if there is no water source)
                if (!this.hasWaterSource()) {
                    int totalStrength = 0;
                    int totalLength = 0;
                    for (WaterSource source : wss) {
                        totalStrength += source.getStrength();
                        totalLength += source.getLength();
                    }

                    WaterSource ws = new WaterSource(totalStrength, totalLength);
                    ws.setFromProp(false);

                    // Remove ancient sources
                    Scheduler.getScheduler().removeWaterSourceOf(x, y);

                    this.setWaterSource(ws);

                    Scheduler.getScheduler().addImpactFromSourceOf(x, y);
                }
            }
        }
    }

    public void setAsNeighbourPipe(int index, PipeHolder ph){
        this.pipeHolder.addNeighbourPipe(index, ph);

        if (this.hasWaterSource()){
            if (!this.waterSource.isFromProp()) {
                calcWaterSourceFromPipe((index + 2) % 4);
            }
        }
    }

    public PipeHolder getPipeHolder() {
        return this.pipeHolder;
    }



    @Override
    public abstract String toString();
}
