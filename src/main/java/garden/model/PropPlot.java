package garden.model;

import java.util.Random;

public class PropPlot extends Plot {
    private Prop prop;


    public PropPlot() {
        int randVal = (new Random()).nextInt(Prop.props.size());
        this.prop = Prop.props.get(Prop.props.keySet().toArray()[randVal]);
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


    @Override
    public String toString() {
        return "Parcelle non cultivable.\n";
    }
}
