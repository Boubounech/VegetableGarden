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


    @Override
    public String toString() {
        String text = "";
        text += "Parcelle non cultivable.\n";
        if (this.prop != null) {
            text += "Contient des " + this.prop.getName() + ".";
            text += "\n" + this.prop.getDescription();
        } else {
            text += "Ne contient rien.";
        }
        return text;
    }
}
