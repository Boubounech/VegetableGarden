package garden.model;

import java.util.Observable;

public class Scheduler extends Observable implements Runnable {

    private static Scheduler scheduler;
    private Garden garden;
    private WeatherManager weather;

    private Scheduler() {
        garden = new Garden(10, 10);
        weather = new WeatherManager();
    }

    public static Scheduler getScheduler(){
        if (scheduler == null){
            scheduler = new Scheduler();
        }
        return scheduler;
    }

    @Override
    public void run() {

        setChanged();
        notifyObservers();

        while(true) {
            try {
                Thread.sleep(1000/20);
                (new Thread(weather)).start();

                setChanged();
                notifyObservers();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Garden getGarden() {
        return this.garden;
    }

    public Weather getWeather() { return this.weather.getWeather();}

    // FUNCTION CALLED FROM VIEW
    public void setIsProp(int x, int y, boolean isProp){
        if (isProp)
            this.garden.setPlot(x, y, new Prop());
        else
            this.garden.setPlot(x, y, new CultivablePlot());
    }
}
