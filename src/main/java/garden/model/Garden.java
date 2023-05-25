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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        plots = new Plot[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++) {
                int alea = r.nextInt(2);
                if (alea == 0)
                    this.plots[x][y] = new Prop();
                else
                    this.plots[x][y] = new CultivablePlot();
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

}
