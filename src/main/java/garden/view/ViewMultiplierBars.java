package garden.view;

import garden.model.Vegetable;
import garden.model.VegetableType;

import javax.swing.*;
import java.awt.*;

public class ViewMultiplierBars extends JPanel {
    private MultiplierBar waterBar;
    private MultiplierBar lightBar;
    private MultiplierBar temperatureBar;

    private VegetableType currentVegetable;

    public ViewMultiplierBars() {

        this.setLayout(new GridLayout(3, 1));


        JLabel waterIcon = new JLabel();
        waterIcon.setIcon(new ImageIcon(View.pictures.get("waterIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));
        JLabel lightIcon = new JLabel();
        lightIcon.setIcon(new ImageIcon(View.pictures.get("lightIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));
        JLabel temperatureIcon = new JLabel();
        temperatureIcon.setIcon(new ImageIcon(View.pictures.get("temperatureIcon").getScaledInstance(24, 24,  Image.SCALE_DEFAULT)));

        waterBar = new MultiplierBar(new float[]{0.0f}, 0.5f);
        lightBar = new MultiplierBar(new float[]{0.0f}, 0.5f);
        temperatureBar = new MultiplierBar(new float[]{0.0f}, 0.5f);

        JPanel water = new JPanel();
        water.setLayout(new BorderLayout());
        water.add(waterIcon, BorderLayout.LINE_START);
        water.add(waterBar, BorderLayout.CENTER);

        JPanel light = new JPanel();
        light.setLayout(new BorderLayout());
        light.add(lightIcon, BorderLayout.LINE_START);
        light.add(lightBar, BorderLayout.CENTER);

        JPanel temperature = new JPanel();
        temperature.setLayout(new BorderLayout());
        temperature.add(temperatureIcon, BorderLayout.LINE_START);
        temperature.add(temperatureBar, BorderLayout.CENTER);

        this.add(water);
        this.add(light);
        this.add(temperature);

        this.currentVegetable = null;
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
    }

    public void update(Vegetable v, int waterLevel, int lightLevel, int temperatureLevel) {
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

            temperatureBar.setFracs(new float[]{
                    0.0f,
                    ((float)(v.getIdealTemperature() - v.getRangeLimitTemperature())) * 0.01f,
                    ((float)(v.getIdealTemperature() - (v.getRangeLimitTemperature()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealTemperature() + (v.getRangeLimitTemperature()) * 0.5f)) * 0.01f,
                    ((float)(v.getIdealTemperature() + v.getRangeLimitTemperature())) * 0.01f,
                    1.0f
            });

            this.currentVegetable = v.getType();
        }

        this.waterBar.setCursor(waterLevel * 0.01f);
        this.lightBar.setCursor(lightLevel * 0.01f);
        this.temperatureBar.setCursor(temperatureLevel * 0.01f);
    }

    public VegetableType getCurrentVegetable() {
        return this.currentVegetable;
    }
}
