package garden.model;

import java.util.Random;

/**
 *  - vegetable : Vegetable
 */
public class CultivablePlot extends Plot {
    private Vegetable vegetable;
    private int stateGrowth;
    private int emptyTime;
    private int growTime;

    public CultivablePlot(int x, int y) {
        super(x, y);
        this.vegetable = null;
        this.stateGrowth = 0;
        this.emptyTime = 0;
        this.growTime = 0;
    }

    @Override
    public void update() {
        if(stateGrowth == 0 || vegetable == null){
            emptyTime++;
            if(emptyTime >= 500){
                Scheduler.getInstance().setIsProp(this.getX(), this.getY(), true);
                this.emptyTime = 0;
            }
        }
        else {
            double growMultiplier = getGrowMultiplier();
            growTime += growMultiplier;

            double growPercent = ((double) growTime / (double) vegetable.getGrowTime()) * 100;
            if(growPercent >= 200){
                //TODO : rotten vegetable
            }
            else if(growPercent >= 100){
                stateGrowth = 5;
            }
            else if(growPercent >= 75){
                stateGrowth = 4;
            }
            else if(growPercent >= 50){
                stateGrowth = 3;
            }
            else if(growPercent >= 25){
                stateGrowth = 2;
            }
        }
    }

    @Override
    public String getItem() {
        if(vegetable == null){
            return "empty";
        }
        return vegetable.getType().toString()+Math.min(stateGrowth-1, 3);
    }

    public int getGrowthState(){
        return stateGrowth;
    }

    public boolean containsVegetable() {
        return this.vegetable != null;
    }

    public Vegetable getVegetable(){
        return vegetable;
    }

    public boolean isPlantable() {
        return true;
    }

    public void plant(VegetableType vegetableType){
        this.vegetable = Vegetable.vegetables.get(vegetableType);
        stateGrowth = 1;
        growTime = 0;
    }

    public void harvest(){
        this.vegetable = null;
        stateGrowth = 0;
        growTime = 0;
        emptyTime = 0;
    }

    public void delete(){
        this.vegetable = null;
        stateGrowth = 0;
        growTime = 0;
        emptyTime = 0;
    }

    public double getGrowMultiplier(){
        double growMultiplier = 1;

        double waterMultiplier = getWaterMultiplier();
        double lightMultiplier = getLightMultiplier();
        double temperatureMultiplier = getTemperatureMultiplier();

        growMultiplier = waterMultiplier * lightMultiplier * temperatureMultiplier;
        if(growMultiplier < 1){
           growMultiplier = new Random().nextInt(0, 1);
        }

        return growMultiplier;
    }

    public double getWaterMultiplier() {
        if(this.getWaterLevel() - vegetable.getIdealHumidity() <= vegetable.getRangeLimitHumidity()/2){
            return 2;
        }
        else if(this.getWaterLevel() - vegetable.getIdealHumidity() <= vegetable.getRangeLimitHumidity()){
            return 1;
        }
        else{
            return 0.5;
        }
    }

    public double getLightMultiplier() {
        if(this.getLightLevel() - vegetable.getIdealLight() <= vegetable.getRangeLimitLight()/2){
            return 3;
        }
        else if(this.getLightLevel() - vegetable.getIdealLight() <= vegetable.getRangeLimitLight()){
            return 2;
        }
        else{
            return 1;
        }
    }

    public double getTemperatureMultiplier() {
        if(this.getTemperatureLevel() - vegetable.getIdealTemperature() <= vegetable.getRangeLimitTemperature()){
            return 2;
        }
        else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Parcelle cultivable.\n";
    }
}
