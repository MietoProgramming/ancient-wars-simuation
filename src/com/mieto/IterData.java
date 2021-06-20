package com.mieto;

/**
 * Class of a single iteration data object.
 */
public class IterData {
    private int deaths = 0;
    public static int numberAllAliveWarriors = 0;

    /**
     *
     * @param deaths deaths in the current iteration
     */
    public IterData(int deaths) {
        this.deaths = deaths;
    }

    /**
     *
     * @return return deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     *
     * @param numberAllAliveWarriors return number of alive warriors
     */
    public static void setNumberAllAliveWarriors(int numberAllAliveWarriors) {
        IterData.numberAllAliveWarriors = numberAllAliveWarriors;
    }
}
