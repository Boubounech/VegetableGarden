package garden;

import garden.model.Scheduler;
import garden.view.View;

public class Main {
    public static void main(String[] args) {
        PictureLoader.loadPictures();
        Scheduler scheduler = Scheduler.getInstance();
        new Thread(scheduler).start();

        View view = View.getInstance();

        scheduler.addObserver(view);

        view.setVisible(true);
    }
}