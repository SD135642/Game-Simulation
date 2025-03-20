package main_action;

import models.Arena;
import models.Fighter;
import models.Team;
import services.PathFinder;
import services.Position;
import java.util.List;

public class Simulation {

    private List<Team> teams;
    private Arena arena;
    private List<Fighter> fighters;
    private SimulationResult simulationResult;
    private TurnResult turnResult;
    private FighterResult fighterResult;

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
        simulationResult = new SimulationResult(this);
        while (canContinue()) {
            runOneCycle();
        }
        return simulationResult;
    }

    public Fighter selectEnemy(Fighter fighter) {
        Fighter nearestEnemy = null;
        int minPathLength = Integer.MAX_VALUE;
        for (var enemyFighter : fighters) {
            if (enemyFighter.getTeamName() != fighter.getTeamName() && enemyFighter.isAlive()) {
                var path = buildPath(fighter, enemyFighter);
                if (path.isEmpty() || path.size() < minPathLength) {
                    minPathLength = path.size();
                    nearestEnemy = enemyFighter;
                }
            }
        }
        fighterResult.setEnemyFighter(nearestEnemy);
        return nearestEnemy;
    }

    public List<Position> buildPath(Fighter fighter, Fighter enemyFighter) {
        Position pos1 = new Position(fighter.getXCoordinate(), fighter.getYCoordinate());
        Position pos2 = new Position(enemyFighter.getXCoordinate(), enemyFighter.getYCoordinate());
        PathFinder pathFinder = new PathFinder(arena);
        return pathFinder.pathFinder(pos1, pos2);  // return path
    }

    public void tryMoveToEnemy(Fighter fighter, List<Position> path) {
        if (path.size() == 2) {
            return;
        }
        var position = path.get(1);
        fighter.setXCoordinate(position.getX());
        fighter.setYCoordinate(position.getY());
        fighterResult.setCurrPosition(new Position(fighter.getXCoordinate(), fighter.getYCoordinate()));
    }

    public void tryAttackEnemy(Fighter fighter, Fighter enemyFighter) {

        if (fighter.getXCoordinate()-1 != enemyFighter.getXCoordinate() && 
            fighter.getXCoordinate()+1 != enemyFighter.getXCoordinate() &&
            fighter.getYCoordinate()-1 != enemyFighter.getYCoordinate() &&
            fighter.getYCoordinate()+1 != enemyFighter.getYCoordinate()) {
            return;
        }
        int enemyHealth = enemyFighter.getCurrentHealth();
        fighter.fight(enemyFighter);
        fighterResult.setDamage(enemyHealth - enemyFighter.getCurrentHealth());
        fighterResult.setEnemyHpAfter(enemyFighter.getCurrentHealth());
    }

    private void fighterTurn(Fighter fighter) {
        fighterResult = new FighterResult(fighter.getCurrentHealth());
        fighterResult.setFighter(fighter);
        fighterResult.setPrevPosition(new Position(fighter.getXCoordinate(), fighter.getYCoordinate()));

        if (fighter.isAlive()) {
            var enemy = selectEnemy(fighter);
            var path = buildPath(fighter, enemy);
            tryMoveToEnemy(fighter, path);
            tryAttackEnemy(fighter, enemy);
        } 
    }

    public void runOneCycle() {
        turnResult = new TurnResult();
        for (int j = 0; j < fighters.size(); j++) {
            fighterTurn(fighters.get(j));
            turnResult.addFighterResult(fighterResult);
        }
        
        simulationResult.addTurnResults(turnResult);
    
    }
}
