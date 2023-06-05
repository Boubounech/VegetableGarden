package garden.view;

import garden.model.*;

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
        // Pipe

        JMenuItem pipe;
        if (Scheduler.getInstance().getGarden().getPlot(x, y).hasPipe()) {
            pipe = new JMenuItem("Retirer le tuyau");
        } else {
            pipe = new JMenuItem("Poser un tuyau" + " (" + Pipe.pipes.get(PipeType.pipe).getPrice() + " g$)");
            if (Pipe.pipes.get(PipeType.pipe).getPrice() > Player.getInstance().getMoney()) {
                pipe.setEnabled(false);
            }
        }
        pipe.addActionListener(e1 -> {
            Scheduler.getInstance().swapPipe(x, y);
        });
        this.add(pipe);

        this.add(new JSeparator());

        if (isProp) {
            if(((PropPlot)(Scheduler.getInstance().getGarden().getPlot(x, y))).getProp().getType() != PropType.pond){
                JMenuItem propOrCultivable;
                propOrCultivable = new JMenuItem("Rendre cultivable" + " (" + PropPlot.getPriceToRemove() + " g$)");
                propOrCultivable.addActionListener(e1 -> {
                    Scheduler.getInstance().removeProp(x, y);
                });
                this.add(propOrCultivable);
            }
        }
        else {
            if(growthState == 0){
                Vegetable.vegetables.forEach((key, value) -> {
                    JMenuItem plant = new JMenuItem("Planter " + value.getName() + " (" + value.getSeedPrice() + " g$)");
                    plant.addActionListener(e1 -> {
                        Scheduler.getInstance().plant(x, y, key);
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
                    Scheduler.getInstance().harvest(x, y);
                });
                this.add(harvest);
            }
            else{
                JMenuItem delete = new JMenuItem("Arracher");
                delete.addActionListener(e1 -> {
                    Scheduler.getInstance().delete(x, y);
                });
                this.add(delete);
            }
        }

        this.setLightWeightPopupEnabled(false);
    }
}
