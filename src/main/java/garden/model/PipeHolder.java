package garden.model;

public class PipeHolder {
    private Pipe pipe;

    private PipeHolder[] neighboursPipes;

    private WaterSource ws;

    public PipeHolder() {
        this.pipe = null;
        neighboursPipes = new PipeHolder[] {null, null, null, null};
    }

    public Pipe setPipe(PipeType pt){
        this.pipe = Pipe.pipes.get(pt);
        return this.pipe;
    }

    public void setWaterSource(WaterSource ws) {
        this.ws = ws;
    }

    public boolean hasWaterSource(){
        return this.ws != null;
    }

    public WaterSource getWaterSource() {
        return this.ws;
    }

    public void removePipe(){
        this.pipe = null;
    }

    public boolean hasPipe(){
        return this.pipe != null;
    }


    public void addNeighbourPipe(int index, PipeHolder ph) {
        // 0:top, 1:right, 2:bottom, 3:left
        if (index <= 3 && index >= 0){
            this.neighboursPipes[index] = ph;
        }
    }

    public void removeNeighbourPipe(int index) {
        // 0:top, 1:right, 2:bottom, 3:left
        if (index <= 3 && index >= 0){
            this.neighboursPipes[index] = null;
        }
    }


    public boolean[] hasNeighboursPipes() {
        return new boolean[] {
                this.neighboursPipes[0] != null,
                this.neighboursPipes[1] != null,
                this.neighboursPipes[2] != null,
                this.neighboursPipes[3] != null
        };
    }

    public PipeHolder[] getNeighboursPipes() {
        return this.neighboursPipes;
    }

    public PipeHolder getNeighbourPipe(int index) {return this.neighboursPipes[index];}

}
