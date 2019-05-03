package Algorithms;

import Utility.Utility;

import javax.rmi.CORBA.Util;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private List<Integer> initial;
    private int iterations;
    private double alpha;
    private int[][] distanceMatrix;
    private Random r;
    private FourBridge fourBridge;
    private DoubleBridge doubleBridge;
    private int optimal;

    public SimulatedAnnealing(long seed, int iterations, double alpha, List<Integer> initial, int[][] distanceMatrix, int optimal) {
        this.iterations = iterations;
        this.initial = initial;
        this.alpha = alpha;
        this.distanceMatrix = distanceMatrix;
        r = new Random(seed);
        fourBridge = new FourBridge(seed);
        doubleBridge = new DoubleBridge(seed);
        this.optimal = optimal;
    }

    public List<Integer> runSA(long starting_time){
        double t = 100.0;
        List<Integer> current = initial;
        List<Integer> best = current;
        TwoOpt twoOpt = new TwoOpt(initial.size(), distanceMatrix);

        double s = System.currentTimeMillis();
        //while((System.currentTimeMillis()- s)/1000 < 60){
        while(t > 0.00000000000000000001 && (System.currentTimeMillis()-starting_time)/1000  <= 160){
            for(int i = 0; i < iterations; i++){
                //System.out.println("T: " + t +"\t Iteration: "+ i);
                List<Integer> next = twoOpt.tsp2opt(doubleBridge.doubleBridgeSwap(current));
                int nex = Utility.calculateCostByPath(next, distanceMatrix);
                int curr = Utility.calculateCostByPath(current, distanceMatrix);
                int gain = nex - curr;
                if(gain < 0 && nex >= optimal){
                    //System.out.println("Next: "+ nex + "\nCurrent: "+curr + "\nGain: "+gain+"\n");
                    //Utility.printPath(next);
                    current = next;
                    if(Utility.gainBetweenPaths(next, best, distanceMatrix) < 0){
                        best = next;
                    }
                }else{
                    if(r.nextDouble() < Math.exp(-gain/t)){
                        //System.out.println("four bridge");
                        current = next;
                    }
                }
            }
            t *= alpha;
        }
        //System.out.println("Annealing duration: "+ (System.currentTimeMillis()- s)/1000);
        return best;
    }
}
