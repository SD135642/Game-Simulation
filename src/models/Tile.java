package models;

import exceptions.TileNotEmptyException;
import services.Position;

import java.util.Optional;

public class Tile {
    private final boolean accessibility;
    private Optional<Fighter> fighter;
    private final Position position;

    public void setFighter(Fighter fighter) {
        if (this.fighter.isPresent()) {
            throw new TileNotEmptyException(position.getX(), position.getY());
        }
        this.fighter = Optional.of(fighter);
    }
    public void removeFighter() {
        this.fighter = Optional.empty();
    }

    public Position getPosition() {
        return position;
    }

    public Optional<Fighter> getFighter() {
        return fighter;
    }

    public Tile(boolean accessibility, Position position) {
        this.fighter = Optional.empty();
        this.accessibility = accessibility;
        this.position = position;
    }

    public boolean isAccessible() { return accessibility; }


    public boolean isEmpty() { return fighter.isEmpty(); }

    public boolean isWalkable() { return accessibility && fighter.isEmpty(); }




}
