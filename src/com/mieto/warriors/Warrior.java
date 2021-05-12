package com.mieto.warriors;

public class Warrior {
    private int level;
    public boolean alive = true;
    private double dodgePercentage;
    private boolean canAttack = false;
    public int team;

    public boolean getDamage(){
        return true; //TODO
    }

    public boolean attackWarrior(Warrior enemyWarrior){
        return true; //TODO
    }

    public boolean dodgeAttack(){
        return false;//TODO
    }

    public void setLevel(int level){
        this.level = level;
    }

}
