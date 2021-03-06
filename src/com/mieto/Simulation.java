package com.mieto;

import com.mieto.environment.terrain.*;
import com.mieto.environment.weather.*;
import com.mieto.tactics.*;
import com.mieto.warriors.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simulation is the most important class in entire project.
 * In this class we concede every bigger operation.
 * @author Kamil Kałuża
 * @version 1.0
 */

public class Simulation {
    private BattleMap battlefield;
    private int[] battlefieldLines = new int[]{1, 2, 3, 4, 5};
    private List<IterData> iterationsData = new ArrayList<IterData>();
    private SaveData saveDataObject = new SaveData();

    /**
     * Run main part of the simulation.
     */
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
            if(battlefield.teamOneWarriors.size() == 0 && battlefield.teamTwoWarriors.size() == 0){
                System.out.println("You can not run simulation without warriors! Clouds are not able to fight :). Try again");
                continue;
            }
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

            List<Double> bonuses = new ArrayList<Double>();
            bonuses.add(battlefield.getMeleeBonus(1));
            bonuses.add(battlefield.getMeleeBonus(2));
            bonuses.add(battlefield.getRangeBonus(1));
            bonuses.add(battlefield.getRangeBonus(2));
            String winnerInfo = "";

            while (!battlefield.teamOneWarriors.isEmpty() && !battlefield.teamTwoWarriors.isEmpty()) {
                deaths = 0;
                double bonus;
                for (Warrior warrior :
                        battlefield.teamOneWarriors) {

                    Warrior attackedWarrior = getEnemyWarriorFromTheSameLine(warrior, battlefield.teamTwoWarriors);
                    if (attackedWarrior == null) {
                        if (warrior.battlefieldLine < battlefieldLines[4]) warrior.battlefieldLine += 1;
                    } else {
                        bonus = warrior.type.equals("Melee") ? bonuses.get(0) : bonuses.get(2);
                        warrior.attackWarrior(attackedWarrior, bonus);
                        if (!attackedWarrior.alive) {
                            battlefield.teamTwoWarriors.remove(attackedWarrior);
                            deaths++;
                            if (battlefield.teamTwoWarriors.isEmpty()) {
                                winner = 1;
                                warriorsFromTeamWinner = battlefield.teamOneWarriors;
                                winnerInfo = "Team 1 Won!";
                                break;
                            }
                        }
                    }
                }

                for (Warrior warrior :
                        battlefield.teamTwoWarriors) {
                    Warrior attackedWarrior = getEnemyWarriorFromTheSameLine(warrior, battlefield.teamOneWarriors);
                    if (attackedWarrior == null) {
                        if (warrior.battlefieldLine > battlefieldLines[0]) warrior.battlefieldLine -= 1;
                    } else {
                        bonus = warrior.type.equals("Melee") ? bonuses.get(0) : bonuses.get(2);
                        warrior.attackWarrior(attackedWarrior, bonus);
                        if (!attackedWarrior.alive) {
                            battlefield.teamOneWarriors.remove(attackedWarrior);
                            deaths++;
                            if (battlefield.teamOneWarriors.isEmpty()) {
                                winner = 2;
                                warriorsFromTeamWinner = battlefield.teamTwoWarriors;
                                winnerInfo = "Team 2 Won!";
                                break;
                            }
                        }
                    }
                }
                System.out.println(deaths + (deaths == 1 ? " Warrior died" : " Warriors died"));
                System.out.println(winnerInfo);
                iterationsData.add(new IterData(deaths));
            }

