package garden.view;

import javax.swing.*;
import java.awt.*;

public class MultiplierBar extends JComponent {
    private float cursor;
    private float[] fracs;
    private final Color[] colors = new Color[] {Color.red, Color.yellow, Color.green, Color.yellow, Color.red};

    public MultiplierBar(float[] fracs, float cursor) {
        this.fracs = fracs;
        this.setPreferredSize(new Dimension(240, 24));
        this.cursor = cursor;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (fracs.length == 6) {
            for (int i = 0; i < fracs.length - 1; i++) {
                g2d.setColor(colors[i]);
                g2d.fillRect((int) (fracs[i]*this.getWidth()),  this.getHeight() / 4, (int) (fracs[i+1]*this.getWidth()) - (int) (fracs[i]*this.getWidth()), this.getHeight() / 2);
            }
        } else {
            g2d.setColor(Color.gray);
            g2d.fillRect(0, this.getHeight() / 4, this.getWidth(), this.getHeight() / 2);
        }

        g2d.setColor(Color.black);
        g2d.fillRect((int) (this.cursor*this.getWidth()) - 5,  0, 10, this.getHeight());
    }

    public void setCursor(float cursor) {
        this.cursor = cursor;

        repaint();
    }

    public void setFracs(float[] fracs) {
        this.fracs = fracs;

        repaint();
    }
}
