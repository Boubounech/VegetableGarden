package garden.view;

import garden.PictureLoader;
import garden.model.Player;

import javax.swing.*;
import java.awt.*;

public class ViewPlayer extends JPanel {

    private final JLabel moneyAmount;

    public ViewPlayer() {
        this.setLayout(new BorderLayout());

        // Player
        JLabel moneyText = new JLabel();
        moneyText.setText("Argent : ");

        this.moneyAmount = new JLabel();
        this.moneyAmount.setText("NONE");
        this.moneyAmount.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel moneyPic = new JLabel();
        moneyPic.setIcon(new ImageIcon(PictureLoader.get("gardenDollar")));

        this.add(new JSeparator(), BorderLayout.PAGE_START);
        this.add(moneyText, BorderLayout.LINE_START);
        this.add(this.moneyAmount, BorderLayout.CENTER);
        this.add(moneyPic, BorderLayout.LINE_END);
        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    public void update(Player player) {
        this.moneyAmount.setText(String.valueOf(player.getMoney()));
    }
}