            saveData(winner, warriorsFromTeamWinner);
            scanner.nextLine();
            System.out.println("Do you want to run simulation again?: [y,n]");
            String answer = scanner.next();
            if ("n".equals(answer.trim())) {
                run = false;
            }
            iterationsData.clear();
        }
    }

    /**
     *
     * @param warrior warrior who attack another warrior
     * @param enemyTeam team of enemies
     * @return          enemy warrior from the same line
     */
    private Warrior getEnemyWarriorFromTheSameLine(Warrior warrior, List<Warrior> enemyTeam) {
        for (Warrior enemyWarrior :
                enemyTeam) {
            if (warrior.battlefieldLine == enemyWarrior.battlefieldLine) return enemyWarrior;
        }
        return null;
    }

    /**
     *
     * @param teamWinner id of winners' warriors team
     * @param warriorsFromTeamWinner list of winners' warriors
     */
    public void saveData(int teamWinner, List<Warrior> warriorsFromTeamWinner) {
        boolean resultOfSave = saveDataObject.saveData(iterationsData, teamWinner, warriorsFromTeamWinner);
        System.out.println(resultOfSave ? "Data has been saved" : "Something went wrong. Author messed up :(");
    }

    /**
     *
     * @param level level of details of saved data
     */
    public void setSaveDataLevel(int level) {
        saveDataObject.setDetailsLevel(level);
    }

    /**
     *
     * @param weather weather on the battlefield
     * @param terrain terrain on the battlefield
     */
    public void setBattlefield(Weather weather, Terrain terrain) {
        battlefield.setWeather(weather);
        battlefield.setTerrain(terrain);
    }

    /**
     *
     * @param team id of winners' warriors team
     * @param scanner scanner of System.in
     * @throws IndexOutOfBoundsException exception when user input wrong data
     */
    private void setupTeam(int team, Scanner scanner) throws IndexOutOfBoundsException {
        int nbOfWarriors = 0;
        int levelOfWarriors = 1;
        String tactic;
        while (true) {
            try {

                System.out.println("Team " + team + ":");
                System.out.println("Number of melee warriors on horses:");
                nbOfWarriors = scanner.nextInt();
                if(nbOfWarriors < 0) throw new IndexOutOfBoundsException();
                if (nbOfWarriors > 0) {
                    System.out.println("Level of those warriors[1-3]:");
                    levelOfWarriors = scanner.nextInt();
                    if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
                    for (int i = 1; i <= nbOfWarriors; i++) {
                        Warrior warrior = new MeleeHorseWarrior(levelOfWarriors);
                        warrior.team = team;
                        battlefield.saveWarrior(team, warrior);
                    }
                }

                System.out.println("Number of melee warriors:");
                nbOfWarriors = scanner.nextInt();
                if(nbOfWarriors < 0) throw new IndexOutOfBoundsException();
                if (nbOfWarriors > 0) {
                    System.out.println("Level of those warriors[1-3]:");
                    levelOfWarriors = scanner.nextInt();
                    if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
                    for (int i = 1; i <= nbOfWarriors; i++) {
                        Warrior warrior = new MeleeWarrior(levelOfWarriors);
                        warrior.team = team;
                        battlefield.saveWarrior(team, warrior);
                    }
                }

                System.out.println("Number of range warriors on horses:");
                nbOfWarriors = scanner.nextInt();
                if(nbOfWarriors < 0) throw new IndexOutOfBoundsException();
                if (nbOfWarriors > 0) {
                    System.out.println("Level of those warriors[1-3]:");
                    levelOfWarriors = scanner.nextInt();
                    if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
                    for (int i = 1; i <= nbOfWarriors; i++) {
                        Warrior warrior = new RangeHorseWarrior(levelOfWarriors);
                        warrior.team = team;
                        battlefield.saveWarrior(team, warrior);
                    }
                }

                System.out.println("Number of range warriors:");
                nbOfWarriors = scanner.nextInt();
                if(nbOfWarriors < 0) throw new IndexOutOfBoundsException();
                if (nbOfWarriors > 0) {
                    System.out.println("Level of those warriors[1-3]:");
                    levelOfWarriors = scanner.nextInt();
                    if (levelOfWarriors < 1 || levelOfWarriors > 3) levelOfWarriors = 1;
                    for (int i = 1; i <= nbOfWarriors; i++) {
                        Warrior warrior = new RangeWarrior(levelOfWarriors);
                        warrior.team = team;
                        battlefield.saveWarrior(team, warrior);
                    }
                }

                System.out.println("Choose Tactic: [Maniple, Phalanx, Square, TripleLine]");
                tactic = scanner.next();
                Tactic[] tacticsArr = battlefield.getTactics();
                switch (tactic) {
                    case "Maniple":
                        tacticsArr[team - 1] = new TheManipleTactic();
                        break;
                    case "Phalanx":
                        tacticsArr[team - 1] = new ThePhalanxTactic();
                        break;
                    case "Square":
                        tacticsArr[team - 1] = new TheSquareTactic();
                        break;
                    case "TripleLine":
                        tacticsArr[team - 1] = new TheTripleLineTactic();
                        break;
                    default:
                        System.out.println("Invalid data. Default tactic: Square");
                        tacticsArr[team - 1] = new TheSquareTactic();
                        break;
                }
                battlefield.setTactics(tacticsArr);
            } catch (Exception e) {
                System.out.println("Invalid data. Try Again");
                scanner.nextLine();
                continue;
            }
            break;
        }
    }

    /**
     *
     * @param scanner scanner of System.in
     * @return          instance of terrain class
     */
    private Terrain setupTerrain(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Choose Terrain: [Forest, Pass, Mountain, Valley]");
                scanner.reset();
                String terrain = scanner.next();

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
                        System.out.println("Invalid data. Default Terrain: Pass");
                        return new Pass();
                }
            } catch (Exception e) {
                System.out.println("Invalid data. Try Again");
            }
        }
    }

    /**
     *
     * @param scanner scanner of System.in
     * @return          instance of weather class
     */
    private Weather setupWeather(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Choose Weather: [Cloudy, Rain, Sunny, Windy]");
                scanner.reset();
                String weather = scanner.next();

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
            } catch (Exception e) {
                System.out.println("Invalid data. Try Again");
            }

        }
    }
}
