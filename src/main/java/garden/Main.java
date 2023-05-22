package garden;

import garden.model.Scheduler;
import garden.model.Vegetable;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = Scheduler.getScheduler();
        Thread th_sche = new Thread(scheduler);

        th_sche.start();
        try {
            Vegetable.loadVegetables("src/main/resources/json/vegetables.json");
            for(Vegetable v : Vegetable.vegetables.values()){
                System.out.println(v.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}