package garden.view;

import garden.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * The information menu of a plot
 *
 * @since 1.0
 * @author Alban
 */
public class ViewMenu extends JPanel {

    // Weather
    private final ViewWeather viewWeather;

    // Player
    private final ViewPlayer viewPlayer;

    // Plot
    private final ViewMenuPlot viewMenuPlot;


    /**
     * Constructor
     */
    public ViewMenu() {
        this.setPreferredSize(new Dimension(240, 48 * Scheduler.getInstance().getGarden().getPlots().length));
        this.setLayout(new BorderLayout());

        // Weather = TOP OF VIEW
        this.viewWeather = new ViewWeather();

        // RTS = BOTTOM OF VIEW
        ViewRTS viewRTS = new ViewRTS();

        // EVERYTHING ELSE = CENTER
        // Player & plot
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        // PLAYER = TOP OF CENTER
        this.viewPlayer = new ViewPlayer();
        center.add(this.viewPlayer, BorderLayout.PAGE_START);

        // PLOT = CENTER OF CENTER
        this.viewMenuPlot = new ViewMenuPlot();

        center.add(this.viewMenuPlot, BorderLayout.CENTER);

        this.add(this.viewWeather, BorderLayout.PAGE_START);
        this.add(center, BorderLayout.CENTER);
        this.add(viewRTS, BorderLayout.PAGE_END);
    }

    public void update(Garden g, int[] focusedPlot, Weather currentWeather, Player player) {

        // Weather infos
        this.viewWeather.update(currentWeather);

        // Player infos
        this.viewPlayer.update(player);

        // Plot infos
        this.viewMenuPlot.update(g, focusedPlot);

    }
}
