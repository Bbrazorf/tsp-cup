package Structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {
    private String FILENAME;

    public Loader(String filename) {
        this.FILENAME = filename+".tsp";
    }

    public void setFILENAME(String filename) {
        this.FILENAME = filename +".tsp";
    }

    public CitySet getCitySet() {
        CitySet set = null;
        BufferedReader br = null;
        FileReader fr = null;
        System.out.println("Reading "+ FILENAME + "...");
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(FILENAME).getFile());
            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;
            int current = 0;
            boolean coords = false;

            while ((sCurrentLine = br.readLine()) != null && !sCurrentLine.equals("EOF")) {
                sCurrentLine = sCurrentLine.trim();
                if(coords){
                    String[] split;
                    split = sCurrentLine.split(" ");
                    set.addCoordinate(new Coordinate(Double.parseDouble(split[1]), Double.parseDouble(split[2])), Integer.parseInt(split[0])-1);
                }

                if(sCurrentLine.contains("DIMENSION :"))
                    set = new CitySet(Integer.parseInt(sCurrentLine.substring(12)));
                if(sCurrentLine.contains("DIMENSION:"))
                    set = new CitySet(Integer.parseInt(sCurrentLine.substring(11)));
                if(sCurrentLine.contains("NODE_COORD_SECTION")){
                    coords = !coords;
                    //System.out.println("Reading nodes...");
                }
                if(sCurrentLine.contains("BEST_KNOWN : ")){
                    if(set != null){
                        set.setBest(Integer.parseInt(sCurrentLine.substring(13)));
                        //System.out.println("Best known:" + set.getBest());
                    }
                }

                //System.out.println(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return set;
    }


}
