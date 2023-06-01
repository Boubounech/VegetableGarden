package garden.view;

import garden.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * The information menu of a plot
 *
 * @since 1.0
 * @author
 */
public class ViewMenu extends JPanel {
    private JLabel label;

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
    private JLabel plotInfos;

    private JPanel plotContent;
    private JLabel plotContentPic;
    private JLabel plotContentName;
    private JLabel plotContentDescription;
    private JLabel plotContentLevels;

    private JPanel plotButtons;
    private JButton[] plotButtonsToShow;



    /**
     * Constructor
     */
    public ViewMenu() {
        this.setPreferredSize(new Dimension(240, 48 * Scheduler.getScheduler().getGarden().getPlots().length));
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
        // Plot plot
        this.plotGeneral = new JPanel();
        this.plotGeneral.setLayout(new BorderLayout());
        this.plotPic = new ViewPlot(0, 0);
        this.plotInfos = new JLabel();
        this.plotGeneral.add(this.plotPic, BorderLayout.LINE_START);
        this.plotGeneral.add(this.plotInfos, BorderLayout.LINE_END);
        // Plot content
        this.plotContent = new JPanel();
        this.plotContent.setLayout(new BorderLayout());
        this.plotContentName = new JLabel();
        this.plotContentPic = new JLabel();
        this.plotContentDescription = new JLabel();
        this.plotContentLevels = new JLabel();
        this.plotContent.add(this.plotContentName, BorderLayout.PAGE_START);
        this.plotContent.add(this.plotContentPic, BorderLayout.LINE_START);
        this.plotContent.add(this.plotContentDescription, BorderLayout.CENTER);
        this.plotContent.add(this.plotContentLevels, BorderLayout.PAGE_END);
        // Plot Buttons
        this.plotButtons = new JPanel();
        this.plotButtons.setLayout(new FlowLayout());
        this.plotButtonsToShow = new JButton[0];

        this.plotGeneral.add(this.plotContent, BorderLayout.PAGE_END);
        this.plot.add(this.plotGeneral, BorderLayout.PAGE_END);
        this.plot.add(this.plotButtons, BorderLayout.CENTER);

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
        Scheduler.getScheduler().setRandomTickSpeed(this.rtsSlider.getValue());
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

        this.add(this.weather, BorderLayout.PAGE_START);
        this.add(this.center, BorderLayout.CENTER);
        this.add(this.rts, BorderLayout.PAGE_END);
    }

