package com.mieto;

import com.mieto.environment.terrain.Terrain;
import com.mieto.environment.weather.Weather;
import com.mieto.tactics.Tactic;
import com.mieto.warriors.Warrior;

import java.util.ArrayList;
import java.util.List;

public class BattleMap {
    public List<Warrior> teamOneWarriors = new ArrayList<>();
    public List<Warrior> teamTwoWarriors = new ArrayList<>();
    public Terrain terrain;
    public Weather weather;
    private Tactic[] tactics = new Tactic[2];

    public Tactic[] getTactics() {
        return tactics;
    }

    public void deleteWarrior(Warrior warrior, int team) {
        switch (team){
            case 1:
                teamOneWarriors.remove(warrior);
                break;
            case 2:
                teamTwoWarriors.remove(warrior);
                break;
            default:
                System.out.println("Error: Team number out of range [1,2]");
                break;
        }
    }

    public void saveWarrior(int team, Warrior warrior) {
        if (team == 1) {
            this.teamOneWarriors.add(warrior);
        } else {
            this.teamTwoWarriors.add(warrior);
        }
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setTactics(Tactic[] tactics) {
        this.tactics = tactics;
    }

    public double getMeleeBonus(int team){
        return terrain.meleeDifficulty + weather.meleeDifficulty + tactics[team-1].meleeDifficulty;
    }

    public double getRangeBonus(int team){
        return terrain.rangeDifficulty + weather.rangeDifficulty + tactics[team-1].rangeDifficulty;
    }
}
