package garden;

import garden.model.Garden;
import garden.model.Scheduler;
import garden.model.Vegetable;
import garden.view.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = Scheduler.getScheduler();
        new Thread(scheduler).start();

        View view = new View();

        scheduler.addObserver(view);

        view.setVisible(true);
    }
}