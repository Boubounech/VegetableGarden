package garden.view;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ViewMenuHelp extends JToolBar {
    private JButton keyHelpItem;
    private JButton vegetableHelpItem;

    public ViewMenuHelp() {
        super();

        build();
    }

    private void build(){
        keyHelpItem = new JButton("Touches clavier");
        //keyHelpItem.setPreferredSize(new Dimension(150, 20));
        //keyHelpItem.setMaximumSize(new Dimension(150, 20));
        keyHelpItem.setActionCommand("keyHelp");
        this.add(keyHelpItem);

        vegetableHelpItem = new JButton("Informations légumes");
        //vegetableHelpItem.setPreferredSize(new Dimension(150, 20));
        //vegetableHelpItem.setMaximumSize(new Dimension(150, 20));
        vegetableHelpItem.setActionCommand("vegetableHelp");
        this.add(vegetableHelpItem);

        this.setFloatable(false);

        keyHelpItem.addActionListener(e -> System.out.println("keyHelp"));

        vegetableHelpItem.addActionListener(e -> System.out.println("vegetableHelp"));
    }
}
