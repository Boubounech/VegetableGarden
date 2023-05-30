package garden.model;

public enum VegetableType {
    carrot,
    potato,
    eggplant,
    tomato,
    salad,
    pumpkin;

    public static VegetableType get(int i) {
        return VegetableType.values()[i];
    }
}