    public void update(Garden g, int[] focusedPlot, Weather currentWeather, Player player) {

        // Weather infos
        this.weatherLabel.setText("<html>" +
                "<p> Météo : " + currentWeather.getName() + "</p>" +
                "<p> Humidité : " + currentWeather.getHumidity() + "%</p>" +
                "<p> Luminosité : " + currentWeather.getLight() + "%</p>" +
                "<p> Température : " + currentWeather.getTemperature() + "°C</p>" +
                "</html>");
        this.weatherPic.setIcon(new ImageIcon(View.pictures.get(currentWeather.getType().toString()).getScaledInstance(48 * 2, 48 * 2, Image.SCALE_DEFAULT)));

        // Player infos
        this.moneyAmount.setText(String.valueOf(player.getMoney()));

        // Plot general infos
        this.plotInfos.setText(g.getPlot(focusedPlot[0], focusedPlot[1]).toString());
        this.plotPic.setItem(g.getPlot(focusedPlot[0], focusedPlot[1]).getItem());
        this.plotPic.setHumidity(g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterLevel());
        if (g.getPlot(focusedPlot[0], focusedPlot[1]).hasWaterSource()) {
            this.plotContentLevels.setText("<html>" +
                    "<p> ws:" + g.getPlot(focusedPlot[0], focusedPlot[1]).hasWaterSource() + " - p:" + g.getPlot(focusedPlot[0], focusedPlot[1]).hasPipe() + "</p>" +
                    "<p> str: " + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterSource().getStrength() + " l:" + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterSource().getLength() + "</p>" +
                    "<p> nbsrc: " + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterSourceNumber() + "</p>" +
                    "<p> Humidité : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterLevel() + "</p>" +
                    "<p> Luminosité : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getLightLevel() + "</p>" +
                    "<p> Température : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getTemperatureLevel() + "</p>" +
                    "</html>");
        } else {
            this.plotContentLevels.setText("<html>" +
                    "<p> ws:" + g.getPlot(focusedPlot[0], focusedPlot[1]).hasWaterSource() + " - p:" + g.getPlot(focusedPlot[0], focusedPlot[1]).hasPipe() + "</p>" +
                    "<p> nbsrc: " + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterSourceNumber() + "</p>" +
                    "<p> Humidité : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getWaterLevel() + "</p>" +
                    "<p> Luminosité : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getLightLevel() + "</p>" +
                    "<p> Température : " + g.getPlot(focusedPlot[0], focusedPlot[1]).getTemperatureLevel() + "</p>" +
                    "</html>");
        }
        if (g.getPlot(focusedPlot[0], focusedPlot[1]) instanceof CultivablePlot cp) {
            if (this.plotPic.getIsProp())
                this.plotPic.setIsProp(false);
            this.plotPic.setGrowthState(cp.getGrowthState());



            // Plot content infos
            if (cp.containsVegetable()) {
                if (this.plotContentName.getText() != cp.getVegetable().getName()) {
                    this.plotContentPic.setIcon(new ImageIcon(View.pictures.get(cp.getVegetable().getType().toString() + "4").getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
                    this.plotContentName.setText(cp.getVegetable().getName());
                    this.plotContentDescription.setText("<html>" +
                            "<p>" + cp.getVegetable().getDescription() + "</p>" +
                            "</html>");
                }
            } else {
                if (this.plotContentName.getText() != "Terre") {
                    this.plotContentPic.setIcon(new ImageIcon(View.pictures.get("none").getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
                    this.plotContentName.setText("Terre");
                    this.plotContentDescription.setText("<html><p>De la terre qui n'attend plus que des cultures.</p></html>");
                }
            }
        } else if (g.getPlot(focusedPlot[0], focusedPlot[1]) instanceof PropPlot pp){
            if (!this.plotPic.getIsProp())
                this.plotPic.setIsProp(true);

            // Plot content infos
            this.plotContentPic.setIcon(new ImageIcon(View.pictures.get(pp.getProp().getType().toString()).getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
            this.plotContentName.setText(pp.getProp().getName());
            this.plotContentDescription.setText("<html><p>" + pp.getProp().getDescription() + "</p></html>");
        }
        // Plot buttons
        if (g.getPlot(focusedPlot[0], focusedPlot[1]) instanceof PropPlot) {
            JButton[] buttonsToShow = new JButton[1];
            buttonsToShow[0] = new JButton("Rendre cultivable");
            buttonsToShow[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Scheduler.getScheduler().setIsProp(focusedPlot[0], focusedPlot[1], false);
                }
            });
            this.plotButtonsToShow = buttonsToShow;
        } else if (g.getPlot(focusedPlot[0], focusedPlot[1]) instanceof CultivablePlot cp) {
            if (!cp.containsVegetable()) {
                JButton[] buttonsToShow = new JButton[6];
                for (int i = 0; i < 6; i++) {
                    VegetableType vt = (VegetableType) Vegetable.vegetables.keySet().toArray()[i];
                    buttonsToShow[i] = new JButton("Planter " + Vegetable.vegetables.get(vt).getName() + " (" + Vegetable.vegetables.get(vt).getSeedPrice() + " g$)");
                    buttonsToShow[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Scheduler.getScheduler().plant(focusedPlot[0], focusedPlot[1], vt);
                        }
                    });
                }
                this.plotButtonsToShow = buttonsToShow;
            } else {
                if (cp.getGrowthState() >= 4) {
                    JButton[] buttonsToShow = new JButton[1];
                    buttonsToShow[0] = new JButton("Récolter");
                    buttonsToShow[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Scheduler.getScheduler().harvest(focusedPlot[0], focusedPlot[1]);
                        }
                    });
                    this.plotButtonsToShow = buttonsToShow;
                } else {
                    JButton[] buttonsToShow = new JButton[1];
                    buttonsToShow[0] = new JButton("Arracher");
                    buttonsToShow[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Scheduler.getScheduler().delete(focusedPlot[0], focusedPlot[1]);
                        }
                    });
                    this.plotButtonsToShow = buttonsToShow;
                }
            }
        }
        this.plotButtons.removeAll();
        this.plotButtons.revalidate();
        this.plotButtons.repaint();
        for (JButton jButton : this.plotButtonsToShow) {
            this.plotButtons.add(jButton);
        }
    }

    // Listen to rts slider
    public void rtsSliderStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int randomTickSpeed = (int) (source.getValue() * source.getValue());
            Scheduler.getScheduler().setRandomTickSpeed(randomTickSpeed);
        }
    }
}
