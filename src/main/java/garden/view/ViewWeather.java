package garden.view;

import garden.model.Weather;
import garden.model.WeatherType;

import javax.swing.*;
import java.awt.*;

public class ViewWeather extends JPanel {

    private final JLabel weatherPic;
    private final JLabel weatherLabel;

    private WeatherType displayedWeather;

    public ViewWeather() {
        // Weather
        this.setLayout(new BorderLayout());
        this.weatherPic = new JLabel();
        this.weatherLabel = new JLabel();
        this.add(this.weatherPic, BorderLayout.LINE_START);
        this.add(this.weatherLabel, BorderLayout.CENTER);
        this.displayedWeather = null;
    }

    public void update(Weather currentWeather) {
        if (displayedWeather != currentWeather.getType()) {
            this.weatherLabel.setText("<html>" +
                    "<p> Météo : " + currentWeather.getName() + "</p>" +
                    "<p> Humidité : " + currentWeather.getHumidity() + "%</p>" +
                    "<p> Luminosité : " + currentWeather.getLight() + "%</p>" +
                    "<p> Température : " + currentWeather.getTemperature() + "°C</p>" +
                    "</html>");
            this.weatherPic.setIcon(new ImageIcon(View.pictures.get(currentWeather.getType().toString()).getScaledInstance(48 * 2, 48 * 2, Image.SCALE_DEFAULT)));

            this.displayedWeather = currentWeather.getType();
        }
    }
}
