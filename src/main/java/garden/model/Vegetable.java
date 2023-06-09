package garden.model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 *  - name : String
 *  - type : VegetableType
 *  - seedPrice : int
 *  - sellPrice : int
 *  - growTime : int
 *  - idealHumidity : int
 *  - idealLight : int
 *  - idealTemperature : int
 *  - rangeLimitHumidity : int
 *  - rangeLimitLight : int
 *  - rangeLimitTemperature : int
 *
 *  + vegetales : EnumMap<VegetableType, Vegetable>
 *
 *  - Vegetable(name : String, type : VegetableType, seedPrice : int, sellPrice : int, growTime : int, idealHumidity : int, idealLight : int, idealTemperature : int, rangeLimitHumidity : int, rangeLimitLight : int, rangeLimitTemperature : int)
 *  + loadVegetables(path : String) : void
 *  + getVegetable(type : VegetableType) : Vegetable
 *  + getVegetableName(type : VegetableType) : String
 *  + getVegetableSeedPrice(type : VegetableType) : int
 *  + getVegetableSellPrice(type : VegetableType) : int
 *  + getVegetableGrowTime(type : VegetableType) : int
 *  + getVegetableIdealHumidity(type : VegetableType) : int
 *  + getVegetableIdealLight(type : VegetableType) : int
 *  + getVegetableIdealTemperature(type : VegetableType) : int
 *  + getVegetableRangeLimitHumidity(type : VegetableType) : int
 *  + getVegetableRangeLimitLight(type : VegetableType) : int
 *  + getVegetableRangeLimitTemperature(type : VegetableType) : int
 */
public class Vegetable implements Serializable {
    private String name;
    private VegetableType type;
    private String description;
    private int seedPrice;
    private int sellPrice;
    private int growTime;
    private int idealHumidity;
    private int idealLight;
    private int idealTemperature;
    private int rangeLimitHumidity;
    private int rangeLimitLight;
    private int rangeLimitTemperature;

    public static EnumMap<VegetableType, Vegetable> vegetables = new EnumMap<>(VegetableType.class);

    public Vegetable(String name, VegetableType type, String description, int seedPrice, int sellPrice, int growTime, int idealHumidity, int idealLight, int idealTemperature, int rangeLimitHumidity, int rangeLimitLight, int rangeLimitTemperature) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.seedPrice = seedPrice;
        this.sellPrice = sellPrice;
        this.growTime = growTime;
        this.idealHumidity = idealHumidity;
        this.idealLight = idealLight;
        this.idealTemperature = idealTemperature;
        this.rangeLimitHumidity = rangeLimitHumidity;
        this.rangeLimitLight = rangeLimitLight;
        this.rangeLimitTemperature = rangeLimitTemperature;
    }

    //function that loads the vegetables from a JSON file
    public static void loadVegetables() throws IOException {
        Gson gson = new Gson();
        String json = null;
        try {
             json = JsonFileReader.readJSON("json/vegetables.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Vegetable[] vegetableList = gson.fromJson(json, Vegetable[].class);
        for(Vegetable v : vegetableList){
            vegetables.put(v.getType(), v);
        }
    }

    public String getName() {
        return name;
    }

    public VegetableType getType() {
        return type;
    }

    public String getDescription() {return description;}

    public int getSeedPrice() {
        return seedPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getGrowTime() {
        return growTime;
    }

    public int getIdealHumidity() {
        return idealHumidity;
    }

    public int getIdealLight() {
        return idealLight;
    }

    public int getIdealTemperature() {
        return idealTemperature;
    }

    public int getRangeLimitHumidity() {
        return rangeLimitHumidity;
    }

    public int getRangeLimitLight() {
        return rangeLimitLight;
    }

    public int getRangeLimitTemperature() {
        return rangeLimitTemperature;
    }
}
