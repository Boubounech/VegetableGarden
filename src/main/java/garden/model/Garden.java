package garden.model;

import java.io.IOException;
import java.util.Random;

/**
 *
 */
public class Garden implements Runnable {

    private Plot[][] plots;
    private int randomTickSpeed;


    public Garden(int width, int height, int randomTickSpeed) {
        this.randomTickSpeed = randomTickSpeed;
        Random r = new Random();

        try {
            Vegetable.loadVegetables();
            Prop.loadProps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        plots = new Plot[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++) {
                int alea = r.nextInt(2);
                if (alea == 0)
                    this.plots[x][y] = new PropPlot(x, y);
                else
                    this.plots[x][y] = new CultivablePlot(x, y);
            }
        }
    }

    public void updateAllSources(){
        this.plots[7][7] = new PropPlot(7, 7);
        ((PropPlot)(this.plots[7][7])).setProp(PropType.pond);

        for(int x = 0; x < this.plots.length; x++){
            for(int y = 0; y < this.plots[0].length; y++) {
                if (this.plots[x][y] instanceof PropPlot pp)
                    pp.setProp(pp.getProp().getType());
            }
        }
    }

    public Plot[][] getPlots(){
        return this.plots;
    }

    public Plot getPlot(int x, int y) {
        return this.plots[x][y];
    }

    @Override
    public void run() {
        for(int x = 0; x < this.plots.length; x++){
            for(int y = 0; y < this.plots[x].length; y++) {
                //Update the plot with probability of randomTickSpeed/(width*height)
                if (Math.random() < (double) this.randomTickSpeed / (double) (this.plots.length * this.plots[x].length))
                    this.plots[x][y].update();
            }
        }
    }

    public void setPlot(int x, int y, Plot plot){
        this.plots[x][y] = plot;
    }

    public int getRandomTickSpeed() {
        return randomTickSpeed;
    }

    public void setRandomTickSpeed(int rts) {
        this.randomTickSpeed = rts;
    }

    public void addWaterSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getWaterLevel();
            int waterSourceNumber = this.plots[x][y].getWaterSourceNumber();
            int newLevel = (currentLevel * waterSourceNumber + level) / (waterSourceNumber + 1);
            this.plots[x][y].setWaterLevel(newLevel);
            this.plots[x][y].setWaterSourceNumber(waterSourceNumber + 1);
        }
    }

    public void addTemperatureSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getTemperatureLevel();
            int temperatureSourceNumber = this.plots[x][y].getTemperatureSourceNumber();
            int newLevel = (currentLevel * temperatureSourceNumber + level) / (temperatureSourceNumber + 1);
            this.plots[x][y].setTemperatureLevel(newLevel);
            this.plots[x][y].setTemperatureSourceNumber(temperatureSourceNumber + 1);
        }
    }

    public void addLightSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getLightLevel();
            int lightSourceNumber = this.plots[x][y].getLightSourceNumber();
            int newLevel = (currentLevel * lightSourceNumber + level) / (lightSourceNumber + 1);
            this.plots[x][y].setLightLevel(newLevel);
            this.plots[x][y].setLightSourceNumber(lightSourceNumber + 1);
        }
    }

    public void removeWaterSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getWaterLevel();
            int waterSourceNumber = this.plots[x][y].getWaterSourceNumber();
            if (waterSourceNumber <= 1) {
                this.plots[x][y].setWaterLevel(0);
                this.plots[x][y].setWaterSourceNumber(0);
            }
            else {
                int newLevel = (currentLevel * waterSourceNumber - level) / (waterSourceNumber - 1);
                this.plots[x][y].setWaterLevel(newLevel);
                this.plots[x][y].setWaterSourceNumber(waterSourceNumber - 1);
            }
        }
    }

    public void removeTemperatureSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getTemperatureLevel();
            int temperatureSourceNumber = this.plots[x][y].getTemperatureSourceNumber();
            if (temperatureSourceNumber <= 1) {
                this.plots[x][y].setTemperatureLevel(0);
                this.plots[x][y].setTemperatureSourceNumber(0);
            }
            else {
                int newLevel = (currentLevel * temperatureSourceNumber - level) / (temperatureSourceNumber - 1);
                this.plots[x][y].setTemperatureLevel(newLevel);
                this.plots[x][y].setTemperatureSourceNumber(temperatureSourceNumber - 1);
            }

        }
    }

    public void removeLightSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getLightLevel();
            int lightSourceNumber = this.plots[x][y].getLightSourceNumber();
            if (lightSourceNumber == 1) {
                this.plots[x][y].setLightLevel(0);
                this.plots[x][y].setLightSourceNumber(0);
            }
            else {
                int newLevel = (currentLevel * lightSourceNumber - level) / (lightSourceNumber - 1);
                this.plots[x][y].setLightLevel(newLevel);
                this.plots[x][y].setLightSourceNumber(lightSourceNumber - 1);
            }

        }
    }

    public void updateSourceOf(int x, int y) {
        // Water source
        if (this.plots[x][y].hasWaterSource()){
            WaterSource ws = this.plots[x][y].getWaterSource();
            for (int xi = x - ws.getLength(); xi <= x + ws.getLength(); xi++) {
                for (int yi = y - ws.getLength(); yi <= y + ws.getLength(); yi++) {
                    if (Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi)) < ws.getLength() + 0.5){
                        addWaterSource(xi, yi, ws.getStrength());
                    }
                }
            }
        }
        // Temperature source
        if (this.plots[x][y].hasTemperatureSource()){
            TemperatureSource ts = this.plots[x][y].getTemperatureSource();
            for (int xi = x - ts.getLength(); xi <= x + ts.getLength(); xi++) {
                for (int yi = y - ts.getLength(); yi <= y + ts.getLength(); yi++) {
                    if (Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi)) < ts.getLength() + 0.5){
                        addTemperatureSource(xi, yi, ts.getStrength());
                    }
                }
            }
        }
        // Light source
        if (this.plots[x][y].hasLightSource()){
            LightSource ls = this.plots[x][y].getLightSource();
            for (int xi = x - ls.getLength(); xi <= x + ls.getLength(); xi++) {
                for (int yi = y - ls.getLength(); yi <= y + ls.getLength(); yi++) {
                    if (Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi)) < ls.getLength() + 0.5){
                        addLightSource(xi, yi, ls.getStrength());
                    }
                }
            }
        }
    }


}
