package garden.model;

import java.io.IOException;
import java.util.Random;

/**
 * WeatherManager manages the current weather in the garden
 *
 *  - weather : Weather
 *  - timeSinceLastChange : int
 *  - timeOfNextChange : int
 *  - timeChangeBottomLimit : int
 *  - timeChangeTopLimit : int
 *
 *  + WeatherManager()
 *
 *  + getWeather() : Weather
 */
public class WeatherManager implements Runnable {
    private Weather weather;
    private int timeSinceLastChange;
    private int timeOfNextChange;
    private final int timeChangeBottomLimit;
    private final int timeChangeTopLimit;

    public WeatherManager() {
        timeChangeBottomLimit = 20;
        timeChangeTopLimit = 200;

        // At first, it's clear for the longest period
        timeOfNextChange = timeChangeTopLimit;

        try {
            Weather.loadWeathers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        weather = Weather.weathers.get(WeatherType.clear);
        timeSinceLastChange = 0;
    }

    public Weather getWeather(){
        return weather;
    }

    @Override
    public void run() {
        timeSinceLastChange++;
        if (timeSinceLastChange > timeOfNextChange){
            Random rdm = new Random();
            weather = Weather.weathers.get(WeatherType.values()[rdm.nextInt(WeatherType.values().length)]);
            System.out.println("Weather is now : " + weather.getName());
            timeSinceLastChange = 0;
            timeOfNextChange = rdm.nextInt(timeChangeTopLimit - timeChangeBottomLimit) + timeChangeBottomLimit;
        }
    }
}
