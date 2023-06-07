package garden.model;

import garden.PictureLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public abstract class JsonFileReader {
    public static String readJSON(String name) throws IOException {
        InputStream is = PictureLoader.class.getClassLoader().getResourceAsStream(name);
        if (is == null){
            System.err.println("Null.........................");
        }
        InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        reader.close();
        isReader.close();
        is.close();
        return sb.toString();
    }
}
