package garden.model;

import java.util.EnumMap;

/**
 * Weather Data holds garden.data for each weather type :
 *  Humidity, sun exposure and temperature
 *  This garden.data must be taken from json files
 *
 *  - weatherType : WeatherType
 *  - name : String
 *  - humidity : int
 *  - sunExposure : int
 *  - temperature : int
 *  - weatherToName : EnumMap<WeatherType, String>
 *  - weatherToHumidity : EnumMap<WeatherType, Integer>
 *  - weatherToSunExposure : EnumMap<WeatherType, Integer>
 *  - weatherToTemperature : EnumMap<WeatherType, Integer>
 *
 *  + WeatherData()
 *  + WeatherData(weather : WeatherType)
 *  - createMaps()
 *  + getCurrentWeather() : WeatherType
 *  + getWeatherHumidity() : int
 *  + getWeatherSunExposure() : int
 *  + getWeatherTemperature() : int
 *  + setWeather(weather : WeatherType)
 */
public class WeatherData {
    static private EnumMap<WeatherType, String> weatherToName = new EnumMap<>(WeatherType.class);
    static private EnumMap<WeatherType, Integer> weatherToHumidity = new EnumMap<>(WeatherType.class);
    static private EnumMap<WeatherType, Integer> weatherToSunExposure = new EnumMap<>(WeatherType.class);
    static private EnumMap<WeatherType, Integer> weatherToTemperature = new EnumMap<>(WeatherType.class);

    // Holds the name of the weather
    public WeatherType weatherType;
    // Weather's display name
    public String name;
    // Humidity level procured by the weather
    public int humidity;
    // Sun exposure (light) getting through the weather
    public int sunExposure;
    // Temperature provided by the weather
    public int temperature;

    public WeatherData() {
        if (weatherToName.isEmpty() || weatherToHumidity.isEmpty() || weatherToSunExposure.isEmpty() || weatherToTemperature.isEmpty())
            createMaps();
    }

    public WeatherData(WeatherType weather) {
        if (weatherToName.isEmpty() || weatherToHumidity.isEmpty() || weatherToSunExposure.isEmpty() || weatherToTemperature.isEmpty())
            createMaps();
        setWeather(weather);
    }

    public void setWeather(WeatherType weather) {
        if (weatherToName.isEmpty() || weatherToHumidity.isEmpty() || weatherToSunExposure.isEmpty() || weatherToTemperature.isEmpty())
            createMaps();
        this.weatherType = weather;
        this.name = weatherToName.get(this.weatherType);
        this.humidity = weatherToHumidity.get(this.weatherType);
        this.sunExposure = weatherToSunExposure.get(this.weatherType);
        this.temperature = weatherToTemperature.get(this.weatherType);
        System.out.println("Set weather to " + name);
    }

    public WeatherType getCurrentWeather() {
        return weatherType;
    }

    public String getName() {
        return name;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getSunExposure() {
        return sunExposure;
    }

    public int getTemperature() {
        return temperature;
    }

    // TODO: replace manual maps with json file
    private void createMaps() {
        weatherToName.clear();
        weatherToName.put(WeatherType.clear, "Clear");
        weatherToName.put(WeatherType.rain, "Rainy");
        weatherToName.put(WeatherType.cloud, "Some clouds");
        weatherToName.put(WeatherType.lotsofcloud, "Lots of clouds");
        weatherToName.put(WeatherType.thunder, "Thunder");
        weatherToName.put(WeatherType.snow, "Snowy");

        weatherToHumidity.clear();
        weatherToHumidity.put(WeatherType.clear, 0);
        weatherToHumidity.put(WeatherType.rain, 80);
        weatherToHumidity.put(WeatherType.cloud, 0);
        weatherToHumidity.put(WeatherType.lotsofcloud, 10);
        weatherToHumidity.put(WeatherType.thunder, 80);
        weatherToHumidity.put(WeatherType.snow, 50);

        weatherToSunExposure.clear();
        weatherToSunExposure.put(WeatherType.clear, 100);
        weatherToSunExposure.put(WeatherType.rain, 5);
        weatherToSunExposure.put(WeatherType.cloud, 50);
        weatherToSunExposure.put(WeatherType.lotsofcloud, 20);
        weatherToSunExposure.put(WeatherType.thunder, 0);
        weatherToSunExposure.put(WeatherType.snow, 5);

        weatherToTemperature.clear();
        weatherToTemperature.put(WeatherType.clear, 62);
        weatherToTemperature.put(WeatherType.rain, 44);
        weatherToTemperature.put(WeatherType.cloud, 56);
        weatherToTemperature.put(WeatherType.lotsofcloud, 50);
        weatherToTemperature.put(WeatherType.thunder, 50);
        weatherToTemperature.put(WeatherType.snow, 25);
    }
}
