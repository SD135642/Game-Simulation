package tests.main_action;

import main_action.Simulation;
import main_action.SimulationResult;
import models.Arena;
import models.Fighter;
import models.Team;
import models.Tile;
import org.junit.Test;
import services.PathFinder;
import services.Position;
import org.junit.Assert;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FightingTests {
    public void testFighting() {
        Arena arena = new Arena(1, 2);


        //*_|
        //*_*
        //|_*
        Tile tile1 = new Tile(true, new Position(0, 0));
        Tile tile2 = new Tile(true, new Position(1, 0));

        arena.setTile(0, 0, tile1);
        arena.setTile(0, 1, tile2);


        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 0);
        PathFinder pathFinder = new PathFinder(arena);

        Fighter fighter1 = new Fighter("hulk", 10, 10, 5, 9, 7);
        Fighter fighter2 = new Fighter("iron_man", 7, 8, 9, 7, 7);

        tile1.setFighter(fighter1);
        tile2.setFighter(fighter2);

        Team team1 = new Team("team1");
        team1.addFighter(fighter1);
        Team team2 = new Team("team2");
        team2.addFighter(fighter2);

        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);

        List<Fighter> fighters = new ArrayList<>();
        fighters.add(fighter1);
        fighters.add(fighter2);

        Simulation simulation = new Simulation(teams, arena, fighters);
        SimulationResult simulationResult = new SimulationResult(simulation);
        simulation.runOneCycle(simulationResult);


        assertEquals(fighter1.getCurrentHealth(), 5);
        assertEquals(fighter2.getCurrentHealth(), 1);

        simulation.runOneCycle(simulationResult);
        assertEquals(fighter1.getCurrentHealth(), 0);
        assertEquals(fighter2.getCurrentHealth(), 1);

    }
}
