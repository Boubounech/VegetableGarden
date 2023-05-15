package garden;

import garden.model.Scheduler;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = Scheduler.getScheduler();
        Thread th_sche = new Thread(scheduler);

        th_sche.start();
    }
}