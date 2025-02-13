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

    //iron man moved but position didn't change (7, 1) --> (8, 1) and (2, 8)

    public void runOneCycle(SimulationResult simulationResult) {
        TurnResult turnResult = new TurnResult();
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
                        Position position2 = new Position(enemyFighter.getXCoordinate(), enemyFighter.getYCoordinate());
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
