package garden.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;

public class Prop implements Serializable {
    private PropType type;
    private String name;
    private String description;
    private int LightStrength;
    private int humidityStrength;
    private int temperatureStrength;
    private int LightLength;
    private int humidityLength;
    private int temperatureLength;

    public static EnumMap<PropType, Prop> props = new EnumMap<>(PropType.class);

    public Prop(PropType type, String name, String description, int lightStrength, int humidityStrength, int temperatureStrength, int lightLength, int humidityLength, int temperatureLength) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.LightStrength = lightStrength;
        this.humidityStrength = humidityStrength;
        this.temperatureStrength = temperatureStrength;
        this.LightLength = lightLength;
        this.humidityLength = humidityLength;
        this.temperatureLength = temperatureLength;
    }

    //function that loads the props from a JSON file
    public static void loadProps() throws IOException {
        Gson gson = new Gson();
        String json = null;
        try {
             json = JsonFileReader.readJSON("json/props.json");
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

    public int getLightStrength() {
        return LightStrength;
    }

    public int getHumidityStrength() {
        return humidityStrength;
    }

    public int getTemperatureStrength() {
        return temperatureStrength;
    }

    public int getLightLength() {
        return LightLength;
    }

    public int getHumidityLength() {
        return humidityLength;
    }

    public int getTemperatureLength() {
        return temperatureLength;
    }
}
