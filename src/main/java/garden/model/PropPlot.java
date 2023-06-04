package garden.model;

import java.util.Random;

public class PropPlot extends Plot {
    private Prop prop;
    private static int priceToRemove = 1;
    private static int priceIncrement = 1;


    public PropPlot(int x, int y) {
        super(x, y);
        PropType[] basicsProps = new PropType[] {PropType.grass, PropType.flowers, PropType.rocks};
        int randVal = (new Random()).nextInt(basicsProps.length);
        setRawProp(basicsProps[randVal]);
    }

    @Override
    public void update() {

    }

    @Override
    public String getItem() {
        if (this.prop == null)
            return "empty";
        return this.prop.getType().toString();
    }

    public boolean isPlantable() {
        return false;
    }

    public Prop getProp(){
        return this.prop;
    }

    public void setRawProp(PropType pt) {
        this.prop = Prop.props.get(pt);
    }

    public void setProp(PropType pt) {
        setRawProp(pt);
        if (this.prop.getHumidityStrength() != 0 && this.prop.getHumidityLength() != 0) {
            this.setWaterSource(new WaterSource(this.prop.getHumidityStrength(), this.prop.getHumidityLength()));
            this.getWaterSource().setFromProp(true);
        }
        if (this.prop.getLightStrength() != 0 && this.prop.getLightLength() != 0) {
            this.setLightSource(new LightSource(this.prop.getLightStrength(), this.prop.getLightLength()));
            this.getLightSource().setFromProp(true);
        }
        if (this.prop.getTemperatureStrength() != 0 && this.prop.getTemperatureLength() != 0) {
            this.setTemperatureSource(new TemperatureSource(this.prop.getTemperatureStrength(), this.prop.getTemperatureLength()));
            this.getTemperatureSource().setFromProp(true);
        }
        Scheduler.getScheduler().addImpactFromSourceOf(this.getX(), this.getY());
    }

    public static int getPriceToRemove() {
        return priceToRemove;
    }

    public static void updatePriceToRemove() {
        priceToRemove += priceIncrement;
        if (priceToRemove % (10*priceIncrement) == 0)
            priceIncrement *= 10;
    }

    @Override
    public String toString() {
        return "Parcelle non cultivable.\n";
    }
}
