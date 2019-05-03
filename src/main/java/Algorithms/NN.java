package Algorithms;

import Structures.Node;
import Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NN {
    private List<Node> nodes;
    private List<Boolean> passed_nodes;
    private int og_root;
    private boolean closed;

    public NN(List<Node> nodes, int size) {
        init(nodes, size);
    }

    private void init(List<Node> nodes, int size){
        this.nodes = nodes;
        for (Node n : nodes)
            n.sortDistances();

        passed_nodes = new ArrayList<>();
        for (int i = 0; i < size; i++){
            passed_nodes.add(false);
        }
        closed = false;
    }

    public void setOg_root(int og_root) {
        this.og_root = og_root;
    }

    public List<Integer> tspNNMeh(int root_id){

        passed_nodes.set(root_id, true);
        List<Integer> list = new ArrayList<>();
        list.add(root_id);
        Map<Integer, Integer> distances = nodes.get(root_id).getDistances();
        for (int i : distances.keySet()){
            if(!passed_nodes.get(i)){
                //System.out.print(root_id + ", ");
                list.addAll(tspNNMeh(i));
                //sum += distances.get(i) + tspNN(i);
                break;
            }
        }

        boolean completed = true;
        for (boolean b : passed_nodes){
            if(!b)
                completed = false;
        }

        if(completed && !closed) {
            //sum += distances.get(og_root);
            list.add(og_root);
            closed = true;
        }
        return list;
    }

    public List<Integer> tspNN(int root_id, int[][] distanceMatrix){
        List<Integer> path = new ArrayList<>();
        int size = distanceMatrix.length;
        int[] list = new int[size];

        for (int i = 0; i < size; i++) {
            list[i] = i;
        }

        path.add(root_id);
        list[root_id] = -1;
        int current_node = root_id;
        boolean completed = false;
        int best_dist, best_node;
        do {
            best_dist = Integer.MAX_VALUE;
            best_node = best_dist;
            if(!(completed = Utility.checkIfArrayFullOfSingleValue(list, -1))) {
                for (int i = 0; i < size; i++) {
                    if(list[i] != -1) {
                        if (distanceMatrix[current_node][i] < best_dist) {
                            best_dist = distanceMatrix[current_node][i];
                            best_node = i;
                        }
                    }
                }
                list[best_node] = -1;
                path.add(best_node);
                current_node = best_node;
            }
        }while (!completed);

        path.add(root_id);
        return path;
    }
}
