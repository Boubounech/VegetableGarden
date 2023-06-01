package garden.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.EnumMap;

public class Pipe {
    private PipeType type;
    private int price;
    private int waterConduct;

    public static EnumMap<PipeType, Pipe> pipes = new EnumMap<>(PipeType.class);

    public Pipe(PipeType type, int price, int waterConduct){
        this.type = type;
        this.price = price;
        this.waterConduct = waterConduct;
    }


    //function that loads the pipes  from a JSON file
    public static void loadPipes() throws IOException {
        Gson gson = new Gson();
        String json = null;
        try {
            json = JsonFileReader.readJSON("src/main/resources/json/pipes.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pipe[] pipeList = gson.fromJson(json, Pipe[].class);
        for(Pipe p : pipeList){
            pipes.put(p.getType(), p);
        }
    }

    private PipeType getType() {
        return this.type;
    }

    private int getPrice() {
        return this.price;
    }

    private int getWaterConduct() {
        return this.waterConduct;
    }
}
