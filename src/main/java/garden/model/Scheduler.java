package garden.model;

import java.util.Observable;

public class Scheduler extends Observable implements Runnable {

    private static Scheduler scheduler;
    private Garden garden;
    private WeatherManager weather;

    private Scheduler() {
        garden = new Garden(15, 15, 4);
        weather = new WeatherManager(90, 180, 4);
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

    public void addImpactFromSourceOf(int x, int y) {
        this.garden.addImpactFromSourceOf(x, y);
    }

    public void addWaterSourceOf(int x, int y, WaterSource ws){
        this.garden.getPlot(x, y).setWaterSource(ws);
    }

    public void removeWaterSourceOf(int x, int y) {
        this.garden.removeWaterSourceOf(x, y);
    }

    // FUNCTION CALLED FROM VIEW

    public int getRandomTickSpeed() {
        return this.garden.getRandomTickSpeed();
    }

    public void setRandomTickSpeed(int rts) {
        this.garden.setRandomTickSpeed(rts);
    }

    public void setIsProp(int x, int y, boolean isProp){
        int waterLevel = this.garden.getPlot(x, y).getRawWaterLevel();
        int temperatureLevel = this.garden.getPlot(x, y).getRawTemperatureLevel();
        int lightLevel = this.garden.getPlot(x, y).getRawLightLevel();
        int waterSourceNumber = this.garden.getPlot(x, y).getWaterSourceNumber();
        int temperatureSourceNumber = this.garden.getPlot(x, y).getTemperatureSourceNumber();
        int lightSourceNumber = this.garden.getPlot(x, y).getLightSourceNumber();

        Plot plot;
        if (isProp) {
            plot = new PropPlot(x, y);
        }
        else {
            plot = new CultivablePlot(x, y);
        }
        plot.initSources(waterLevel, temperatureLevel, lightLevel, waterSourceNumber, temperatureSourceNumber, lightSourceNumber);
        this.garden.setPlot(x, y, plot);

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
                this.removeWaterSourceOf(x, y);
                this.setIsProp(x, y, false);
                PropPlot.updatePriceToRemove();
            }
        }

        setChanged();
        notifyObservers();
    }

    public void swapPipe(int x, int y){
        if (!this.garden.getPlot(x, y).getPipeHolder().hasPipe()){
            this.garden.addPipe(x, y, PipeType.pipe);
        } else {
            //this.garden.removePipe(x, y);
        }
    }

    public void removeWeatherSources(int waterLevel, int temperatureLevel, int lightLevel) {
        for (int x = 0; x < this.garden.getPlots().length; x++) {
            for (int y = 0; y < this.garden.getPlots()[0].length; y++) {
                this.garden.removeImpactFromWaterSource(x, y, waterLevel);
                this.garden.removeImpactFromTemperatureSource(x, y, temperatureLevel);
                this.garden.removeImpactFromLightSource(x, y, lightLevel);
            }
        }
    }

    public void addWeatherSources(int waterLevel, int temperatureLevel, int lightLevel) {
        for (int x = 0; x < this.garden.getPlots().length; x++) {
            for (int y = 0; y < this.garden.getPlots()[0].length; y++) {
                this.garden.addImpactFromWaterSource(x, y, waterLevel);
                this.garden.addImpactFromTemperatureSource(x, y, temperatureLevel);
                this.garden.addImpactFromLightSource(x, y, lightLevel);
            }
        }
    }

}
