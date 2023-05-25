package garden.view;

import com.google.gson.Gson;
import garden.model.JsonFileReader;
import garden.model.VegetableType;
import garden.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewPlot extends JPanel {
    private Image background;
    private Image item;

    public ViewPlot() {
        this.setPreferredSize(new Dimension(48, 48));
        this.background = View.pictures.get("cultivablePlot");
        this.item = View.pictures.get("carrot3");
    }

    public void paint(Graphics g) {
         g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
         g.drawImage(this.item, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void setIsProp(Boolean isProp){
        if (isProp)
            this.background = View.pictures.get("emptyPlot");
        else
            this.background = View.pictures.get("cultivablePlot");

        repaint();
    }
}
