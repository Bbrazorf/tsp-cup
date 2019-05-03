package Structures;

import java.util.HashMap;
import java.util.Map;

public class CitySet {
    private Map<Integer, Coordinate> coords;
    private int size;
    private int best;

    public CitySet(int size) {
        this.size = size;
        coords = new HashMap<Integer, Coordinate>();
        best = 0;
    }

    public void addCoordinate(Coordinate coordinate, int id){
        coords.put(id, coordinate);
    }

    public Map<Integer, Coordinate> getCoords() {
        return coords;
    }

    public int getSize() {
        return size;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }
}
