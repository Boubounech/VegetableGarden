package garden.view;

import garden.model.Player;
import garden.model.PropPlot;
import garden.model.Scheduler;
import garden.model.VegetableType;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private ViewPlot[][] plots;
    private int[] focusedPlot;

    public KeyListener(ViewPlot[][] plots, int[] focusedPlot) {
        this.plots = plots;
        this.focusedPlot = focusedPlot;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_RIGHT){
            setFocusedPlot(focusedPlot[0], (focusedPlot[1] + 1) % plots.length);
        }
        if(e.getKeyCode()==KeyEvent.VK_Q || e.getKeyCode()==KeyEvent.VK_LEFT){
            setFocusedPlot(focusedPlot[0], (focusedPlot[1] + plots.length - 1) % plots.length);
        }
        if(e.getKeyCode()==KeyEvent.VK_Z || e.getKeyCode()==KeyEvent.VK_UP){
            setFocusedPlot((focusedPlot[0] + plots[0].length - 1) % plots[0].length, focusedPlot[1]);
        }
        if(e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_DOWN){
            setFocusedPlot((focusedPlot[0] + 1) % plots[0].length, focusedPlot[1]);
        }
        if(e.getKeyCode()==KeyEvent.VK_C){
            if(Player.getInstance().getMoney() >= PropPlot.getPriceToRemove() && plots[focusedPlot[0]][focusedPlot[1]].getIsProp()){
                Scheduler.getScheduler().removeProp(focusedPlot[0], focusedPlot[1]);
            }
        }
        if(!plots[focusedPlot[0]][focusedPlot[1]].getIsProp()){
            if(e.getKeyCode()==KeyEvent.VK_R){
                if(plots[focusedPlot[0]][focusedPlot[1]].getGrowthState() >= 4)
                    Scheduler.getScheduler().harvest(focusedPlot[0], focusedPlot[1]);
            }
            if(e.getKeyCode()==KeyEvent.VK_A){
                if(plots[focusedPlot[0]][focusedPlot[1]].getGrowthState() < 4)
                    Scheduler.getScheduler().delete(focusedPlot[0], focusedPlot[1]);
            }
            if(plots[focusedPlot[0]][focusedPlot[1]].getGrowthState() == 0){
                int[] keys = {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6};
                int[] numKeys = {KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6};
                for(int i = 0; i < keys.length; i++){
                    if(e.getKeyCode() == keys[i] || e.getKeyCode() == numKeys[i]){
                        Scheduler.getScheduler().plant(focusedPlot[0], focusedPlot[1], VegetableType.get(i));
                    }
                }
            }
        }
    }

    private void setFocusedPlot(int x, int y) {
        this.plots[focusedPlot[0]][focusedPlot[1]].setFocused(false);
        focusedPlot[0] = x;
        focusedPlot[1] = y;
        this.plots[x][y].setFocused(true);
    }
}
