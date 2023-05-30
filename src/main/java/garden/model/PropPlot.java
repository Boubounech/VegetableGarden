package garden.model;

import java.util.Random;

public class PropPlot extends Plot {
    private Prop prop;
    private static int priceToRemove;
    private static int priceIncrement;


    public PropPlot(int x, int y) {
        super(x, y);
        PropType[] basicsProps = new PropType[] {PropType.none, PropType.flowers, PropType.rocks};
        int randVal = (new Random()).nextInt(basicsProps.length);
        this.prop = Prop.props.get(basicsProps[randVal]);
        priceToRemove = 1;
        priceIncrement = 1;

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

    public Prop getProp(){
        return this.prop;
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
