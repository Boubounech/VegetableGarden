package garden.view;

import garden.model.Player;
import garden.model.PropPlot;
import garden.model.Scheduler;
import garden.model.Vegetable;

import javax.swing.*;

public class ContextMenu extends JPopupMenu {
    private int x;
    private int y;
    private boolean isProp;
    private int growthState;

    public ContextMenu(int x, int y, boolean isProp, int growthState) {
        this.x = x;
        this.y = y;
        this.isProp = isProp;
        this.growthState = growthState;
        build();
    }

    private void build() {
        if (isProp) {
            JMenuItem propOrCultivable;
            propOrCultivable = new JMenuItem("Rendre cultivable" + " (" + PropPlot.getPriceToRemove() + " g$)");
            propOrCultivable.addActionListener(e1 -> {
                Scheduler.getScheduler().removeProp(x, y);
            });
            this.add(propOrCultivable);
        }
        else {
            if(growthState == 0){
                Vegetable.vegetables.forEach((key, value) -> {
                    JMenuItem plant = new JMenuItem("Planter " + value.getName() + " (" + value.getSeedPrice() + " g$)");
                    plant.addActionListener(e1 -> {
                        Scheduler.getScheduler().plant(x, y, key);
                    });
                    if(value.getSeedPrice() > Player.getInstance().getMoney())
                        plant.setEnabled(false);
                    else plant.setEnabled(true);
                    this.add(plant);
                });
            }
            else if(growthState >= 4){
                JMenuItem harvest = new JMenuItem("Récolter");
                harvest.addActionListener(e1 -> {
                    Scheduler.getScheduler().harvest(x, y);
                });
                this.add(harvest);
            }
            else{
                JMenuItem delete = new JMenuItem("Arracher");
                delete.addActionListener(e1 -> {
                    Scheduler.getScheduler().delete(x, y);
                });
                this.add(delete);
            }
        }

        this.setLightWeightPopupEnabled(false);
    }
}
