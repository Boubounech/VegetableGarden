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

    private int humidity;

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
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
        //if(!this.isProp)
        if (this.isFocused) {
            g2d.drawImage(View.pictures.get("border"), 0, 0, this.getWidth(), this.getHeight(), this);
        }
        if (!this.isProp) {
            if (this.humidity <= 50) {
                float alpha = Math.max(Math.min(Math.abs((float) (this.humidity - 50.0f) / 50.0f), 1.0f), 0.0f);

                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);

                g2d.setComposite(ac);
                g2d.drawImage(View.pictures.get("dryFilter"), 0, 0, this.getWidth(), this.getHeight(), this);
            } else {
                float alpha = Math.max(Math.min((float) (this.humidity - 50.0f) / 50.0f, 1.0f), 0.0f);
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);

                g2d.setComposite(ac);
                g2d.drawImage(View.pictures.get("wetFilter"), 0, 0, this.getWidth(), this.getHeight(), this);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        g2d.drawImage(this.item, 0, 0, this.getWidth(), this.getHeight(), this);
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

    public void setHumidity(int humidity) { this.humidity = humidity; }

    public int getHumidity() { return this.humidity; }
}
