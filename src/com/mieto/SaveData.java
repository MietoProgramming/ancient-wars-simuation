package com.mieto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class SaveData {
    private int saveDetailsLevel = 1;

    public boolean saveData(){
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
            Date date = new Date();//TODO: depands from details level
            bf.write(date.toString());
            bf.write("Somiething");
            bf.write("Somiething");
            bf.write("Somiething");
            bf.write("Somiething");

            bf.close();
            return true;

        }
        catch (IOException e){
            System.out.println("Exception: " + e.toString());
            return false;
        }
    }

    public void setDetailsLevel(int level){
        this.saveDetailsLevel = level;
    }
}
