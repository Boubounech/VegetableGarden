package garden.view;

import garden.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * The garden garden.view, with all plots
 *
 * @since 1.0
 * @author
 */
public class ViewGarden extends JPanel {
    private ViewPlot[][] plots;
    private GridLayout layout;
    private int[] focusedPlot;

    /**
     * Constructor
     */
    public ViewGarden() {
        this.plots = new ViewPlot[10][10];
        this.layout = new GridLayout(10, 10);
        this.focusedPlot = new int[2];

        for (int i = 0; i < this.plots.length; i++) {
            for (int j = 0; j < this.plots[i].length; j++) {
                this.plots[i][j] = new ViewPlot(i, j);
                this.add(this.plots[i][j]);
                this.plots[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {
                        ViewPlot p = (ViewPlot) e.getSource();
                        setFocusedPlot(p.getX(), p.getY());
                        if(e.getButton() == MouseEvent.BUTTON3){
                            ContextMenu contextMenu = new ContextMenu(p.getX(), p.getY(), p.getIsProp(), p.getGrowthState());
                            contextMenu.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }

        this.setLayout(this.layout);
    }

    private void setFocusedPlot(int x, int y) {
        this.plots[focusedPlot[0]][focusedPlot[1]].setFocused(false);
        focusedPlot[0] = x;
        focusedPlot[1] = y;
        this.plots[x][y].setFocused(true);
    }

    public void update(Garden g){
        for(int x = 0; x < g.getPlots().length; x++){
            for(int y = 0; y < g.getPlots()[x].length; y++){
                this.plots[x][y].setIsProp(g.getPlot(x, y) instanceof PropPlot);
                if (!(g.getPlot(x, y)).getItem().equals("empty")) {
                    this.plots[x][y].setItem(g.getPlot(x, y).getItem());
                }
                if (g.getPlot(x, y) instanceof CultivablePlot)
                    this.plots[x][y].setGrowthState(((CultivablePlot)g.getPlot(x, y)).getGrowthState());
            }
        }
    }

    public int[] getFocusedPlot() {
        return focusedPlot;
    }
}
