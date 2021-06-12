package com.mieto;

import com.mieto.environment.terrain.Terrain;
import com.mieto.environment.terrain.Valley;
import com.mieto.environment.weather.Rain;
import com.mieto.environment.weather.Weather;
import com.mieto.tactics.Tactic;
import com.mieto.tactics.ThePhalanxTactic;
import com.mieto.tactics.TheSquareTactic;
import com.mieto.warriors.MeleeWarrior;
import com.mieto.warriors.Warrior;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleMapTest {

    @Test
    void saveWarrior() {
        BattleMap battlemap = new BattleMap();
        Warrior warrior = new MeleeWarrior(1);
        battlemap.saveWarrior(1, warrior);
        List<Warrior> warriors = new ArrayList<>();
        warriors.add(warrior);
        assertEquals(battlemap.teamOneWarriors, warriors);
        assertEquals(battlemap.teamTwoWarriors, new ArrayList<>());
    }

    @Test
    void setTactics() {
        BattleMap battlemap = new BattleMap();
        Tactic[] tactics = new Tactic[]{new TheSquareTactic(), new ThePhalanxTactic()};
        battlemap.setTactics(tactics);
        assertEquals(battlemap.getTactics(), tactics);
    }

    @Test
    void getMeleeBonus() {
        BattleMap battlemap = new BattleMap();
        Tactic[] tactics = new Tactic[]{new TheSquareTactic(), new ThePhalanxTactic()};
        Weather weather = new Rain();
        Terrain terrain = new Valley();

        battlemap.setTactics(tactics);
        battlemap.setTerrain(terrain);
        battlemap.setWeather(weather);
        double predictedResult = tactics[0].meleeDifficulty + weather.meleeDifficulty + terrain.meleeDifficulty;

        assertEquals(battlemap.getMeleeBonus(1), predictedResult);
    }

    @Test
    void getRangeBonus() {
        BattleMap battlemap = new BattleMap();
        Tactic[] tactics = new Tactic[]{new TheSquareTactic(), new ThePhalanxTactic()};
        Weather weather = new Rain();
        Terrain terrain = new Valley();

        battlemap.setTactics(tactics);
        battlemap.setTerrain(terrain);
        battlemap.setWeather(weather);
        double predictedResult = tactics[1].rangeDifficulty + weather.rangeDifficulty + terrain.rangeDifficulty;

        assertEquals(battlemap.getRangeBonus(2), predictedResult);
    }
}