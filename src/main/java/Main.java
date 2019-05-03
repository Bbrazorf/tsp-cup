import Algorithms.*;
import Algorithms.ACO.ACO;
import Algorithms.ACO.Arc;
import Structures.*;
import Utility.*;
import org.omg.CORBA.INTERNAL;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
            //System.out.println("Starting");
            String filename = args[0];
            Loader load = new Loader(filename);
            CitySet citySet = load.getCitySet();
            Distance[][] distMatrix = new Distance[citySet.getSize()][citySet.getSize()];
            Arc[][] arcs = new Arc[citySet.getSize()][citySet.getSize()];
            long start = System.nanoTime();
            long end;
            long seed = Long.parseLong(args[1]);
            long og_start = System.currentTimeMillis();

            int count = 1;
            Map<Integer, Coordinate> map = citySet.getCoords();
            for (int i = 0; i < citySet.getSize(); i++) {
                for (int j = count; j < citySet.getSize(); j++) {
                    double ix = map.get(i).getX(), ix2 = map.get(j).getX();
                    double iy = map.get(i).getY(), iy2 = map.get(j).getY();
                    double diff = ix - ix2, diff2 = iy - iy2;
                    distMatrix[i][j] = new Distance(i, j, (int) (Math.sqrt(diff * diff + diff2 * diff2) + 0.5));
                    distMatrix[j][i] = distMatrix[i][j];
                }
                count++;
            }

            int[][] distMatrixInt = new int[citySet.getSize()][citySet.getSize()];

            for (int i = 0; i < citySet.getSize(); i++) {
                for (int j = 0; j < citySet.getSize(); j++) {
                    if (i != j) {
                        distMatrixInt[i][j] = distMatrix[i][j].getDistance();
                    }
                }
            }

            //printDistanceMatrix(citySet.getSize(), distMatrix);
            List<Node> nodes = new ArrayList<>();
            for (int i = 0; i < citySet.getSize(); i++) {
                Node temp = new Node(i);
                for (int j = 0; j < citySet.getSize(); j++) {
                    if (i != j)
                        temp.addDistance(j, distMatrix[i][j].getDistance());
                }
                nodes.add(temp);
            }

            NN tspSet = new NN(nodes, nodes.size());
            tspSet.setOg_root(0);
            //System.out.println(tspSet.tspNNMeh(0));
            List<Integer> path = tspSet.tspNNMeh(23);
            int sum = Utility.calculateCostByPath(path, distMatrixInt);

            int best = citySet.getBest();
            /*
            System.out.println("\nNNMeh");
            System.out.println("My cost: " + sum);
            System.out.println("Best: " + best);
            System.out.println("Error percentage: " + ((sum - best)*1.0/best)*100  + "%");
            */
            path = tspSet.tspNN(23, distMatrixInt);
            sum = Utility.calculateCostByPath(path, distMatrixInt);
            /*
            System.out.println("\nNN");
            System.out.println("My cost: " + sum);
            System.out.println("Best: " + best);
            System.out.println("Error percentage: " + ((sum - best)*1.0/best)*100  + "%");
            long end = System.nanoTime();
            System.out.println("\nTime elapsed: "+ (end-start)*1.0/1000000000 + "s");
            */
            for (int i = 0; i < citySet.getSize(); i++) {
                for (int j = 0; j < citySet.getSize(); j++) {
                    if (i != j) {
                        Arc a = new Arc(distMatrix[i][j].getDistance(), 1 / sum);
                        arcs[i][j] = a;
                        arcs[j][i] = a;
                    }
                }
            }


            TwoOpt topt = new TwoOpt(citySet.getSize(), distMatrixInt);
            path = topt.tsp2opt(path);
            sum = Utility.calculateCostByPath(path, distMatrixInt);
            /*System.out.println("\n2-opt");
            System.out.println("My cost: " + sum);
            System.out.println("Best: " + best);
            System.out.println("Error percentage: " + ((sum - best)*1.0/best)*100  + "%");
            */
            //Utility.printPath(path);

//            end = System.nanoTime();
//            System.out.println("\nTime elapsed: "+ (end-start)*1.0/1000000000 + "s");

            //Utility.printPathForExcel(path);

            /*
            ACO aco = new ACO(3, 10000, 1, 5, 0.05, 0.5, 1/(path.size() * best), 0.05 ,1/(path.size() * best), distMatrixInt, arcs, path);
            path = aco.runACO();
            sum = Utility.calculateCostByPath(path, distMatrixInt);
            System.out.println("\nAC0");
            System.out.println("My cost: " + sum);
            System.out.println("Best: " + best);
            System.out.println("Error percentage: " + ((sum - best)*1.0/best)*100  + "%");
            */

            SimulatedAnnealing sa = new SimulatedAnnealing(seed, 100, 0.97, path, distMatrixInt, best);
            path = sa.runSA(og_start);
            sum = Utility.calculateCostByPath(path, distMatrixInt);
            /*System.out.println("\nSimulated annealing");
            System.out.println("My cost: " + sum);
            System.out.println("Best: " + best);
            System.out.println("Error percentage: " + ((sum - best)*1.0/best)*100  + "%");
            */
            //Utility.printPath(path);

//            end = System.nanoTime();
//            System.out.println("\nTime elapsed: "+ (end-start)*1.0/1000000000 + "s");

            // If the file doesn't exists, create and write to it
            // If the file exists, truncate (remove all content) and write to it
                end = System.nanoTime();
                System.out.println("Time elapsed: "+ (end-start)*1.0/1000000000 + "s\n");

                try (FileWriter writer = new FileWriter(filename + ".opt.tour");
                     BufferedWriter bw = new BufferedWriter(writer)) {

                    bw.write("NAME : " + filename + ".opt.tour\n" +
                            "COMMENT : Optimum tour for " + filename + ".tsp (" + best + ")\n" +
                            "TYPE : TOUR\n" +
                            "DIMENSION : " + (path.size() - 1) + "\n" +
                            "TOUR_SECTION\n");
                    for (int i = 0; i < path.size() - 1; i++) {
                        bw.write((path.get(i) + 1) + "\n");
                    }
                    bw.write("-1\nEOF");

                } catch (IOException e) {
                    System.err.format("IOException: %s%n", e);
                }
                /*
                try (FileWriter writer = new FileWriter(filename + ".data");
                     BufferedWriter bw = new BufferedWriter(writer)) {

                    bw.write("NAME : " + filename + ".opt.tour\n" +
                            "COMMENT : Optimum tour for " + filename + ".tsp (" + best + ")\n" +
                            "RESULT : " + sum + "\n" +
                            "SEED : " + seed);

                } catch (IOException e) {
                    System.err.format("IOException: %s%n", e);
                }
                */



    }
}
