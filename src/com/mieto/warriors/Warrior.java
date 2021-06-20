package com.mieto.warriors;

import java.util.Random;

/**
 * Warrior class with statistics and actions.
 */
public class Warrior {
    private int level;
    public boolean alive = true;
    private double dodgePercentage;
    private double accurateAttackPercentage;
    public String type;
    public int team;
    public int battlefieldLine;

    /**
     *
     * @param type type of warrior ["Melee" or "Range"]
     * @param level level of warrior
     */
    public Warrior(String type, int level) {
        setLevel(level);
        this.type = type;
    }

    /**
     * Set warrior as dead.
     */
    private void getDamage(){
        alive = false;
    }

    /**
     *
     * @param enemyWarrior enemy warrior
     * @param bonus bonus of attacking warrior
     */
    public void attackWarrior(Warrior enemyWarrior, double bonus){
        boolean dodge = enemyWarrior.dodgeAttack();
        if(!dodge){
            Random rand = new Random();
            if(rand.nextDouble() < accurateAttackPercentage + bonus) enemyWarrior.getDamage();
        }
    }

    /**
     *
     * @return  either warrior dodged or not
     */
    private boolean dodgeAttack(){
        Random rand = new Random();
        return rand.nextDouble() < dodgePercentage;
    }

    /**
     *
     * @param level level of warrior
     */
    private void setLevel(int level){
        this.level = level;
        this.dodgePercentage = level * 0.3;
        this.accurateAttackPercentage = level * level * 0.05;
    }

}
