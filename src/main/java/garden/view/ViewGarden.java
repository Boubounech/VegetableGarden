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
                    public void mouseClicked(MouseEvent e) {
                        ViewPlot p = (ViewPlot) e.getSource();
                        focusedPlot[0] = p.getX();
                        focusedPlot[1] = p.getY();
                        if(e.getButton() == MouseEvent.BUTTON1){
                            System.out.println("Left click : Done nothing");
                        }
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            System.out.println("Right click : Open popup");
                            JPopupMenu popup = new JPopupMenu();
                            JMenuItem item;
                            if (p.getIsProp()) {
                                item = new JMenuItem("Set as cultivable");
                            }
                            else {
                                item = new JMenuItem("Set as prop");
                            }
                            item.addActionListener(e1 -> {
                                View.getScheduler().setIsProp(p.getX(), p.getY(), !p.getIsProp());
                            });
                            popup.add(item);
                            popup.setLightWeightPopupEnabled(false);
                            popup.show(e.getComponent(), e.getX(), e.getY());
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

    public int[] getFocusedPlot() {
        return focusedPlot;
    }

}
