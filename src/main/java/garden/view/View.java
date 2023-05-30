package garden.view;

import com.google.gson.Gson;
import garden.model.JsonFileReader;
import garden.model.Player;
import garden.model.Scheduler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * The main window of our vegetable garden
 *
 * @since 1.0
 * @author
 */
public class View extends JFrame implements Observer {
    private JPanel mainPanel;

    public static Map<String, Image> pictures = new HashMap<String, Image>();

    private ViewGarden garden;
    private ViewMenu menu;

    /**
     * Constructor
     */
    public View() {
        View.loadPictures();
        this.setTitle("Garden Simulator 3000");
        this.mainPanel = new JPanel();
        this.garden = new ViewGarden();
        this.menu = new ViewMenu();
        this.mainPanel.add(this.garden);
        this.mainPanel.add(this.menu);
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.garden.update(Scheduler.getScheduler().getGarden());
        this.menu.update(Scheduler.getScheduler().getGarden(), this.garden.getFocusedPlot(), Scheduler.getScheduler().getWeather(), Player.getInstance());
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
