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
        boolean run = true;
        while (run) {


            int winner = 0;
            int deaths;
            List<Warrior> warriorsFromTeamWinner = new ArrayList<>();

            battlefield = new BattleMap();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Set simulation details:");
            setupTeam(1, scanner);
            setupTeam(2, scanner);
            Weather finalWeather = setupWeather(scanner);
            Terrain finalTerrain = setupTerrain(scanner);
            setBattlefield(finalWeather, finalTerrain);
            IterData.setNumberAllAliveWarriors(battlefield.teamOneWarriors.size() + battlefield.teamTwoWarriors.size());
            System.out.println("Level of backup details: [1 or 2]:");
            int saveDataDetilsLevel = scanner.nextInt();
            if (saveDataDetilsLevel == 2) {
                setSaveDataLevel(saveDataDetilsLevel);
            } else {
                setSaveDataLevel(1);
            }

            List<double> bonuses = new ArrayList<double>();
            bonuses.add(battlefield.getMeleeBonus(1));
            bonuses.add(battlefield.getMeleeBonus(2));
            bonuses.add(battlefield.getRangeBonus(1));
            bonuses.add(battlefield.getRangeBonus(2));
            while (!battlefield.teamOneWarriors.isEmpty() || !battlefield.teamTwoWarriors.isEmpty()) {
                deaths = 0;
                double bonus;
                for (Warrior warrior :
                        battlefield.teamOneWarriors) {
                    Warrior attackedWarrior = battlefield.teamTwoWarriors.get(0);
                    bonus = warrior.type == "Melee" ? bonuses.get(0) : bonuses.get(2);
                    warrior.attackWarrior(attackedWarrior, bonus);
                    if (!attackedWarrior.alive) {
                        battlefield.teamTwoWarriors.remove(attackedWarrior);
                        deaths++;
                        if (battlefield.teamTwoWarriors.isEmpty()) {
                            winner = 1;
                            warriorsFromTeamWinner = battlefield.teamOneWarriors;
                            System.out.println("Team 1 Won!");
                            break;
                        }
                    }
                }

                for (Warrior warrior :
                        battlefield.teamTwoWarriors) {
                    Warrior attackedWarrior = battlefield.teamOneWarriors.get(0);
                    bonus = warrior.type == "Melee" ? bonuses.get(0) : bonuses.get(2);
                    warrior.attackWarrior(attackedWarrior, bonus);
                    if (!attackedWarrior.alive) {
                        battlefield.teamOneWarriors.remove(attackedWarrior);
                        deaths++;
                        if (battlefield.teamOneWarriors.isEmpty()) {
                            winner = 2;
                            warriorsFromTeamWinner = battlefield.teamTwoWarriors;
                            System.out.println("Team 2 Won!");
                            break;
                        }
                    }
                }

                iterationsData.add(new IterData(deaths));
            }

            saveData(winner, warriorsFromTeamWinner);
            System.out.println("Do you want to run simulation again?: [y,n]");
            String answer = scanner.nextLine();
            if ("n".equals(answer.trim())) {
                run = false;
            }
        }
    }

    public void saveData(int teamWinner, List<Warrior> warriorsFromTeamWinner) {
        boolean resultOfSave = saveDataObject.saveData(iterationsData, teamWinner, warriorsFromTeamWinner);
        System.out.println(resultOfSave ? "Data has been saved" : "Something went wrong. Author messed up :(");
    }

    public void setSaveDataLevel(int level) {
        saveDataObject.setDetailsLevel(level);
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
                System.out.println("Default tactic: Square");
                tacticsArr[team - 1] = new TheSquareTactic();
                break;
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
                    System.out.println("Default Terrain: Pass");
                    return new Pass();
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
                    System.out.println("Default Weather: Cloudy");
                    return new Cloudy();
            }
        }
    }
}
