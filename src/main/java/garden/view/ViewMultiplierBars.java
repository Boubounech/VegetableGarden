package garden.view;

import garden.PictureLoader;
import garden.model.CultivablePlot;
import garden.model.Vegetable;
import garden.model.VegetableType;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ViewMultiplierBars extends JPanel {
    private MultiplierBar waterBar;
    private MultiplierBar lightBar;
    private MultiplierBar temperatureBar;

    private JLabel waterAmount;
    private JLabel lightAmount;
    private JLabel temperatureAmount;

    private JPanel total;
    private JLabel totalMultiplier;

    private VegetableType currentVegetable;

    public ViewMultiplierBars() {

        this.setLayout(new GridLayout(6, 1));

        this.add(new JSeparator());

        JLabel waterIcon = new JLabel();
        waterIcon.setIcon(new ImageIcon(PictureLoader.get("waterIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));
        JLabel lightIcon = new JLabel();
        lightIcon.setIcon(new ImageIcon(PictureLoader.get("lightIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));
        JLabel temperatureIcon = new JLabel();
        temperatureIcon.setIcon(new ImageIcon(PictureLoader.get("temperatureIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));

        waterBar = new MultiplierBar(new float[]{0.0f}, 0.5f);
        lightBar = new MultiplierBar(new float[]{0.0f}, 0.5f);
        temperatureBar = new MultiplierBar(new float[]{0.0f}, 0.5f);

        waterAmount = new JLabel();
        waterAmount.setPreferredSize(new Dimension(45, 20));
        waterAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        lightAmount = new JLabel();
        lightAmount.setPreferredSize(new Dimension(45, 20));
        lightAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        temperatureAmount = new JLabel();
        temperatureAmount.setPreferredSize(new Dimension(45, 20));
        temperatureAmount.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel water = new JPanel();
        water.setLayout(new BorderLayout());
        water.add(waterIcon, BorderLayout.LINE_START);
        water.add(waterBar, BorderLayout.CENTER);
        water.add(waterAmount, BorderLayout.LINE_END);

        JPanel light = new JPanel();
        light.setLayout(new BorderLayout());
        light.add(lightIcon, BorderLayout.LINE_START);
        light.add(lightBar, BorderLayout.CENTER);
        light.add(lightAmount, BorderLayout.LINE_END);

        JPanel temperature = new JPanel();
        temperature.setLayout(new BorderLayout());
        temperature.add(temperatureIcon, BorderLayout.LINE_START);
        temperature.add(temperatureBar, BorderLayout.CENTER);
        temperature.add(temperatureAmount, BorderLayout.LINE_END);

        this.add(water);
        this.add(light);
        this.add(temperature);

        this.currentVegetable = null;

        this.total = new JPanel();
        total.setLayout(new BorderLayout());

        totalMultiplier = new JLabel();
        totalMultiplier.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel totalText = new JLabel("Multiplicateur de pousse : ");

        total.add(totalText, BorderLayout.LINE_START);
        total.add(totalMultiplier, BorderLayout.LINE_END);

        this.add(this.total);

        this.add(new JSeparator());
    }

    public void update(int waterLevel, int lightLevel, int temperatureLevel) {
        // If there is a vegetable but should not
        if (this.currentVegetable != null) {
            waterBar.setFracs(new float[]{ 0.0f });

            lightBar.setFracs(new float[]{ 0.0f });

            temperatureBar.setFracs(new float[]{ 0.0f });

            this.currentVegetable = null;
        }

        this.waterBar.setCursor(waterLevel * 0.01f);
        this.lightBar.setCursor(lightLevel * 0.01f);
        this.temperatureBar.setCursor(temperatureLevel * 0.01f);

        this.waterAmount.setText(String.valueOf(waterLevel) + " %");
        this.lightAmount.setText(String.valueOf(lightLevel) + " %");
        DecimalFormat df = new DecimalFormat("0.0");
        this.temperatureAmount.setText(String.valueOf(df.format((temperatureLevel * 0.8) - 20)) + " °C");

        this.total.setVisible(false);
    }

    public void update(CultivablePlot cp, int waterLevel, int lightLevel, int temperatureLevel) {
        assert cp.containsVegetable();

        Vegetable v = cp.getVegetable();

        if (v.getType() != this.currentVegetable) {
            waterBar.setFracs(new float[]{
                    0.0f,
                    ((float)(v.getIdealHumidity() - v.getRangeLimitHumidity())) * 0.01f,
                    ((float)(v.getIdealHumidity() - (v.getRangeLimitHumidity()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealHumidity() + (v.getRangeLimitHumidity()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealHumidity() + v.getRangeLimitHumidity())) * 0.01f,
                    1.0f
            });

            lightBar.setFracs(new float[]{
                    0.0f,
                    ((float)(v.getIdealLight() - v.getRangeLimitLight())) * 0.01f,
                    ((float)(v.getIdealLight() - (v.getRangeLimitLight()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealLight() + (v.getRangeLimitLight()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealLight() + v.getRangeLimitLight())) * 0.01f,
                    1.0f
            });

            // No red values for temperature
            temperatureBar.setFracs(new float[]{
                    0.0f,
                    0.0f,
                    ((float)(v.getIdealTemperature() - (v.getRangeLimitTemperature()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealTemperature() + (v.getRangeLimitTemperature()) * 0.5f)) * 0.01f,
                    1.0f,
                    1.0f
            });

            this.currentVegetable = v.getType();
        }

        this.waterBar.setCursor(waterLevel * 0.01f);
        this.lightBar.setCursor(lightLevel * 0.01f);
        this.temperatureBar.setCursor(temperatureLevel * 0.01f);

        this.waterAmount.setText(String.valueOf(waterLevel) + " %");
        this.lightAmount.setText(String.valueOf(lightLevel) + " %");
        DecimalFormat df = new DecimalFormat("0.0");
        this.temperatureAmount.setText(String.valueOf(df.format((temperatureLevel * 0.8) - 20)) + " °C");

        this.totalMultiplier.setText("x" + cp.getGrowMultiplier());
        this.total.setVisible(true);
    }

    public VegetableType getCurrentVegetable() {
        return this.currentVegetable;
    }
}
