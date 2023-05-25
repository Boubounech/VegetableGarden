package garden.model;

import java.util.Random;

public class Prop extends Plot {
    private String prop;

    public Prop() {
        String[] props = {"flowers", "rocks", "none"};
        this.prop = props[(new Random()).nextInt(props.length)];
    }

    @Override
    public void update() {

    }

    @Override
    public String getItem() {
        if (this.prop == null)
            return "empty";
        return this.prop;
    }

    @Override
    public String toString() {
        return "Parcelle non cultivable.\n";
    }
}
