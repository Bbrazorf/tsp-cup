package Utility;

import Structures.Distance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    public static final boolean DEBUG = false;
    public static final boolean RESULTS = true;

    public static void printDistanceMatrix(int size, Distance[][] distMatrix){
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                System.out.print(distMatrix[i][j].getDistance()+ "\t");
            }
            System.out.println(" ");
        }
    }

    public static void printPath(List<Integer> path){
        for (int i = 0; i < path.size(); i++){
            if(i%10 == 0)
                System.out.println("");
            if(i == path.size() - 1)
                System.out.println(path.get(i));
            else
                System.out.print(path.get(i) + ", ");
        }
    }

    public static void printPathForExcel(List<Integer> path){
        for (int i = 0; i < path.size(); i++){
            System.out.println(path.get(i));
        }
    }

    public static int calculateCostByPath(List<Integer> path, int[][] distanceMatrix){
        int cost = 0;
        for (int i = 0; i < path.size() -1 ; i++){
            cost += distanceMatrix[path.get(i)][path.get(i+1)];
        }
        return cost;
    }

    public static boolean checkIfArrayFullOfSingleValue(int[] array, int value){
        for (int i : array) {
            if(i != value)
                return false;
        }
        return true;
    }

    public static boolean checkIfArrayTrue(boolean[] array){
        for (boolean b : array) {
            if (!b)
                return false;
        }
        return true;
    }

    public static int gainBetweenPaths(List<Integer> path, List<Integer> path2, int[][] distanceMatrix){
        return calculateCostByPath(path, distanceMatrix) - calculateCostByPath(path2, distanceMatrix);
    }

    public static List<Integer> randomPath(int size){
        List<Integer> path = new ArrayList<>();
        List<Integer> available = new ArrayList<>();
        Random r = new Random();
        for(int i = 1; i <= size; i++)
            available.add(i);

        int initial = -1;

        for(int i = 1; i <= size; i++){
            int num = r.nextInt(available.size());
            path.add(available.get(num));
            if(initial == -1)
                initial = available.get(num);
            available.remove(num);
        }
        path.add(initial);
        System.out.println(path.size());
        return path;
    }
}
