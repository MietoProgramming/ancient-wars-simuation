package com.mieto;

import com.mieto.warriors.Warrior;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Allow to create log from simulation.
 */
public class SaveData {
    private int saveDetailsLevel = 1;

    /**
     *
     * @param dataToSave list of data from every iteration of simulation
     * @param team id of winners' warriors team
     * @param warriors list of winners' warriors
     * @return          either saving ended successfully or not
     */
    public boolean saveData(List<IterData> dataToSave, int team, List<Warrior> warriors){
        File fileLoc = new File("result.txt");
        if(!fileLoc.exists()){
            try{
                fileLoc.createNewFile();
            }
            catch (IOException e){
                System.out.println("Exception: " + e.toString());
                return false;
            }
        }

        try{
            FileWriter fileWriter = new FileWriter(fileLoc.getAbsoluteFile(),true);
            BufferedWriter bf = new BufferedWriter(fileWriter);
            Date date = new Date();
            bf.write(date.toString() + "\n");
            int iteration = 0;
            if(saveDetailsLevel == 2){
                for (IterData data : dataToSave
                        ) {
                    iteration++;
                    bf.write(String.format("Iteration %d \n", iteration));
                    bf.write(String.format("Deaths: %s \n",data.getDeaths()));
                }
            }
            bf.write(String.format("Number of iterations %d \n", dataToSave.size()));
            int deaths = 0;
            for (IterData data :
                 dataToSave) {
                deaths += data.getDeaths();
            }
            bf.write(String.format("Total deaths: %d \n", deaths));
            bf.write(String.format("Winner: Team %d \n", team));
            bf.write(String.format("Team %d warriors at the end of simulation: %d \n", team, warriors.size()));
            bf.write(String.format("Team %d warriors at the end of simulation: 0 \n", team == 1? 2:1));
            bf.write("------------------------------------------------------------------------------");

            bf.close();
            return true;

        }
        catch (IOException e){
            System.out.println("Exception: " + e.toString());
            return false;
        }
    }

    /**
     *
     * @param level level of details of saved data
     */
    public void setDetailsLevel(int level){
        this.saveDetailsLevel = level;
    }
}
