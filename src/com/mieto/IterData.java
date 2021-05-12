package com.mieto;

import com.mieto.warriors.Warrior;

import java.util.Hashtable;

public class IterData {
    public int deaths = 0;
    public int numberAllAliveWarriors = 0;
    public Hashtable<Warrior,int> numberAliveWarriors = new Hashtable<Warrior, int>();


    public IterData(int deaths, Warrior[] warriors) {
    }
}
