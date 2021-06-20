package com.mieto;

import com.mieto.environment.terrain.Terrain;
import com.mieto.environment.weather.Weather;
import com.mieto.tactics.Tactic;
import com.mieto.warriors.Warrior;

import java.util.ArrayList;
import java.util.List;

/**
 * Map of battle which contains:
 * <li>2 teams of warriors</li>
 * <li>weather</li>
 * <li>terrain</li>
 * <li>tactics</li>
 */
public class BattleMap {
    public List<Warrior> teamOneWarriors = new ArrayList<>();
    public List<Warrior> teamTwoWarriors = new ArrayList<>();
    public Terrain terrain;
    public Weather weather;
    private Tactic[] tactics = new Tactic[2];

    /**
     *
     * @return          return tactics
     */
    public Tactic[] getTactics() {
        return tactics;
    }

    /**
     *
     * @param warrior warrior
     * @param team warrior's team
     */
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

    /**
     *
     * @param team warrior's team
     * @param warrior warrior
     */
    public void saveWarrior(int team, Warrior warrior) {
        if (team == 1) {
            this.teamOneWarriors.add(warrior);
        } else {
            this.teamTwoWarriors.add(warrior);
        }
    }

    /**
     *
     * @param terrain chose terrain
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     *
     * @param weather chose weather
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    /**
     *
     * @param tactics chose tactics
     */
    public void setTactics(Tactic[] tactics) {
        this.tactics = tactics;
    }

    /**
     *
     * @param team warrior's team
     * @return      return bonus for melee warrior
     */
    public double getMeleeBonus(int team){
        return terrain.meleeDifficulty + weather.meleeDifficulty + tactics[team-1].meleeDifficulty;
    }

    /**
     *
     * @param team warrior's team
     * @return      return bonus for range warrior
     */
    public double getRangeBonus(int team){
        return terrain.rangeDifficulty + weather.rangeDifficulty + tactics[team-1].rangeDifficulty;
    }
}
