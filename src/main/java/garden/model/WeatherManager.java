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
    private int randomTickSpeed;
    private final int originalRTS;

    private int globalWeight;

    public WeatherManager(int bottomTime, int topTime, int rts) {
        timeChangeBottomLimit = bottomTime;
        timeChangeTopLimit = topTime;
        this.randomTickSpeed = rts;
        this.originalRTS = rts;

        // At first, it's clear for the longest period
        timeOfNextChange = timeChangeTopLimit;

        try {
            Weather.loadWeathers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        weather = Weather.weathers.get(WeatherType.clear);
        timeSinceLastChange = 0;

        this.globalWeight = 0;
        Weather.weathers.forEach((t, w) -> {
            this.globalWeight += w.getWeight();
        });
    }

    public Weather getWeather(){
        return weather;
    }

    public void setRandomTickSpeed(int rts) {
        this.randomTickSpeed = rts;
    }

    @Override
    public void run() {
        timeSinceLastChange += this.randomTickSpeed / this.originalRTS;
        if (timeSinceLastChange > timeOfNextChange){

            this.weather = getNextWeather();
            Random rdm = new Random();

            timeSinceLastChange = 0;
            timeOfNextChange = (rdm.nextInt(timeChangeTopLimit - timeChangeBottomLimit) + timeChangeBottomLimit);
        }
    }

    private Weather getNextWeather() {
        Random rdm = new Random();
        int alea = rdm.nextInt(1, this.globalWeight+1);

        for (int i = 0; i < WeatherType.values().length; i++){
            if (alea <= Weather.weathers.get(WeatherType.values()[i]).getWeight()) {
                return Weather.weathers.get(WeatherType.values()[i]);
            }
            else {
                alea -= Weather.weathers.get(WeatherType.values()[i]).getWeight();
            }
        }

        return Weather.weathers.get(WeatherType.clear);
    }
}
