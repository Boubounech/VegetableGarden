package garden.view;

import garden.model.Scheduler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.Hashtable;

public class ViewRTS extends JPanel {

    public ViewRTS() {
        // Random Tick Speed
        this.setLayout(new BorderLayout());

        // Random tick speed
        JLabel rtsText = new JLabel();
        rtsText.setText("<html><p>Vitesse de pousse</p></html>");
        rtsText.setHorizontalAlignment(SwingConstants.CENTER);
        rtsText.setPreferredSize(new Dimension(69, 50));

        JSlider rtsSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, (int) Math.sqrt(4));
        rtsSlider.addChangeListener(this::rtsSliderStateChanged);
        Scheduler.getScheduler().setRandomTickSpeed(rtsSlider.getValue());

        //Turn on labels at major tick marks.
        rtsSlider.setMinorTickSpacing(1);
        rtsSlider.setPaintTicks(true);
        //Create the label table
        Hashtable<Integer, JLabel> rtsLabelTable = new Hashtable<>();
        rtsLabelTable.put(0, new JLabel("x0"));
        rtsLabelTable.put((int) Math.sqrt(4), new JLabel("x1"));
        rtsLabelTable.put((int) Math.sqrt(16), new JLabel("x4"));
        rtsLabelTable.put((int) Math.sqrt(64), new JLabel("x16"));
        rtsSlider.setLabelTable(rtsLabelTable);
        rtsSlider.setPaintLabels(true);

        this.add(rtsText, BorderLayout.LINE_START);
        this.add(rtsSlider, BorderLayout.CENTER);
    }

    // Listen to rts slider
    public void rtsSliderStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int randomTickSpeed = (source.getValue() * source.getValue());
            Scheduler.getScheduler().setRandomTickSpeed(randomTickSpeed);
        }
    }
}
