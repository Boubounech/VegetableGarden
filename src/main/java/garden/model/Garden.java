package garden.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            Pipe.loadPipes();
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
        ((PropPlot)(this.plots[7][7])).setRawProp(PropType.pond);

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

    public void addPipe(int x, int y, PipeType pt){
        this.plots[x][y].addPipe(pt);
        if (x > 0)
            this.plots[x-1][y].addNeighbourPipe(0);
        if (x < this.plots.length - 1)
            this.plots[x+1][y].addNeighbourPipe(2);
        if (y > 0)
            this.plots[x][y-1].addNeighbourPipe(1);
        if (y < this.plots[0].length - 1)
            this.plots[x][y+1].addNeighbourPipe(3);

        recalcPipesWaterSources();
    }

    private void recalcPipesWaterSources() {
        // Get prop water sources
        ArrayList<int[]> propWaterSources = new ArrayList<>();

        for (int x = 0; x < this.plots.length; x++){
            for (int y = 0; y < this.plots[0].length; y++){
                if (this.plots[x][y].hasWaterSource()) {
                    if (this.plots[x][y].getWaterSource().isFromProp()) {
                        propWaterSources.add(new int[] {x, y});
                    } else {
                        Scheduler.getScheduler().removeWaterSourceOf(x, y);
                    }
                }
            }
        }

        if (propWaterSources.isEmpty()){
            System.out.println("No Water Source with pipe");
            return;
        }

        //ArrayList<int[]> directSurroundings = getNearPipes(propWaterSources.get(0)[0], propWaterSources.get(0)[1]);
        // Recursively add water sources to each pipe surroundings water source
        recurAddWaterSources(propWaterSources.get(0)[0], propWaterSources.get(0)[1], propWaterSources);

        // Enjoy :D
    }

    private void recurAddWaterSources(int x, int y, ArrayList<int[]> alreadyDone) {
        //System.out.println("Going for " + x + ", " + y);

        alreadyDone.add(new int[] {x, y});

        ArrayList<int[]> around = getNearPipes(x, y);

        if (around.isEmpty())
            return;

        for (int[] p : around) {
            if (!this.plots[p[0]][p[1]].hasWaterSource()) {
                int strength = 0;
                int length = 0;
                if (this.plots[x][y].getWaterSource().isFromProp()) {
                    strength = (int) (this.plots[x][y].getWaterSource().getStrength() * ((float) this.plots[p[0]][p[1]].getPipe().getWaterConduct() / 100.0f));
                    length = (int) (this.plots[x][y].getWaterSource().getLength() * ((float) this.plots[p[0]][p[1]].getPipe().getWaterConduct() / 100.0f));
                } else {
                    strength = this.plots[x][y].getWaterSource().getStrength();
                    length = this.plots[x][y].getWaterSource().getLength();
                }
                WaterSource ws = new WaterSource(strength, length);
                ws.setFromProp(false);
                this.plots[p[0]][p[1]].setWaterSource(ws);
                Scheduler.getScheduler().addImpactFromSourceOf(p[0], p[1]);
            }

            // If we haven't done the next one
            boolean alrDone = false;
            for (int[] ad : alreadyDone) {
                if (ad[0] == p[0] && ad[1] == p[1]) {
                    alrDone = true;
                }
            }

            if (!alrDone) {
                recurAddWaterSources(p[0], p[1], alreadyDone);
            }
        }
    }

    private ArrayList<int[]> getNearPipes(int x, int y) {
        ArrayList<int[]> nearPipes = new ArrayList<>();

        boolean[] hasNearPipes = this.plots[x][y].getNeighboursPipes();
        for (int i = 0; i < hasNearPipes.length; i++) {
            if (hasNearPipes[i]) {
                if (i == 0)
                    nearPipes.add(new int[] {x+1, y});
                else if (i == 1)
                    nearPipes.add(new int[] {x, y+1});
                else if (i == 2)
                    nearPipes.add(new int[] {x-1, y});
                else if (i == 3)
                    nearPipes.add(new int[] {x, y-1});
            }
        }

        return nearPipes;
    }

    public void removePipe(int x, int y){
        this.plots[x][y].removePipe();
        if (x > 0)
            this.plots[x-1][y].removeNeighbourPipe(0);
        if (x < this.plots.length - 1)
            this.plots[x+1][y].removeNeighbourPipe(2);
        if (y > 0)
            this.plots[x][y-1].removeNeighbourPipe(1);
        if (y < this.plots[0].length - 1)
            this.plots[x][y+1].removeNeighbourPipe(3);

        recalcPipesWaterSources();
    }

    public void addImpactFromWaterSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawWaterLevel();
            int waterSourceNumber = this.plots[x][y].getWaterSourceNumber();
            //int newLevel = (currentLevel * waterSourceNumber + level) / (waterSourceNumber + 1);
            int newLevel = currentLevel + level;
            this.plots[x][y].setWaterLevel(newLevel);
            this.plots[x][y].setWaterSourceNumber(waterSourceNumber + 1);
        }
    }

    public void addImpactFromTemperatureSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawTemperatureLevel();
            int temperatureSourceNumber = this.plots[x][y].getTemperatureSourceNumber();
            int newLevel = (currentLevel * temperatureSourceNumber + level) / (temperatureSourceNumber + 1);
            this.plots[x][y].setTemperatureLevel(newLevel);
            this.plots[x][y].setTemperatureSourceNumber(temperatureSourceNumber + 1);
        }
    }

    public void addImpactFromLightSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawLightLevel();
            int lightSourceNumber = this.plots[x][y].getLightSourceNumber();
            int newLevel = (currentLevel * lightSourceNumber + level) / (lightSourceNumber + 1);
            this.plots[x][y].setLightLevel(newLevel);
            this.plots[x][y].setLightSourceNumber(lightSourceNumber + 1);
        }
    }

    public void removeImpactFromWaterSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawWaterLevel();
            int waterSourceNumber = this.plots[x][y].getWaterSourceNumber();
            if (waterSourceNumber <= 1) {
                this.plots[x][y].setWaterLevel(0);
                this.plots[x][y].setWaterSourceNumber(0);
            }
            else {
                int newLevel = currentLevel - level;
                this.plots[x][y].setWaterLevel(newLevel);
                this.plots[x][y].setWaterSourceNumber(waterSourceNumber - 1);
            }
        }
    }

    public void removeImpactFromTemperatureSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawTemperatureLevel();
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

    public void removeImpactFromLightSource(int x, int y, int level) {
        if (x >= 0 && x < this.plots.length && y >= 0 && y < this.plots[0].length){
            int currentLevel = this.plots[x][y].getRawLightLevel();
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

    public void addImpactFromSourceOf(int x, int y) {
        // Water source
        if (this.plots[x][y].hasWaterSource()){
            WaterSource ws = this.plots[x][y].getWaterSource();
            for (int xi = x - ws.getLength(); xi <= x + ws.getLength(); xi++) {
                for (int yi = y - ws.getLength(); yi <= y + ws.getLength(); yi++) {
                    double distance = Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi));
                    if (distance < ws.getLength() + 0.5f){
                        addImpactFromWaterSource(xi, yi, (int) (ws.getStrength() * (1.0f - distance/(ws.getLength() + 0.5f))));
                    }
                }
            }
        }
        // Temperature source
        if (this.plots[x][y].hasTemperatureSource()){
            TemperatureSource ts = this.plots[x][y].getTemperatureSource();
            for (int xi = x - ts.getLength(); xi <= x + ts.getLength(); xi++) {
                for (int yi = y - ts.getLength(); yi <= y + ts.getLength(); yi++) {
                    double distance = Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi));
                    if (distance < ts.getLength() + 0.5){
                        addImpactFromTemperatureSource(xi, yi, (int) (ts.getStrength() * (1.0f - distance/(ts.getLength() + 0.5f))));
                    }
                }
            }
        }
        // Light source
        if (this.plots[x][y].hasLightSource()){
            LightSource ls = this.plots[x][y].getLightSource();
            for (int xi = x - ls.getLength(); xi <= x + ls.getLength(); xi++) {
                for (int yi = y - ls.getLength(); yi <= y + ls.getLength(); yi++) {
                    double distance = Math.sqrt((x-xi)*(x-xi) + (y-yi)*(y-yi));
                    if (distance < ls.getLength() + 0.5){
                        addImpactFromLightSource(xi, yi, (int) (ls.getStrength() * (1.0f - distance/(ls.getLength() + 0.5f))));
                    }
                }
            }
        }
    }


    public void removeWaterSourceOf(int x, int y) {
        if (this.plots[x][y].hasWaterSource()) {
            WaterSource ws = this.plots[x][y].getWaterSource();
            for (int xi = x - ws.getLength(); xi <= x + ws.getLength(); xi++) {
                for (int yi = y - ws.getLength(); yi <= y + ws.getLength(); yi++) {
                    double distance = Math.sqrt((x - xi) * (x - xi) + (y - yi) * (y - yi));
                    if (distance < ws.getLength() + 0.5f) {
                        removeImpactFromWaterSource(xi, yi, (int) (ws.getStrength() * (1.0f - distance / (ws.getLength() + 0.5f))));
                    }
                }
            }
            this.plots[x][y].setWaterSource(null);
        }
    }

    public void removeTemperatureSourceOf(int x, int y) {
        if (this.plots[x][y].hasTemperatureSource()) {
            TemperatureSource ts = this.plots[x][y].getTemperatureSource();
            for (int xi = x - ts.getLength(); xi <= x + ts.getLength(); xi++) {
                for (int yi = y - ts.getLength(); yi <= y + ts.getLength(); yi++) {
                    double distance = Math.sqrt((x - xi) * (x - xi) + (y - yi) * (y - yi));
                    if (distance < ts.getLength() + 0.5f) {
                        removeImpactFromTemperatureSource(xi, yi, (int) (ts.getStrength() * (1.0f - distance / (ts.getLength() + 0.5f))));
                    }
                }
            }
        }
    }
}
