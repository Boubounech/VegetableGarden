package garden.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.EnumMap;

/**
 * Weather holds values for each weather type :
 *  Humidity, sun exposure and temperature
 *  These values must be taken from json files
 *
 *  - weatherType : WeatherType
 *  - name : String
 *  - humidity : int
 *  - sunExposure : int
 *  - temperature : int
 *  + weathers : EnumMap<WeatherType, Weather>
 *
 *  + Weather()
 *  + Weather(weather : WeatherType)
 *  - createMaps()
 *  + getCurrentWeather() : WeatherType
 *  + getWeatherHumidity() : int
 *  + getWeatherSunExposure() : int
 *  + getWeatherTemperature() : int
 *  + setWeather(weather : WeatherType)
 */
public class Weather {

    public static EnumMap<WeatherType, Weather> weathers = new EnumMap<>(WeatherType.class);

    // Holds the name of the weather
    public WeatherType type;
    // Weather's display name
    public String name;
    // Weather's probability
    public int weight;
    // Humidity level procured by the weather
    public int humidity;
    // Light getting through the weather
    public int light;
    // Temperature provided by the weather
    public int temperature;

    public Weather(WeatherType type, String name, int weight, int humidity, int light, int temperature) {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.humidity = humidity;
        this.light = light;
        this.temperature = temperature;
    }

    public WeatherType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getWeight() { return this.weight; }

    public int getHumidity() {
        return humidity;
    }

    public int getLight() {
        return light;
    }

    public int getTemperature() {
        return temperature;
    }

    //function that loads the weathers from a JSON file
    public static void loadWeathers() throws IOException {
        Gson gson = new Gson();
        String json = null;
        try {
             json = JsonFileReader.readJSON("src/main/resources/json/weathers.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Weather[] weatherList = gson.fromJson(json, Weather[].class);
        for(Weather w : weatherList){
            weathers.put(w.getType(), w);
        }
    }

}
