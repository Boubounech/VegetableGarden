package garden.model;

public class Player {
    private int money;

    public static Player instance;

    private Player() {
        this.money = 10000;
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public int getMoney() {
        return money;
    }

    public boolean pay(int amount) {
        if(amount < 0)
            return false;
        if (this.money >= amount) {
            this.money -= amount;
            return true;
        }
        return false;
    }

    public void earn(int amount) {
        this.money += amount;
    }
}
