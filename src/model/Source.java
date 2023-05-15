package model;

/**
 *  - strength : int
 *  - length : int
 *
 *  + SetSourceData(str:int, len:int)
 *  + GetStrength() : int
 *  + GetLength() : int
 */
public abstract class Source {
    private int strength;
    private int length;

    public int getStrength() {
        return strength;
    }

    public int getLength() {
        return length;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setSourceData(int strength, int length) {
        setStrength(strength);
        setLength(length);
    }
}
