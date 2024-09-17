package tests.services;

import models.Arena;
import models.Fighter;
import models.Tile;
import org.junit.Test;
import services.PathFinder;
import services.Position;
import org.junit.Assert;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PathFinderTests {
    @Test
    public void PathFinder_findPathBetweenTwo() {
        Arena arena = new Arena(3, 3);

        //*__
        //*_*
        //||*
        Fighter fighter1 = new Fighter("Hulk", 10, 9, 8, 7, 6);
        Fighter fighter2 = new Fighter("IronMan", 8, 7, 6, 5, 4);
        fighter1.setXCoordinate(1);
        fighter1.setYCoordinate(2);
        fighter2.setXCoordinate(0);
        fighter2.setYCoordinate(2);

        arena.setTile(0, 0, new Tile(false, new Position(0, 0)));
        arena.setTile(0, 1, new Tile(true, new Position (1, 0)));
        arena.setTile(0, 2, new Tile(true, new Position(2, 0)));
        arena.getTile(2, 0).setFighter(fighter2);

        arena.setTile(1, 0, new Tile (false, new Position(0, 1)));
        arena.setTile(1, 1, new Tile (true, new Position(1, 1)));
        arena.setTile(1, 2, new Tile (false, new Position(2, 1)));

        arena.setTile(2, 0, new Tile (false, new Position(0, 2)));
        arena.setTile(2, 1, new Tile (false, new Position(1, 2)));
        arena.getTile(1, 2).setFighter(fighter1);
        arena.setTile(2, 2, new Tile (false, new Position(2, 2)));


        Position pos1 = new Position(2, 1);
        Position pos2 = new Position(2, 0);
        PathFinder pathFinder = new PathFinder(arena);
        List<Position> positions = pathFinder.pathFinder(pos1, pos2);

        List<Position> correct_positions = new ArrayList<>();
        Position pos3 = new Position(2, 1);
        Position pos4 = new Position(2, 0);
        correct_positions.add(pos3);
        correct_positions.add(pos4);

        assertEquals(positions, correct_positions);
    }
}
