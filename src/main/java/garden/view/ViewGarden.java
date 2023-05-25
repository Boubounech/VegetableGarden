package garden.view;

import garden.model.Garden;
import garden.model.Prop;

import javax.swing.*;
import java.awt.*;

/**
 * The garden garden.view, with all plots
 *
 * @since 1.0
 * @author
 */
public class ViewGarden extends JPanel {
    private ViewPlot[][] plots;
    private GridLayout layout;

    /**
     * Constructor
     */
    public ViewGarden() {
        this.plots = new ViewPlot[10][10];
        this.layout = new GridLayout(10, 10);

        for (int i = 0; i < this.plots.length; i++) {
            for (int j = 0; j < this.plots[i].length; j++) {
                this.plots[i][j] = new ViewPlot();
                this.add(this.plots[i][j]);
            }
        }

        this.setLayout(this.layout);
    }

    public void update(Garden g){
        for(int x = 0; x < g.getPlots().length; x++){
            for(int y = 0; y < g.getPlots()[x].length; y++){
                this.plots[x][y].setIsProp(g.getPlot(x, y) instanceof Prop);
            }
        }
    }
}
