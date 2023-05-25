package garden.view;

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

    /**
     * Constructor
     */
    public ViewMenu() {
        this.label = new JLabel("Menu");
        this.add(this.label);
        this.setPreferredSize(new Dimension(240,480));
    }
}
