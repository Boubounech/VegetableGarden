package garden.view;

import garden.PictureLoader;

import javax.swing.*;
import java.awt.*;

public class ViewPlot extends JPanel {
    private Image background;
    private Image item;
    private boolean isProp;
    private final int x;
    private final int y;
    private int growthState;

    private int humidity;

    private boolean isFocused;

    private boolean hasPipe;
    private boolean[] neighboursPipes;

    public ViewPlot(int x, int y) {
        this.x = x;
        this.y = y;
        this.setPreferredSize(new Dimension(48, 48));
        this.background = null;
        this.item = null;
        this.growthState = 0;
        this.isFocused = false;
        this.setIsProp(true);
        this.neighboursPipes = new boolean[]{false, false, false, false};
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
        //if(!this.isProp)
        if (!this.isProp) {
            if (this.humidity <= 50) {
                float alpha = Math.max(Math.min(Math.abs((this.humidity - 50.0f) / 50.0f), 1.0f), 0.0f);

                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);

                g2d.setComposite(ac);
                g2d.drawImage(PictureLoader.get("dryFilter"), 0, 0, this.getWidth(), this.getHeight(), this);
            } else {
                float alpha = Math.max(Math.min((this.humidity - 50.0f) / 50.0f, 1.0f), 0.0f);
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);

                g2d.setComposite(ac);
                g2d.drawImage(PictureLoader.get("wetFilter"), 0, 0, this.getWidth(), this.getHeight(), this);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        g2d.drawImage(this.item, 0, 0, this.getWidth(), this.getHeight(), this);

        if (this.hasPipe) {
            if (this.neighboursPipes[0])
                g2d.drawImage(PictureLoader.get("pipeBottom"), 0, 0, this.getWidth(), this.getHeight(), this);
            if (this.neighboursPipes[1])
                g2d.drawImage(PictureLoader.get("pipeRight"), 0, 0, this.getWidth(), this.getHeight(), this);
            if (this.neighboursPipes[2])
                g2d.drawImage(PictureLoader.get("pipeTop"), 0, 0, this.getWidth(), this.getHeight(), this);
            if (this.neighboursPipes[3])
                g2d.drawImage(PictureLoader.get("pipeLeft"), 0, 0, this.getWidth(), this.getHeight(), this);

            if (this.item == PictureLoader.get("pond"))
                g2d.drawImage(PictureLoader.get("pipePond"), 0, 0, this.getWidth(), this.getHeight(), this);

            if (!this.neighboursPipes[0] && !this.neighboursPipes[1] && !this.neighboursPipes[2] && !this.neighboursPipes[3])
                g2d.drawImage(PictureLoader.get("pipeCenter"), 0, 0, this.getWidth(), this.getHeight(), this);
        }



        if (this.isFocused) {
            g2d.drawImage(PictureLoader.get("border"), 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }



    public void setIsProp(Boolean isProp){
        this.isProp = isProp;
        this.item = null;
        if (isProp)
            this.background = PictureLoader.get("emptyPlot");
        else
            this.background = PictureLoader.get("cultivablePlot");

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
        this.item = PictureLoader.get(itemName);
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

    public void setHasPipe(boolean hasPipe){
        this.hasPipe = hasPipe;
    }

    public void setNeighboursPipes(boolean[] neighboursPipes){
        this.neighboursPipes = neighboursPipes;
    }
}
