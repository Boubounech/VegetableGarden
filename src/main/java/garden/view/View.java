package garden.view;

import garden.model.Player;
import garden.model.Scheduler;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The main window of our vegetable garden
 *
 * @since 1.0
 */
public class View extends JFrame implements Observer {

    private static View view;
    private ViewGarden garden;
    private ViewMenu menu;

    public boolean isActive = true;

    /**
     * Constructor
     */
    private View() {
        build();
    }

    public static View getInstance(){
        if(view == null){
            view = new View();
        }
        return view;
    }

    private void build() {
        //Create the main panel
        JPanel mainPanel = new JPanel();

        //Create the garden and the menus
        this.garden = new ViewGarden();
        this.menu = new ViewMenu();
        ViewMenuHelp menuHelp = new ViewMenuHelp();
        GridBagLayout mainLayout = new GridBagLayout();
        mainPanel.setLayout(mainLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        //Add the component menuHelp to the main panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        mainLayout.setConstraints(menuHelp, constraints);
        mainPanel.add(menuHelp);

        //Add the component garden to the main panel
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        mainLayout.setConstraints(this.garden, constraints);
        mainPanel.add(this.garden);

        //Add the component menu to the main panel
                constraints.insets = new Insets(0, 10, 10, 10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainLayout.setConstraints(this.menu, constraints);
        mainPanel.add(this.menu);

        //Set the main panel as the content pane
        this.setContentPane(mainPanel);

        //Set the window's properties
        this.setTitle("Garden Simulator 3000");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!isActive) {
            setEnabled(false);
        }
        else {
            if(!isEnabled()) {
                setEnabled(true);
                requestFocus();
            }
        }
        this.garden.update(Scheduler.getInstance().getGarden());
        this.menu.update(Scheduler.getInstance().getGarden(), this.garden.getFocusedPlot(), Scheduler.getInstance().getWeather(), Player.getInstance());
    }
}
