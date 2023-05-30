package garden.model;

import java.util.Observable;

public class Scheduler extends Observable implements Runnable {

    private static Scheduler scheduler;
    private Garden garden;
    private WeatherManager weather;

    private Scheduler() {
        garden = new Garden(15, 15, 3);
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

        this.garden.updateAllSources();
        setChanged();
        notifyObservers();

        while(true) {
            try {
                Thread.sleep(1000/20);
                (new Thread(weather)).start();
                (new Thread(garden)).start();

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

    // FUNCTION CALLED FROM THE MODEL

    public void updateSourceOf(int x,int y) {
        this.garden.updateSourceOf(x, y);
    }

    // FUNCTION CALLED FROM VIEW

    public int getRandomTickSpeed() {
        return this.garden.getRandomTickSpeed();
    }

    public void setRandomTickSpeed(int rts) {
        this.garden.setRandomTickSpeed(rts);
    }

    public void setIsProp(int x, int y, boolean isProp){
        if (isProp)
            this.garden.setPlot(x, y, new PropPlot(x, y));
        else
            this.garden.setPlot(x, y, new CultivablePlot(x, y));

        setChanged();
        notifyObservers();
    }

    public void plant(int x, int y, VegetableType vegetableType){
        if (Player.getInstance().pay(Vegetable.vegetables.get(vegetableType).getSeedPrice())){
            ((CultivablePlot)(this.garden.getPlot(x, y))).plant(vegetableType);

            setChanged();
            notifyObservers();
        }
    }

    public void harvest(int x, int y){
        CultivablePlot plot = (CultivablePlot)(this.garden.getPlot(x, y));
        Player.getInstance().earn(plot.getVegetable().getSellPrice());
        plot.harvest();

        setChanged();
        notifyObservers();
    }

    public void delete(int x, int y){
        ((CultivablePlot)(this.garden.getPlot(x, y))).delete();

        setChanged();
        notifyObservers();
    }

    public void removeProp(int x, int y) {
        if (this.garden.getPlot(x, y) instanceof PropPlot) {
            if (Player.getInstance().pay(PropPlot.getPriceToRemove())) {
                this.garden.setPlot(x, y, new CultivablePlot(x, y));
                PropPlot.updatePriceToRemove();
            }
        }

        setChanged();
        notifyObservers();
    }
}
