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
    private ViewMenuHelp menuHelp;
    private GridBagLayout mainLayout;
    private GridBagConstraints constraints;

    /**
     * Constructor
     */
    public View() {
        View.loadPictures();
        build();
    }

    private void build() {
        //Create the main panel
        this.mainPanel = new JPanel();

        //Create the garden and the menus
        this.garden = new ViewGarden();
        this.menu = new ViewMenu();
        this.menuHelp = new ViewMenuHelp();
        this.mainLayout = new GridBagLayout();
        this.mainPanel.setLayout(this.mainLayout);
        this.constraints = new GridBagConstraints();
        this.constraints.fill = GridBagConstraints.BOTH;

        //Add the component menuHelp to the main panel
        this.constraints.gridx = 0;
        this.constraints.gridy = 0;
        this.constraints.gridwidth = 2;
        this.mainLayout.setConstraints(this.menuHelp, this.constraints);
        this.mainPanel.add(this.menuHelp);

        //Add the component garden to the main panel
        this.constraints.insets = new Insets(0, 10, 10, 10);
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.constraints.gridwidth = 1;
        this.mainLayout.setConstraints(this.garden, this.constraints);
        this.mainPanel.add(this.garden);

        //Add the component menu to the main panel
                this.constraints.insets = new Insets(0, 10, 10, 10);
        this.constraints.gridx = 1;
        this.constraints.gridy = 1;
        this.mainLayout.setConstraints(this.menu, this.constraints);
        this.mainPanel.add(this.menu);

        //Set the main panel as the content pane
        this.setContentPane(this.mainPanel);

        //Set the window's properties
        this.setTitle("Garden Simulator 3000");
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
