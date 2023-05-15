package model;

import java.util.Observable;

public class Model extends Observable implements Runnable {
    private Garden garden;

    public Model() {
        garden = new Garden();
    }

    public Garden getGarden() {
        return garden;
    }

    @Override
    public void run() {

    }
}
