package garden.view;

import garden.model.Vegetable;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Class that displays the key help window and keep the focus until it is closed
 */
public class ViewKeyHelp extends JFrame {
    private JPanel panel;
    private JButton exitButton;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JTable table;
    private JTableHeader header;

    public ViewKeyHelp(){
        super("Touches clavier");
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
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        Object[][] donnees = {
                {"Sélectionner un élément", "Clic gauche", ""},
                {"Ouvrir le menu contextuel", "Clic droit", ""},
                {"Se déplacer vers le haut", "Z", "flèche haut"},
                {"Se déplacer vers la gauche", "Q", "flèche gauche"},
                {"Se déplacer vers le bas", "S", "flèche bas"},
                {"Se déplacer vers la droite", "D", "flèche droite"},
                {"Arracher un légume", "A", ""},
                {"Récolter un légume", "R", ""},
                {"Rendre cultivable une parcelle", "C", ""},
                {"Construire/déconstruire un tuyau", "P", ""},
                {"Planter une carotte", "1", ""},
                {"Planter une patate", "2", ""},
                {"Planter une aubergine", "3", ""},
                {"Planter une tomate", "4", ""},
                {"Planter une salade", "5", ""},
                {"Planter une citrouille", "6", ""}
        };

        String[] entetes = {"Action", "Touche 1", "Touche 2"};

        table = new JTable(donnees, entetes);
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
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
