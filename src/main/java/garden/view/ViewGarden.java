package garden.view;

import garden.model.Garden;
import garden.model.Prop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
                this.plots[i][j] = new ViewPlot(i, j);
                this.add(this.plots[i][j]);
                this.plots[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON1){
                            System.out.println("Left click : Set as prop");
                            ViewPlot p = (ViewPlot) e.getSource();
                            View.getScheduler().setIsProp(p.getX(), p.getY(), true);
                        }
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            System.out.println("Right click : Set as cultivable");
                            ViewPlot p = (ViewPlot) e.getSource();
                            View.getScheduler().setIsProp(p.getX(), p.getY(), false);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

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

    public void update(Garden g){
        for(int x = 0; x < g.getPlots().length; x++){
            for(int y = 0; y < g.getPlots()[x].length; y++){
                this.plots[x][y].setIsProp(g.getPlot(x, y) instanceof Prop);
            }
        }
    }
}
