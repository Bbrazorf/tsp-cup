package Algorithms;

import Utility.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoOpt {
    private int[][] distanceMatrix;
    private int size;

    public TwoOpt(int size, int[][] distanceMatrix) {
        this.size = size;
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> swapNodes(List<Integer> path, int i, int j){
        List<Integer> new_path = new ArrayList<>();
        int size = path.size();

        for (int k = 0; k <= i; k++) {
            new_path.add(path.get(k));
        }

        for (int k = j; k > i; k--) {
            new_path.add(path.get(k));
        }

        for (int k = j + 1; k < size-1; k++) {
            new_path.add(path.get(k));
        }

        new_path.add(new_path.get(0));
    /*
        new_path.addAll(path.subList(0, i+1));
        List<Integer> reverse = path.subList(i+1, j+1);
        Collections.reverse(reverse);
        new_path.addAll(reverse);
        new_path.addAll(path.subList(j+1, size));
*/
        return new_path;
    }

    private int computeGain(List<Integer> path, int i, int j){
        return (distanceMatrix[path.get(i)][path.get(j)] + distanceMatrix[path.get(i+1)][path.get(j+1)] - distanceMatrix[path.get(i)][path.get(i+1)] - distanceMatrix[path.get(j)][path.get(j+1)]);
    }

    public List<Integer> tsp2opt(List<Integer> path){
        boolean improvement = true, debug = false;
        double best_distance = Utility.calculateCostByPath(path, distanceMatrix);
        int iteration = 0, gains = 0, swaps = 0, gain, best_i = 0, best_j = 0, best_gain = 0, size = path.size();
        do{
            iteration++;
            best_gain = 0;
            for (int i = 0; i < size - 1; i++ ) {
                for (int j = i + 1; j < size - 1; j++) {
                    if((gain = computeGain(path, i,j)) < best_gain) {
                        gains++;
                        best_gain = gain;
                        best_i = i;
                        best_j = j;
                    }
                }
            }
            if(best_gain != 0){
                path = swapNodes(path, best_i, best_j);
                swaps++;
            }
        }while(best_gain < 0);
        if(debug) {
            System.out.println("\nIterations: " + iteration);
            System.out.println("Swaps: " + swaps);
            System.out.println("Gains: " + gains);
        }
        return path;
    }

}
