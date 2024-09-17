package services;


import java.util.Objects;

public class Position {
    int xCoor;
    int yCoor;

    public Position(int xCoor, int yCoor) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public int getX() {
        return xCoor;
    }

    public int getY() {
        return yCoor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return xCoor == position.xCoor && yCoor == position.yCoor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoor, yCoor);
    }

    @Override
    public String toString() {
        return "[" + xCoor + yCoor + "]";
    }

}
