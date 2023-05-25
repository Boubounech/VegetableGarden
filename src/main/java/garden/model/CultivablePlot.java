package garden.model;

/**
 *  - vegetable : Vegetable
 */
public class CultivablePlot extends Plot {
    private Vegetable vegetable;
    private int stateGrowth;
    private int emptyTime;
    private int growTime;

    public CultivablePlot() {
        this.vegetable = null;
        this.stateGrowth = 0;
        this.emptyTime = 0;
        this.growTime = 0;
    }

    @Override
    public void update() {
        if(stateGrowth == 0 || vegetable == null){
            emptyTime++;
            if(emptyTime >= 10){
                //TODO : change the type of the plot
                this.emptyTime = 0;
            }
        }
        else {
            growTime++;
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

    public int getStateGrowth(){
        return stateGrowth;
    }

    public void plant(VegetableType vegetableType){
        this.vegetable = Vegetable.vegetables.get(vegetableType);
        stateGrowth = 1;
        growTime = 0;
        System.out.println("Planted " + this.vegetable.getType().toString());
    }

    @Override
    public String toString() {
        return "It is a CULTIVABLE PLOT";
    }
}
