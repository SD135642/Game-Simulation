package services;
import models.Arena;
import models.Tile;
import java.util.*;

public class PathFinder {
    Arena arena;
    private int[][] visitedNeighbors;
    private Position currentPos;
    private Position finalPos;
    private boolean notFound;

    public PathFinder(Arena arena) {
        this.arena = arena;
    }

    public Position findTopNeighbor(Position pos) {
        int height = arena.getHeight();
        int yCoor = pos.getY() + 1;
        int xCoor = pos.getX();
        if (yCoor < height) {
            Position top = new Position(xCoor, yCoor);
            return top;
        }
        return null;
    }

    public Position findLeftNeighbor(Position pos) {
        int yCoor = pos.getY();
        int xCoor = pos.getX() - 1;
        if (xCoor >= 0) {
            Position left = new Position(xCoor, yCoor);
            return left;
        }
        return null;
    }

    public Position findBottomNeighbor(Position pos) {
        int yCoor = pos.getY() - 1;
        int xCoor = pos.getX();
        if (yCoor >= 0) {
            Position bottom = new Position(xCoor, yCoor);
            return bottom;
        }
        return null;
    }
    public Position findRightNeighbor(Position pos) {
        int yCoor = pos.getY();
        int xCoor = pos.getX() + 1;
        int width = arena.getWidth();
        if (xCoor < width) {
            Position right = new Position(xCoor, yCoor);
            return right;
        }
        return null;
    }

    public List<Position> getNeighbors(Position pos) {
        ArrayList<Position> neighbors = new ArrayList<>();

        Position right = findRightNeighbor(pos);
        Position left = findLeftNeighbor(pos);
        Position bottom = findBottomNeighbor(pos);
        Position top = findTopNeighbor(pos);
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

    public void getNeighborDistances(Queue<Position> queue) {
        List<Position> neighbors = new ArrayList<>();
        neighbors = getNeighbors(currentPos);
        for (var neighbor : neighbors) {
            Tile tempTile = arena.getTile(neighbor.getX(), neighbor.getY());
            if (visitedNeighbors[neighbor.getY()][neighbor.getX()] == -1 && tempTile.isWalkable()) {
                visitedNeighbors[neighbor.getY()][neighbor.getX()] = visitedNeighbors[currentPos.getY()][currentPos.getX()] + 1;
                queue.add(neighbor);
            }
            if (neighbor.equals(finalPos)) {
                notFound = false;
                visitedNeighbors[neighbor.getY()][neighbor.getX()] = visitedNeighbors[currentPos.getY()][currentPos.getX()] + 1;
            }
        }
    }

    public void findDistancesToEachCell() {
        notFound = true;
        Queue<Position> queue = new ArrayDeque<>();
        queue.add(currentPos);
        visitedNeighbors[currentPos.getY()][currentPos.getX()] = 0;

        while (!queue.isEmpty() && notFound) {
            currentPos = queue.peek();
            queue.remove();
            getNeighborDistances(queue);
        }
    }
    
    public List<Position> findShortestPath(Position originalPos1) {
        List<Position> path = new ArrayList<>();
        path.add(finalPos);
        while (!finalPos.equals(originalPos1)) { //compares links
            int distance1 = visitedNeighbors[finalPos.getY()][finalPos.getX()];
            List<Position> neighbors = new ArrayList<>();
            neighbors = getNeighbors(finalPos);
            for (var neighbor : neighbors) {
                int distance2 = visitedNeighbors[neighbor.getY()][neighbor.getX()];
                if (distance2 == (distance1 - 1)) {
                    path.add(neighbor);
                    finalPos = new Position(neighbor.getX(), neighbor.getY());
                    break;
                }
            }
        }
        return path;
    }

    public List<Position> reversePath(List<Position> path) { 
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

    public List<Position> pathFinder(Position pos1, Position pos2) {
        this.currentPos = pos1;    //!
        this.finalPos = pos2;
        int width = arena.getWidth();
        int height = arena.getHeight();
        visitedNeighbors = new int[height][width];
        Position originalPos1 = pos1;
        for (int i = 0; i < height; i++) {
            Arrays.fill(visitedNeighbors[i], -1);
        }

        findDistancesToEachCell();
        List<Position> path = findShortestPath(originalPos1);
        return reversePath(path);
    }
}
