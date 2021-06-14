package Bed_reserve;

// to store and retrieve the bed details
public class BedDetailsContainer {
    private int vacant;
    private int occupied;
    private int total;

    public BedDetailsContainer(int vacant, int occupied, int total) {
        this.vacant = vacant;
        this.occupied = occupied;
        this.total = total;
    }

    public int getVacant() {
        return vacant;
    }

    public int getOccupied() {
        return occupied;
    }

    public int getTotal() {
        return total;
    }
}
