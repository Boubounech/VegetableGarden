package garden.model;

import java.util.Random;

/**
 * Weather manages the current weather in the garden
 *
 *  - weather : WeatherData
 *  - timeSinceLastChange : int
 *  - timeOfNextChange : int
 *  - timeChangeBottomLimit : int
 *  - timeChangeTopLimit : int
 *
 *  + Weather()
 *
 *  + getCurrentWeather() : WeatherData
 */
public class Weather implements Runnable {
    private WeatherData weather;
    private int timeSinceLastChange;
    private int timeOfNextChange;
    private final int timeChangeBottomLimit;
    private final int timeChangeTopLimit;

    public Weather() {
        timeChangeBottomLimit = 20;
        timeChangeTopLimit = 200;

        // At first, it's clear for the longest period
        timeOfNextChange = timeChangeTopLimit;

        weather = new WeatherData(WeatherType.clear);
        timeSinceLastChange = 0;
    }

    public WeatherData getWeather(){
        return weather;
    }

    @Override
    public void run() {
        timeSinceLastChange++;
        if (timeSinceLastChange > timeOfNextChange){
            Random rdm = new Random();
            weather.setWeather(WeatherType.values()[rdm.nextInt(WeatherType.values().length)]);
            timeSinceLastChange = 0;
            timeOfNextChange = rdm.nextInt(timeChangeTopLimit - timeChangeBottomLimit) + timeChangeBottomLimit;
        }
    }
}
