package garden.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;

public class Prop implements Serializable {
    private PropType type;
    private String name;
    private String description;
    private int lightLevel;
    private int humidityLevel;
    private int temperatureLevel;

    public static EnumMap<PropType, Prop> props = new EnumMap<>(PropType.class);

    public Prop(PropType type, String name, String description, int lightLevel, int humidityLevel, int temperatureLevel) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.lightLevel = lightLevel;
        this.humidityLevel = humidityLevel;
        this.temperatureLevel = temperatureLevel;
    }

    //function that loads the props from a JSON file
    public static void loadProps() throws IOException {
        Gson gson = new Gson();
        String json = null;
        try {
             json = JsonFileReader.readJSON("src/main/resources/json/props.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Prop[] propList = gson.fromJson(json, Prop[].class);
        for(Prop p : propList){
            props.put(p.getType(), p);
        }
    }

    public PropType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public int getHumidityLevel() {
        return humidityLevel;
    }

    public int getTemperatureLevel() {
        return temperatureLevel;
    }
}
