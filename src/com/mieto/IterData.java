package com.mieto;

public class IterData {
    private int deaths = 0;
    public static int numberAllAliveWarriors = 0;


    public IterData(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public static void setNumberAllAliveWarriors(int numberAllAliveWarriors) {
        IterData.numberAllAliveWarriors = numberAllAliveWarriors;
    }
}
