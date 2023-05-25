package garden.model;

import java.util.Random;

/**
 *
 */
public class Garden implements Runnable {

    private Plot[][] plots;

    public Garden(int width, int height) {
        Random r = new Random();

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

    }
}
