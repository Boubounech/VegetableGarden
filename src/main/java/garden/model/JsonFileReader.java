package garden.model;

import java.io.*;

public abstract class JsonFileReader {
    public static String readJSON(String path) throws IOException {
        File jsonGameInfo = new File(path);
        FileInputStream fileIn = new FileInputStream(jsonGameInfo);
        InputStreamReader isReader = new InputStreamReader(fileIn);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        reader.close();
        isReader.close();
        fileIn.close();
        return sb.toString();
    }
}
