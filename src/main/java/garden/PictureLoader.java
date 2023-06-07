package garden;

import com.google.gson.Gson;
import garden.model.JsonFileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PictureLoader {
    private static final Map<String, Image> pictures = new HashMap<String, Image>();

    public static Image get(String name){
        return pictures.get(name);
    }
    public static void loadPictures(){
        Gson gson = new Gson();
        String json = null;
        try {
            json = JsonFileReader.readJSON("json/pictures.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] picturesAsArray = gson.fromJson(json, String[].class);
        try {
            for(String p : picturesAsArray){
                URL url = PictureLoader.class.getClassLoader().getResource("pictures/" + p + ".png");
                Image i = ImageIO.read(url);
                pictures.put(p, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
