package garden.view;

import garden.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ViewMenuPlot extends JPanel {


    private final ViewPlot plotPic;
    private final JLabel plotInfo;

    private final JLabel plotContentPic;
    private final JLabel plotContentName;
    private final JLabel plotContentDescription;
    private final JLabel plotContentLevels;

    private final JPanel plotButtons;
    private JButton[] plotButtonsToShow;


    public ViewMenuPlot(){

        // Plot
        this.setLayout(new BorderLayout());
        
        // Plot plot
        // Plot
        JPanel plotGeneral = new JPanel();
        plotGeneral.setLayout(new BorderLayout());
        
        this.plotPic = new ViewPlot(0, 0);
        
        this.plotInfo = new JLabel();
        
        plotGeneral.add(this.plotPic, BorderLayout.LINE_START);
        plotGeneral.add(this.plotInfo, BorderLayout.LINE_END);
        
        
        // Plot content
        JPanel plotContent = new JPanel();
        plotContent.setLayout(new BorderLayout());
        
        this.plotContentName = new JLabel();
        
        this.plotContentPic = new JLabel();
        
        this.plotContentDescription = new JLabel();
        
        this.plotContentLevels = new JLabel();
        
        plotContent.add(this.plotContentName, BorderLayout.PAGE_START);
        plotContent.add(this.plotContentPic, BorderLayout.LINE_START);
        plotContent.add(this.plotContentDescription, BorderLayout.CENTER);
        plotContent.add(this.plotContentLevels, BorderLayout.PAGE_END);
        
        
        // Plot Buttons
        this.plotButtons = new JPanel();
        this.plotButtons.setLayout(new FlowLayout());
        this.plotButtonsToShow = new JButton[0];

        plotGeneral.add(plotContent, BorderLayout.PAGE_END);
        
        
        this.add(plotGeneral, BorderLayout.PAGE_END);
        this.add(this.plotButtons, BorderLayout.CENTER);
    }
    
    public void update(Garden g, int[] fp) {

        // Plot cultivable or prop
        this.plotInfo.setText(g.getPlot(fp[0], fp[1]).toString());

        this.plotPic.setItem(g.getPlot(fp[0], fp[1]).getItem());
        this.plotPic.setHumidity(g.getPlot(fp[0], fp[1]).getWaterLevel());

        // Plot information
        showLevels(g, fp);

        // if plot is cultivable
        if (g.getPlot(fp[0], fp[1]) instanceof CultivablePlot cp) {
            if (this.plotPic.getIsProp())
                this.plotPic.setIsProp(false);
            this.plotPic.setGrowthState(cp.getGrowthState());

            // Plot content infos
            if (cp.containsVegetable()) {
                if (!Objects.equals(this.plotContentName.getText(), cp.getVegetable().getName())) {
                    this.plotContentPic.setIcon(new ImageIcon(View.pictures.get(cp.getVegetable().getType().toString() + "4").getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
                    this.plotContentName.setText(cp.getVegetable().getName());
                    this.plotContentDescription.setText("<html>" +
                            "<p>" + cp.getVegetable().getDescription() + "</p>" +
                            "</html>");
                }
            } else {
                if (!Objects.equals(this.plotContentName.getText(), "Terre")) {
                    this.plotContentPic.setIcon(new ImageIcon(View.pictures.get("none").getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
                    this.plotContentName.setText("Terre");
                    this.plotContentDescription.setText("<html><p>De la terre qui n'attend plus que des cultures.</p></html>");
                }
            }
        }

        // Else if plot is prop
        else if (g.getPlot(fp[0], fp[1]) instanceof PropPlot pp){
            if (!this.plotPic.getIsProp())
                this.plotPic.setIsProp(true);

            // Plot content infos
            this.plotContentPic.setIcon(new ImageIcon(View.pictures.get(pp.getProp().getType().toString()).getScaledInstance(24 * 2, 24 * 2, Image.SCALE_DEFAULT)));
            this.plotContentName.setText(pp.getProp().getName());
            this.plotContentDescription.setText("<html><p>" + pp.getProp().getDescription() + "</p></html>");
        }


        JButton[] newButtonsToShow = new JButton[0];

        // Plot buttons
        if (g.getPlot(fp[0], fp[1]) instanceof PropPlot) {
            if(((PropPlot)(Scheduler.getScheduler().getGarden().getPlot(fp[0], fp[1]))).getProp().getType() != PropType.pond){
                newButtonsToShow = new JButton[1];
                newButtonsToShow[0] = new JButton("Rendre cultivable (" + PropPlot.getPriceToRemove() + " g$)");
                newButtonsToShow[0].addActionListener(e -> Scheduler.getScheduler().removeProp(fp[0], fp[1]));
            }
            else{
                newButtonsToShow = new JButton[0];
            }
        }
        else if (g.getPlot(fp[0], fp[1]) instanceof CultivablePlot cp) {
            if (!cp.containsVegetable()) {
                newButtonsToShow = new JButton[6];
                for (int i = 0; i < 6; i++) {
                    VegetableType vt = (VegetableType) Vegetable.vegetables.keySet().toArray()[i];
                    newButtonsToShow[i] = new JButton("Planter " + Vegetable.vegetables.get(vt).getName() + " (" + Vegetable.vegetables.get(vt).getSeedPrice() + " g$)");
                    newButtonsToShow[i].addActionListener(e -> Scheduler.getScheduler().plant(fp[0], fp[1], vt));
                }
            } else {
                if (cp.getGrowthState() >= 4) {
                    newButtonsToShow = new JButton[1];
                    newButtonsToShow[0] = new JButton("Récolter");
                    newButtonsToShow[0].addActionListener(e -> Scheduler.getScheduler().harvest(fp[0], fp[1]));
                } else {
                    newButtonsToShow = new JButton[1];
                    newButtonsToShow[0] = new JButton("Arracher");
                    newButtonsToShow[0].addActionListener(e -> Scheduler.getScheduler().delete(fp[0], fp[1]));
                }
            }
        }

        // Remove all buttons
        if (newButtonsToShow.length == 0 && this.plotButtonsToShow.length != 0){
            this.plotButtonsToShow = newButtonsToShow;

            this.plotButtons.removeAll();
            this.plotButtons.revalidate();
            this.plotButtons.repaint();

            return;
        }

        // If there is new buttons
        if (this.plotButtonsToShow.length == 0) {
            this.plotButtonsToShow = newButtonsToShow;

            for (JButton jButton : this.plotButtonsToShow) {
                this.plotButtons.add(jButton);
            }

            return;
        }

        // If number of buttons changed : repaint them
        if (newButtonsToShow.length != this.plotButtonsToShow.length){
            this.plotButtonsToShow = newButtonsToShow;

            this.plotButtons.removeAll();
            this.plotButtons.revalidate();
            this.plotButtons.repaint();
            if (this.plotButtonsToShow != null) {
                for (JButton jButton : this.plotButtonsToShow) {
                    this.plotButtons.add(jButton);
                }
            }

            return;
        }

        // Or if type of buttons changed : repaint them
        if (!Objects.equals(newButtonsToShow[0].getText(), this.plotButtonsToShow[0].getText())){
            this.plotButtonsToShow = newButtonsToShow;

            this.plotButtons.removeAll();
            this.plotButtons.revalidate();
            this.plotButtons.repaint();
            if (this.plotButtonsToShow != null) {
                for (JButton jButton : this.plotButtonsToShow) {
                    this.plotButtons.add(jButton);
                }
            }
        }
    }

    private void showLevels(Garden g, int[] fp) {
        this.plotContentLevels.setText("<html>" +
                (g.getPlot(fp[0], fp[1]).isPlantable() ? (((CultivablePlot)g.getPlot(fp[0], fp[1])).containsVegetable() ? "<p> Multiplicateur de pousse : x" + ((CultivablePlot)g.getPlot(fp[0], fp[1])).getGrowMultiplier() + "</p>" : "") : "") +
                "<p> Humidité : " + g.getPlot(fp[0], fp[1]).getWaterLevel() + "</p>" +
                "<p> Luminosité : " + g.getPlot(fp[0], fp[1]).getLightLevel() + "</p>" +
                "<p> Température : " + g.getPlot(fp[0], fp[1]).getTemperatureLevel() + "</p>" +
                "</html>");
    }
}
