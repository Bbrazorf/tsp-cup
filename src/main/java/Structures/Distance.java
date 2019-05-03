package Structures;

public class Distance {
    private int id;
    private int id2;
    private int distance;

    public Distance(int id, int id2, int distance) {
        this.id = id;
        this.id2 = id2;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public int getId2() {
        return id2;
    }

    public int getDistance() {
        return distance;
    }
}
