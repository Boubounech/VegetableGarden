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
    private boolean isProp;
    private int x;
    private int y;
    private int growthState;

    private boolean isFocused;

    public ViewPlot(int x, int y) {
        this.x = x;
        this.y = y;
        this.setPreferredSize(new Dimension(48, 48));
        this.background = null;
        this.item = null;
        this.growthState = 0;
        this.isFocused = false;
        this.setIsProp(true);
    }

    public void paint(Graphics g) {
        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
        //if(!this.isProp)
        g.drawImage(this.item, 0, 0, this.getWidth(), this.getHeight(), this);
        if (this.isFocused) {
            g.drawImage(View.pictures.get("border"), 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void setIsProp(Boolean isProp){
        this.isProp = isProp;
        this.item = null;
        if (isProp)
            this.background = View.pictures.get("emptyPlot");
        else
            this.background = View.pictures.get("cultivablePlot");

        repaint();
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public boolean getIsProp(){
        return this.isProp;
    }

    public void setItem(String itemName){
        this.item = View.pictures.get(itemName);
        repaint();
    }

    public void setItemImage(Image item) {
        this.item = item;
        repaint();
    }

    public void setGrowthState(int growthState){
        this.growthState = growthState;
    }

    public int getGrowthState(){
        return this.growthState;
    }
}
