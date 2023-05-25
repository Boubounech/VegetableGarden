package garden.view;

import garden.model.Garden;
import garden.model.Weather;
import garden.model.WeatherType;

import javax.swing.*;
import java.awt.*;

/**
 * The information menu of a plot
 *
 * @since 1.0
 * @author
 */
public class ViewMenu extends JPanel {
    private JLabel label;
    private JLabel plotInfos;
    private JLabel weatherPic;

    /**
     * Constructor
     */
    public ViewMenu() {
        this.label = new JLabel("Menu");
        this.plotInfos = new JLabel();
        this.weatherPic = new JLabel();

        this.weatherPic.setPreferredSize(new Dimension(48*2, 48*2));

        this.add(this.label);
        this.add(this.plotInfos);
        this.add(this.weatherPic);
        this.setPreferredSize(new Dimension(240,480));
    }

    public void update(Garden g, int[] focusedPlot, Weather currentWeather) {
        plotInfos.setText(g.getPlot(focusedPlot[0], focusedPlot[1]).toString());
        this.weatherPic.setIcon(new ImageIcon(View.pictures.get(currentWeather.getType().toString())));
    }
}
