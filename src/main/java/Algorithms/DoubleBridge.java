package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DoubleBridge {
    Random random;

    public DoubleBridge(long seed) {
        random = new Random(seed);
    }

    public List<Integer> doubleBridgeSwap(List<Integer> path) {
        List<Integer> indices = new ArrayList<>();
        int[] new_path = new int[path.size()];

        while (indices.size() != 4) {
            int a = random.nextInt(path.size() - 1);
            if (!indices.contains(a)) {
                indices.add(a);
                //System.out.println("add idx "+ a);
            }
        }
        Collections.sort(indices);

        int cont = 0;
        new_path[cont] = path.get(indices.get(0));
        for (int i = indices.get(2) +1 ; i <= indices.get(3) ; i++) {
            new_path[cont] = path.get(i);
            cont++;
        }

        for (int i = indices.get(1) +1 ; i <= indices.get(2) ; i++) {
            new_path[cont] = path.get(i);
            cont++;
        }
        for (int i = indices.get(0) +1 ; i <= indices.get(1) ; i++) {
            new_path[cont] = path.get(i);
            cont++;
        }
        for (int i = indices.get(3) +1 ; i < path.size()-1 ; i++) {
            new_path[cont] = path.get(i);
            cont++;
        }
        for (int i = 0; i < indices.get(0) ; i++) {
            new_path[cont] = path.get(i);
            cont++;
        }
        new_path[cont++] = path.get(indices.get(0));
        List<Integer> scrambled = new ArrayList<>();
        for (int i = 0; i < new_path.length; i++) {
            scrambled.add(new_path[i]);
        }
        return scrambled;
    }
}
