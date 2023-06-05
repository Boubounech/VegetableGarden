package garden.view;

import garden.model.Vegetable;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.text.DecimalFormat;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class ViewVegetableHelp extends JFrame{
    private JPanel panel;
    private JButton exitButton;
    private JTable table;
    private JTableHeader header;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public ViewVegetableHelp(){
        super("Informations sur les légumes");
        build();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                View.getInstance().isActive = true;
            }
        });
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void build(){
        panel = new JPanel();setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DecimalFormat df = new DecimalFormat("0.0");
        Object[][] donnees = new Object[Vegetable.vegetables.size()][7];
        int i = 0;
        for(Vegetable vegetable : Vegetable.vegetables.values()){
            donnees[i][0] = vegetable.getName();
            donnees[i][1] = vegetable.getGrowTime();
            donnees[i][2] = vegetable.getSeedPrice() + " g$";
            donnees[i][3] = vegetable.getSellPrice() + " g$";
            donnees[i][4] = vegetable.getIdealHumidity() + " %";
            donnees[i][5] = vegetable.getIdealLight() + " %";
            donnees[i][6] = df.format((vegetable.getIdealTemperature()*0.8)-20) + " °C";
            i++;
        }

        String[] entetes = {"Nom", "Durée de pousse", "Prix graine", "Prix de vente", "Humidité idéale", "Luminosité idéale", "Température idéale"};

        table = new JTable(donnees, entetes);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(125);
        table.getColumnModel().getColumn(6).setPreferredWidth(125);
        header = table.getTableHeader();
        header.setOpaque(true);
        header.setBackground(Color.lightGray);
        exitButton = new JButton("Fermer");
        exitButton.addActionListener(e -> {
            View.getInstance().isActive = true;
            this.dispose();
        });

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        panel.setLayout(layout);

        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 0, 10);
        layout.setConstraints(header, constraints);
        panel.add(header);

        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 10, 10, 10);
        layout.setConstraints(table, constraints);
        panel.add(table);

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0, 10, 10, 10);
        layout.setConstraints(exitButton, constraints);
        panel.add(exitButton);
        this.add(panel);
    }
}
