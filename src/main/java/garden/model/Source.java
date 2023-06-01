package garden.model;

/**
 *  - strength : int
 *  - length : int
 *
 *  + SetSourceData(str:int, len:int)
 *  + GetStrength() : int
 *  + GetLength() : int
 */
public abstract class Source {
    /*
    TODO:
    isFromProp
     */
    private int strength;
    private int length;

    private boolean isFromProp;

    public int getStrength() {
        return strength;
    }

    public int getLength() {
        return length;
    }

    public boolean isFromProp() { return isFromProp; }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setFromProp(boolean isFromProp) { this.isFromProp = isFromProp; }

    public void setSourceData(int strength, int length, boolean isFromProp) {
        setStrength(strength);
        setLength(length);
        setFromProp(isFromProp);
    }
}
