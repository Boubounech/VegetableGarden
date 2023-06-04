package garden.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class that displays the key help window and keep the focus until it is closed
 */
public class ViewKeyHelp extends JFrame {
    private JPanel panel;
    private JButton exitButton;

    public ViewKeyHelp(){
        super("Touches clavier");
        build();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                View.isActive = true;
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
        panel.add(new JLabel("Touches clavier :"));
        panel.add(new JLabel("Clic gauche : sélectionner un élément"));
        panel.add(new JLabel("Clic droit : ouvrir le menu contextuel"));
        panel.add(new JLabel("A : arracher un légume"));
        panel.add(new JLabel("R : récolter un légume"));
        panel.add(new JLabel("C : rendre cultivable une parcelle"));
        panel.add(new JLabel("P : contruire un tuyau"));
        panel.add(new JLabel("Z ou flèche haut : se déplacer vers le haut"));
        panel.add(new JLabel("Q ou flèche gauche : se déplacer vers la gauche"));
        panel.add(new JLabel("S ou flèche bas : se déplacer vers le bas"));
        panel.add(new JLabel("D ou flèche droite : se déplacer vers la droite"));
        panel.add(new JLabel("1 : planter une carotte"));
        panel.add(new JLabel("2 : planter une patate"));
        panel.add(new JLabel("3 : planter une aubergine"));
        panel.add(new JLabel("4 : planter une tomate"));
        panel.add(new JLabel("5 : planter une salade"));
        panel.add(new JLabel("6 : planter une citrouille"));
        exitButton = new JButton("Fermer");
        exitButton.addActionListener(e -> {
            View.isActive = true;
            this.dispose();
        });
        panel.add(exitButton);
        this.add(panel);
    }
}
