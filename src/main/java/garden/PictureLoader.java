package garden;

import com.google.gson.Gson;
import garden.model.JsonFileReader;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PictureLoader {
    private static Map<String, Image> pictures = new HashMap<String, Image>();

    public static Image get(String name){
        return pictures.get(name);
    }
    public static void loadPictures(){
        Gson gson = new Gson();
        String json = null;
        try {
             json = JsonFileReader.readJSON("src/main/resources/json/pictures.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] picturesAsArray = gson.fromJson(json, String[].class);
        for(String p : picturesAsArray){
            pictures.put(p, Toolkit.getDefaultToolkit().getImage("src/main/resources/pictures/"+p+".png"));
        }
    }
}
