package services;
import main_action.Simulation;
import models.Arena;
import models.Tile;
import java.util.*;

public class PathFinder {
    Arena arena;

    public PathFinder(Arena arena) {
        this.arena = arena;
    }

    public Position findTopNeighbor(Position pos1) {
        int height = arena.getHeight();
        int yCoor = pos1.getY() + 1;
        int xCoor = pos1.getX();
        if (yCoor < height) {
            Tile tile = arena.getTile(xCoor, yCoor);
            Position top = new Position(xCoor, yCoor);
            return top;
        }
        return null;
    }

    public Position findLeftNeighbor(Position pos1) {
        int yCoor = pos1.getY();
        int xCoor = pos1.getX() - 1;
        if (xCoor >= 0) {
            Tile tile = arena.getTile(xCoor, yCoor);
            Position left = new Position(xCoor, yCoor);
            return left;
        }
        return null;
    }

    public Position findBottomNeighbor(Position pos1) {
        int yCoor = pos1.getY() - 1;
        int xCoor = pos1.getX();
        if (yCoor >= 0) {
            Tile tile = arena.getTile(xCoor, yCoor);
            Position bottom = new Position(xCoor, yCoor);
            return bottom;
        }
        return null;
    }
    public Position findRightNeighbor(Position pos1) {
        int yCoor = pos1.getY();
        int xCoor = pos1.getX() + 1;
        int width = arena.getWidth();
        if (xCoor < width) {
            Tile tile = arena.getTile(xCoor, yCoor);
            Position right = new Position(xCoor, yCoor);
            return right;
        }
        return null;
    }

    public List<Position> getNeighbors(Position pos1) {
        ArrayList<Position> neighbors = new ArrayList<>();

        Position right = findRightNeighbor(pos1);
        Position left = findLeftNeighbor(pos1);
        Position bottom = findBottomNeighbor(pos1);
        Position top = findTopNeighbor(pos1);
        if (top != null) {
            neighbors.add(top);
        }
        if (right != null) {
            neighbors.add(right);
        }
        if (bottom != null) {
            neighbors.add(bottom);
        }
        if (left != null) {
            neighbors.add(left);
        }
        return neighbors;
    }

    public List<Position> pathFinder(Position pos1, Position pos2) {
        int width = arena.getWidth();
        int height = arena.getHeight();
        int[][] visitedNeighbors = new int[height][width];
        Position originalPos1 = pos1;
        for (int i = 0; i < height; i++) {
            Arrays.fill(visitedNeighbors[i], -1);
        }

        Queue<Position> queue = new ArrayDeque<>();
        boolean notFound = true;
        queue.add(pos1);
        visitedNeighbors[pos1.getY()][pos1.getX()] = 0;

        while (!queue.isEmpty() && notFound) {
            pos1 = queue.peek();
            queue.remove();
            List<Position> neighbors = new ArrayList<>();
            neighbors = getNeighbors(pos1);
            for (var neighbor : neighbors) {
                Tile tempTile = arena.getTile(neighbor.getX(), neighbor.getY());
                if (visitedNeighbors[neighbor.getY()][neighbor.getX()] == -1 && tempTile.isWalkable()) {
                    visitedNeighbors[neighbor.getY()][neighbor.getX()] = visitedNeighbors[pos1.getY()][pos1.getX()] + 1;
                    queue.add(neighbor);
                }
                if (neighbor.equals(pos2)) {
                    notFound = false;
                    visitedNeighbors[neighbor.getY()][neighbor.getX()] = visitedNeighbors[pos1.getY()][pos1.getX()] + 1;
                }
            }
        }
        List<Position> path = new ArrayList<>();
        path.add(pos2);
        while (!pos2.equals(originalPos1)) { //compares links
            int distance1 = visitedNeighbors[pos2.getY()][pos2.getX()];
            List<Position> neighbors = new ArrayList<>();
            neighbors = getNeighbors(pos2);
            for (var neighbor : neighbors) {
                int distance2 = visitedNeighbors[neighbor.getY()][neighbor.getX()];
                if (distance2 == (distance1 - 1)) {
                    path.add(neighbor);
                    pos2 = new Position(neighbor.getX(), neighbor.getY());
                    break;
                }
            }
        }
        int j = 0;
        int i = path.size() - 1;
        while (j < i) {
            Position posTemp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, posTemp);
            j++;
            i--;
        }
        return path;
    }
}
