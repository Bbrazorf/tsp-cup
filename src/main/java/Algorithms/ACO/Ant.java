package Algorithms.ACO;

import java.util.ArrayList;
import java.util.List;

public class Ant {
    private int id;
    private int current_city;
    private List<Integer> available;
    private List<Integer> og_available;
    private List<Integer> trail;

    public Ant(int id, int current_city, List<Integer> available) {
        this.id = id;
        this.current_city = current_city;
        this.available = available;
        og_available = available;
        trail = new ArrayList<>();
        available.remove(new Integer(current_city));
        trail.add(current_city);
    }

    public void moveTo(int city){
        current_city = city;
        available.remove(new Integer(city));
        trail.add(city);
    }

    public void resetAnt(){
        available = og_available;
        trail = new ArrayList<>();
        available.remove(new Integer(current_city));
        trail.add(current_city);
    }

    public int getCurrent_city() {
        return current_city;
    }

    public List<Integer> getAvailable() {
        return available;
    }

    public List<Integer> getTrail() {
        return trail;
    }

    public int getId() {
        return id;
    }
}
