package garden.model;

import java.util.Observable;

public class Scheduler extends Observable implements Runnable {

    private static Scheduler scheduler;
    private Garden garden;
    private Weather weather;

    private Scheduler() {
        garden = new Garden();
        weather = new Weather();
    }

    public static Scheduler getScheduler(){
        if (scheduler == null){
            scheduler = new Scheduler();
        }
        return scheduler;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000/20);
                (new Thread(weather)).start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
