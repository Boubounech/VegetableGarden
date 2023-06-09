package garden.view;

import garden.PictureLoader;
import garden.model.Weather;
import garden.model.WeatherType;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ViewWeather extends JPanel {

    private final JLabel weatherPic;

    private final JLabel weatherName;
    private final JLabel weatherWater;
    private final JLabel weatherLight;
    private final JLabel weatherTemperature;

    private WeatherType displayedWeather;

    public ViewWeather() {
        // Weather
        this.setLayout(new BorderLayout());
        this.weatherPic = new JLabel();

        // Name & icon & value
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        this.weatherName = new JLabel();
        this.weatherName.setHorizontalAlignment(SwingConstants.CENTER);

        this.weatherWater = new JLabel();
        this.weatherLight = new JLabel();
        this.weatherTemperature = new JLabel();

        JLabel waterIcon = new JLabel();
        waterIcon.setIcon(new ImageIcon(PictureLoader.get("waterIcon").getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        waterIcon.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lightIcon = new JLabel();
        lightIcon.setIcon(new ImageIcon(PictureLoader.get("lightIcon").getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        lightIcon.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel temperatureIcon = new JLabel();
        temperatureIcon.setIcon(new ImageIcon(PictureLoader.get("temperatureIcon").getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        temperatureIcon.setHorizontalAlignment(SwingConstants.RIGHT);

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        weatherPanel.add(this.weatherName, c);
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        weatherPanel.add(waterIcon, c);
        c.gridx = 1;
        c.gridy = 1;
        weatherPanel.add(this.weatherWater, c);
        c.gridx = 0;
        c.gridy = 2;
        weatherPanel.add(lightIcon, c);
        c.gridx = 1;
        c.gridy = 2;
        weatherPanel.add(this.weatherLight, c);
        c.gridx = 0;
        c.gridy = 3;
        weatherPanel.add(temperatureIcon, c);
        c.gridx = 1;
        c.gridy = 3;
        weatherPanel.add(this.weatherTemperature, c);


        this.add(this.weatherPic, BorderLayout.LINE_START);
        this.add(weatherPanel, BorderLayout.CENTER);

        this.displayedWeather = null;
    }

    public void update(Weather currentWeather) {
        if (displayedWeather != currentWeather.getType()) {
            DecimalFormat df = new DecimalFormat("0.0");
            this.weatherName.setText(currentWeather.getName());
            this.weatherWater.setText(String.valueOf(currentWeather.getHumidity()) + " %");
            this.weatherLight.setText(String.valueOf(currentWeather.getLight()) + " %");
            this.weatherTemperature.setText(df.format((currentWeather.getTemperature() * 0.8) - 20) + " °C");
            this.weatherPic.setIcon(new ImageIcon(PictureLoader.get(currentWeather.getType().toString()).getScaledInstance(48 * 2, 48 * 2, Image.SCALE_DEFAULT)));

            this.displayedWeather = currentWeather.getType();
        }
    }
}
