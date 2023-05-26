package garden.view;

import garden.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.Hashtable;

/**
 * The information menu of a plot
 *
 * @since 1.0
 * @author
 */
public class ViewMenu extends JPanel {
    private JLabel label;
    private JLabel plotInfos;

    // Weather
    private JPanel weather;
    private JLabel weatherPic;
    private JLabel weatherLabel;

    // Random tick speed
    private JPanel rts;
    private JLabel rtsText;
    private JSlider rtsSlider;

    // Player
    private JPanel money;
    private JPanel moneyPanel;
    private JLabel moneyText;
    private JLabel moneyAmount;
    private JLabel moneyPic;

    // Player & plot
    private JPanel center;

    // Plot
    private JPanel plot;
    private JPanel plotGeneral;
    private ViewPlot plotPic;
    private JLabel plotCoords;


    /**
     * Constructor
     */
    public ViewMenu() {
        this.setPreferredSize(new Dimension(240, 480));
        this.setLayout(new BorderLayout());

        // Weather
        this.weather = new JPanel();
        this.weather.setLayout(new BorderLayout());
        this.weatherPic = new JLabel();
        this.weatherLabel = new JLabel();
        this.weather.add(this.weatherPic, BorderLayout.LINE_START);
        this.weather.add(this.weatherLabel, BorderLayout.CENTER);

        this.center = new JPanel();
        this.center.setLayout(new BorderLayout());

        // Player Infos
        this.money = new JPanel();
        this.money.setLayout(new BorderLayout());
        this.moneyPanel = new JPanel();
        this.moneyPanel.setLayout(new BorderLayout());
        this.moneyText = new JLabel();
        this.moneyText.setText("Argent : ");
        this.moneyAmount = new JLabel();
        this.moneyAmount.setText("NONE");
        this.moneyPanel.add(this.moneyText, BorderLayout.CENTER);
        this.moneyPanel.add(this.moneyAmount, BorderLayout.LINE_END);
        this.moneyPic = new JLabel();
        this.moneyPic.setIcon(new ImageIcon(View.pictures.get("gardenDollar")));
        this.money.add(this.moneyPanel, BorderLayout.CENTER);
        this.money.add(this.moneyPic, BorderLayout.LINE_END);

        this.center.add(this.money, BorderLayout.PAGE_START);

        // Plot
        this.plot = new JPanel();
        this.plot.setLayout(new BorderLayout());
        this.plotGeneral = new JPanel();
        this.plotGeneral.setLayout(new BorderLayout());
        this.plotPic = new ViewPlot(0, 0);
        this.plotCoords = new JLabel();
        this.plotGeneral.add(this.plotPic, BorderLayout.LINE_START);
        this.plotGeneral.add(this.plotCoords, BorderLayout.CENTER);
        this.plot.add(this.plotGeneral, BorderLayout.PAGE_START);

        this.center.add(this.plot, BorderLayout.CENTER);

        // Random Tick Speed
        this.rts = new JPanel();
        this.rts.setLayout(new BorderLayout());
        this.rtsText = new JLabel();
        this.rtsText.setText("<html><p>Vitesse de pousse</p></html>");
        this.rtsText.setHorizontalAlignment(SwingConstants.CENTER);
        this.rtsText.setPreferredSize(new Dimension(69, 50));
        this.rtsSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, (int) Math.sqrt(4));
        this.rtsSlider.addChangeListener(this::rtsSliderStateChanged);
        View.getScheduler().setRandomTickSpeed(this.rtsSlider.getValue());
        //Turn on labels at major tick marks.
        this.rtsSlider.setMinorTickSpacing(1);
        this.rtsSlider.setPaintTicks(true);
        //Create the label table
        Hashtable<Integer, JLabel> rtsLabelTable = new Hashtable<>();
        rtsLabelTable.put(0, new JLabel("x0"));
        rtsLabelTable.put((int) Math.sqrt(4), new JLabel("x1"));
        rtsLabelTable.put((int) Math.sqrt(16), new JLabel("x4"));
        rtsLabelTable.put((int) Math.sqrt(64), new JLabel("x16"));
        this.rtsSlider.setLabelTable(rtsLabelTable);
        this.rtsSlider.setPaintLabels(true);
        this.rts.add(this.rtsText, BorderLayout.LINE_START);
        this.rts.add(this.rtsSlider, BorderLayout.CENTER);


        this.plotInfos = new JLabel();

        this.add(this.weather, BorderLayout.PAGE_START);
        this.add(this.center, BorderLayout.CENTER);
        this.add(this.rts, BorderLayout.PAGE_END);
    }

    public void update(Garden g, int[] focusedPlot, Weather currentWeather, Player player) {
        plotInfos.setText(g.getPlot(focusedPlot[0], focusedPlot[1]).toString());

        // Weather infos
        this.weatherLabel.setText(currentWeather.getName());
        this.weatherPic.setIcon(new ImageIcon(View.pictures.get(currentWeather.getType().toString()).getScaledInstance(48 * 2, 48 * 2, Image.SCALE_DEFAULT)));

        // Player infos
        this.moneyAmount.setText(String.valueOf(player.getMoney()));

        // Plot infos
        this.plotCoords.setText("   x = " + (focusedPlot[0]+1) + " ; y = " + (focusedPlot[1]+1));
        this.plotPic.setItem(g.getPlot(focusedPlot[0], focusedPlot[1]).getItem());
        if (g.getPlot(focusedPlot[0], focusedPlot[1]) instanceof CultivablePlot) {
            if (this.plotPic.getIsProp())
                this.plotPic.setIsProp(false);
            this.plotPic.setGrowthState(((CultivablePlot) g.getPlot(focusedPlot[0], focusedPlot[1])).getGrowthState());
        } else {
            if (!this.plotPic.getIsProp())
                this.plotPic.setIsProp(true);
        }
    }

    // Listen to rts slider
    public void rtsSliderStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int randomTickSpeed = (int) (source.getValue() * source.getValue());
            View.getScheduler().setRandomTickSpeed(randomTickSpeed);
        }
    }
}
