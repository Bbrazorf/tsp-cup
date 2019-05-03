package Algorithms.ACO;

import Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ACO {
    private int ant_number;
    private int iterations;
    private double alpha;
    private double beta;
    private double q;
    private double eps;
    private double t0;
    private double vaporization;
    private double pheromone;
    private int[][] distance_matrix;
    private List<Ant> ants;
    private List<Integer> path;
    private Arc[][] arcs;
    private Random random;

    public ACO(int ant_number, int iterations, double alpha, double beta, double q, double eps, double t0, double vaporization, double pheromone, int[][] distance_matrix, Arc[][] arcs, List<Integer> path) {
        this.ant_number = ant_number;
        this.iterations = iterations;
        this.alpha = alpha;
        this.beta = beta;
        this.q = q;
        this.eps = eps;
        this.t0 = t0;
        this.vaporization = vaporization;
        this.pheromone = pheromone;
        this.distance_matrix = distance_matrix;
        ants = new ArrayList<>();
        for (int i = 0; i < ant_number; i++) {
            ants.add(new Ant(i, (i+1)*23, path));
        }
        this.arcs = arcs;
        this.path = path;
        random = new Random();
        random.setSeed(23);
    }

    private int selectNextCity(Ant ant)
    {
        double numerators[] = new double[ant.getAvailable().size()];
        double numerator, best_numerator = 0;
        int best_city = 0;
        double denumerator = 0;
        for(int city : ant.getAvailable()){
            Arc arc = arcs[ant.getCurrent_city()][city];
            numerator = Math.pow(arc.getP(), alpha) * Math.pow(1/arc.getD(), beta);
            numerators[city] = numerator;
            if(numerator > best_numerator){
                best_numerator = numerator;
                best_city = city;
            }
            denumerator += numerator;
        }

        if(random.nextDouble() >= q){
            return best_city;
        }

        ant.getAvailable().remove(best_city);
        double highest_numerator = -1;
        int highest_city = -1;

        for(int city : ant.getAvailable()){
            if(numerators[city] > highest_numerator){
                highest_numerator = numerators[city-1];
                highest_city = city;
            }
        }
        ant.getAvailable().add(best_city);

        return highest_city;
    }

    private void moveAnts(){
        int idx = 0;
        for(int i = 0; i < path.size()-1; i++)
        {
            for(Ant ant : ants){
                int current = ant.getTrail().get(ant.getTrail().size()-1);
                int next = selectNextCity(ant);
                ant.moveTo(next);
                arcs[current][next].setP((1-eps) * arcs[current][next].getP() + eps * t0);

            }
        }
    }

    private void updateBest(){
        int best_path = Integer.MAX_VALUE;
        Ant best_ant = null;
        for(Ant ant : ants){
            int length = Utility.calculateCostByPath(ant.getTrail(), distance_matrix);
            if(length < best_path){
                best_path = length;
                best_ant = ant;
            }
        }

        int old_city = -1;
        boolean first = true;

        for (int city : best_ant.getTrail()){
            if(first){
                first = false;
                old_city = city;
                continue;
            }
            arcs[old_city][city].setP((1-vaporization)*arcs[old_city][city].getP() + vaporization * (1/best_path));
        }
    }

    private void updateTrails(){
        for (Ant ant : ants){
            if(Utility.calculateCostByPath(ant.getTrail(), distance_matrix) < Utility.calculateCostByPath(path, distance_matrix)){
                path = ant.getTrail();
            }
            ant.resetAnt();
        }
    }

    public List<Integer> runACO(){
        for(int i = 0; i < iterations; i++)
        {
            System.out.println("Iteration " + i);
            moveAnts();
            updateBest();
            updateTrails();
        }

        return path;
    }

}
