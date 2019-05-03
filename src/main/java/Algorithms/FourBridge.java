package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FourBridge {
    Random random;

    public FourBridge(long seed) {
        random = new Random(seed);
    }

    public List<Integer> fourBridgeSwap(List<Integer> path){
        List<Integer> indices = new ArrayList<>();
        List<Integer> new_path = new ArrayList<>();

        while(indices.size() != 4){
            int a = random.nextInt(path.size()-1) + 1;
            if(!indices.contains(a)){
                indices.add(a);
                //System.out.println("add idx "+ a);
            }
        }
        Collections.sort(indices);
        /*System.out.println("Indici");
        for (int i = 0; i < 4; i++) {
            System.out.print(indices.get(i) + "\t");
        }*/
        for (int i = 1; i < indices.size(); i++) {
            if(indices.get(i) == indices.get(i-1))
                indices.set(i, indices.get(i)+1);
        }
        /*
        for (int idx : indices){
            System.out.println(idx);
        }*/

        for (int i = 0; i <= indices.get(0); i++) {
            new_path.add(path.get(i));
        }

        for (int i = indices.get(2)+1; i <= indices.get(3); i++) {
            new_path.add(path.get(i));
        }

        for (int i = indices.get(1)+1; i <= indices.get(2); i++) {
            new_path.add(path.get(i));
        }

        for (int i = indices.get(0)+1; i <= indices.get(1); i++) {
            new_path.add(path.get(i));
        }

        for (int i = indices.get(3)+1; i <= path.size()-1; i++) {
            new_path.add(path.get(i));
        }
        return new_path;
    }
}
