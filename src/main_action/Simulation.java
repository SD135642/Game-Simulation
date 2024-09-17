package main_action;

import models.Arena;
import models.Fighter;
import models.Team;
import models.Tile;
import services.PathFinder;
import services.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private List<Team> teams;
    private Arena arena;
    private List<Fighter> fighters;

    public Simulation(List<Team> teams, Arena arena, List<Fighter> fighters) {
        this.teams = teams;
        this.arena = arena;
        this.fighters = fighters;

    }

    public List<Team> getTeams() {
        return teams;
    }
    public Arena getArena() {
        return arena;
    }
    public List<Fighter> getFighters() {
        return fighters;
    }

    public void randomPlacement(Arena arena, List<Fighter> fighters) {
        int width = arena.getWidth();
        int height = arena.getHeight();
        ArrayList<ArrayList> emptyTilesCoordinates = new ArrayList<>();
        Random generator = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = arena.getTile(x, y);
                if (tile.isAccessible()) {
                    ArrayList<Integer> coordinates = new ArrayList<>();
                    coordinates.add(x);
                    coordinates.add(y);
                    emptyTilesCoordinates.add(coordinates);
                }
            }
        }
        for (int i = 0; i < fighters.size(); i++) {
            Fighter fighter = fighters.get(i);
            ArrayList<Integer> coordinates = new ArrayList<>();
            int random = generator.nextInt(0, emptyTilesCoordinates.size());
            coordinates = emptyTilesCoordinates.get(random);
            int x = coordinates.get(0);
            int y = coordinates.get(1);
            fighter.setXCoordinate(x);
            fighter.setYCoordinate(y);
            emptyTilesCoordinates.remove(random);
            arena.getTile(x, y).setFighter(fighter);
        }
    }

    public boolean canContinue() {
        boolean cont = true;
        int aliveTeams = teams.size();
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).allFightersDead()) {
                aliveTeams--;
            }
        }
        return aliveTeams > 1;
    }

    public boolean figherPresent(int xCoor, int yCoor) {
        for (var fighter : fighters) {
            return (fighter.getXCoordinate() == xCoor && fighter.getYCoordinate() == yCoor);
        }
        return false;
    }

    public SimulationResult run() {
        SimulationResult simulationResult = new SimulationResult(this);
        while (canContinue()) {
            runOneCycle(simulationResult);
        }
        return simulationResult;
    }

    public void runOneCycle(SimulationResult simulationResult) {
        TurnResult turnResult = new TurnResult();
        randomPlacement(arena, fighters);  //change to non-random
        for (int j = 0; j < fighters.size(); j++) {
            Fighter fighter = fighters.get(j);
            List<Position> path = new ArrayList<>();
            PathFinder pathFinder = new PathFinder(arena);
            if (fighter.isAlive()) {
                FighterResult fighterResult = new FighterResult();
                fighterResult.setFighter(fighter); ///!!!
                fighterResult.setHpBefore(fighter.getCurrentHealth()); ///!!!
                int xCoor = fighter.getXCoordinate();
                int yCoor = fighter.getYCoordinate();
                fighterResult.setPrevPosition(new Position(xCoor, yCoor)); ///!!!
                for (var enemyFighter : fighters) {
                    if (enemyFighter.getTeamName() != fighter.getTeamName()) {
                        fighterResult.setEnemyFighter(enemyFighter); ///!!!
                        Position position1 = new Position(fighter.getXCoordinate(), fighter.getYCoordinate());
                        Position position2 = new Position(enemyFighter.getXCoordinate(), fighter.getYCoordinate());
                        List<Position> newPath = pathFinder.pathFinder(position1, position2);
                        if (path.isEmpty() || newPath.size() < path.size()) {
                            path = newPath;
                        }
                    }
                }
                // fighterResult -> fighter
                // currHp -> hpAfter
                Tile currTile = arena.getTile(xCoor, yCoor);
                Position positionNext = path.get(1);
                int x = positionNext.getX();
                int y = positionNext.getY();
                fighterResult.setCurrPosition(new Position(x, y)); ///!!!
                Tile nextTile = arena.getTile(x, y);
                nextTile.setFighter(fighter);
                currTile.removeFighter();

                if (path.size() == 2) {
                    fighter.fight(this, path.get(1));
                }
                turnResult.addFighterResult(fighterResult);
            }

        }
        for (int i = 0; i < turnResult.getNumResults(); i++) {
            turnResult.getFighterResult(i).setHpAfter(turnResult.getFighterResult(i).getFighter().getCurrentHealth());
        }
        simulationResult.addTurnResults(turnResult);
    }
}
