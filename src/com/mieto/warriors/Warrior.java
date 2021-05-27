package com.mieto.warriors;

import java.util.Random;

public class Warrior {
    private int level;
    public boolean alive = true;
    private double dodgePercentage;
    private double accurateAttackPercentage;
    public String type;

    public void getDamage(){
        alive = false;
    }

    public void attackWarrior(Warrior enemyWarrior, double bonus){
        boolean dodge = enemyWarrior.dodgeAttack();
        if(!dodge){
            Random rand = new Random();
            if(rand.nextDouble() < accurateAttackPercentage + bonus) enemyWarrior.getDamage();
        }
    }

    public boolean dodgeAttack(){
        Random rand = new Random();
        return rand.nextDouble() < dodgePercentage;
    }

    public void setLevel(int level){
        this.level = level;
        this.dodgePercentage = level * 0.1;
        this.accurateAttackPercentage = level * 0.1;
    }

}
