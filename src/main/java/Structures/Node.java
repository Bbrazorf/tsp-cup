package Structures;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Node {
    private int id;
    private Map<Integer, Integer> distances;

    public Node(int id) {
        this.id = id;
        distances = new LinkedHashMap<Integer, Integer>();
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getDistances() {
        return distances;
    }

    public void reset(){
        distances = new LinkedHashMap<Integer, Integer>();
    }

    public void addDistance(int city_target_id, int distance){
        if(!distances.containsKey(city_target_id))
            distances.put(city_target_id, distance);
    }

    public void sortDistances(){
        Map<Integer, Integer> sorted = distances
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
        distances = sorted;
    }
}
