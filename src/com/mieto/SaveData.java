package com.mieto;

public class SaveData {
    private int saveDetailsLevel = 1;

    public boolean saveData(){
        return true; //TODO: save data to file
    }

    public void setDetailsLevel(int level){
        this.saveDetailsLevel = level;
    }
}
