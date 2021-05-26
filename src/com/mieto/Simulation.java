package com.mieto;

import com.mieto.environment.terrain.*;
import com.mieto.environment.weather.*;
import com.mieto.tactics.*;
import com.mieto.warriors.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private BattleMap battlefield;
    private List<IterData> iterationsData = new ArrayList<IterData>();
    private SaveData saveDataObject = new SaveData();

    public void run() {

        battlefield = new BattleMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set simulation details:");
        setupTeam(1, scanner);
        setupTeam(2, scanner);
        Weather finalWeather = setupWeather(scanner);
        Terrain finalTerrain = setupTerrain(scanner);
        battlefield.setWeather(finalWeather);
        battlefield.setTerrain(finalTerrain);
        IterData.setNumberAllAliveWarriors(battlefield.teamOneWarriors.size() + battlefield.teamTwoWarriors.size());
        int deaths;
        while(battlefield.teamOneWarriors.size() != 0 || battlefield.teamTwoWarriors.size() != 0){
            deaths = 0;
            for (Warrior warrior :
                    battlefield.teamOneWarriors) {

            }




            iterationsData.add(new IterData(deaths));
        }
//todo
    }

    public void saveIterationData() {
//todo
    }

    public void saveData() {
//todo
    }

    public void setSaveDataLevel() {
//todo
    }

    public void setBattlefield(Weather weather, Terrain terrain) {
        battlefield.setWeather(weather);
        battlefield.setTerrain(terrain);
    }

    private void setupTeam(int team, Scanner scanner) {
        int nbOfWarriors = 0;
        int levelOfWarriors = 1;
        String tactic;

        System.out.println("Team " + team + ":");
        System.out.println("Number of melee warriors on horses:");
        nbOfWarriors = scanner.nextInt();
        System.out.println("Level of those warriors[1-3]:");
        levelOfWarriors = scanner.nextInt();
        if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
        for (int i = 1; i <= nbOfWarriors; i++) {
            battlefield.saveWarrior(team, new MeleeHorseWarrior(levelOfWarriors));
        }

        System.out.println("Number of melee warriors:");
        nbOfWarriors = scanner.nextInt();
        System.out.println("Level of those warriors[1-3]:");
        levelOfWarriors = scanner.nextInt();
        if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
        for (int i = 1; i <= nbOfWarriors; i++) {
            battlefield.saveWarrior(team, new MeleeWarrior(levelOfWarriors));
        }

        System.out.println("Number of range warriors on horses:");
        nbOfWarriors = scanner.nextInt();
        System.out.println("Level of those warriors[1-3]:");
        levelOfWarriors = scanner.nextInt();
        if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
        for (int i = 1; i <= nbOfWarriors; i++) {
            battlefield.saveWarrior(team, new RangeHorseWarrior(levelOfWarriors));
        }

        System.out.println("Number of range warriors:");
        nbOfWarriors = scanner.nextInt();
        System.out.println("Level of those warriors[1-3]:");
        levelOfWarriors = scanner.nextInt();
        if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
        for (int i = 1; i <= nbOfWarriors; i++) {
            battlefield.saveWarrior(team, new RangeWarrior(levelOfWarriors));
        }

        System.out.println("Choose Tactic: [TheManiple, ThePhalanx, TheSquare, TheTripleLine]");
        tactic = scanner.nextLine();
        Tactic[] tacticsArr = battlefield.getTactics();
        switch (tactic) {
            case "TheManiple":
                tacticsArr[team - 1] = new TheManipleTactic();
                break;
            case "ThePhalanx":
                tacticsArr[team - 1] = new ThePhalanxTactic();
                break;
            case "TheSquare":
                tacticsArr[team - 1] = new TheSquareTactic();
                break;
            case "TheTripleLine":
                tacticsArr[team - 1] = new TheTripleLineTactic();
                break;
            default:
                System.out.println("No tactic");
        }
        battlefield.setTactics(tacticsArr);
    }

    private Terrain setupTerrain(Scanner scanner) {
        while (true) {
            System.out.println("Choose Terrain: [Forest, Pass, Mountain, Valley]");
            String terrain = scanner.nextLine();

            switch (terrain) {
                case "Forest":
                    return new Forest();
                case "Pass":
                    return new Pass();
                case "Mountain":
                    return new Mountain();
                case "Valley":
                    return new Valley();
                default:
                    System.out.println("No tactic");
                    continue;
            }
        }
    }

    private Weather setupWeather(Scanner scanner) {
        while (true) {
            System.out.println("Choose Weather: [Cloudy, Rain, Sunny, Windy]");
            String weather = scanner.nextLine();

            switch (weather) {
                case "Cloudy":
                    return new Cloudy();
                case "Rain":
                    return new Rain();
                case "Sunny":
                    return new Sunny();
                case "Windy":
                    return new Windy();
                default:
                    System.out.println("No tactic");
                    continue;
            }
        }
    }
}
