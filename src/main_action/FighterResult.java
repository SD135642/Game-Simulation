package main_action;

import models.Fighter;
import services.Position;

public class FighterResult {
    private int hpBefore;
    private int hpAfter;
    private Fighter fighter;
    private Fighter attackTarget;
    private Position from;
    private Position to;

    public void setHpBefore(int val) {
        hpBefore = val;
    }
    public void setHpAfter(int val) {
        hpAfter = val;
    }
    public void setFighter(Fighter fighter) {
        this.fighter = fighter;
    }
    public void setEnemyFighter(Fighter fighter) {
        attackTarget = fighter;
    }
    public void setPrevPosition(Position pos) {
        from = pos;
    }
    public void setCurrPosition(Position pos) {
        to = pos;
    }

    public int getHpBefore() {
        return hpBefore;
    }
    public int getHpAfter() {
        return hpAfter;
    }
    public Fighter getFighter() {
        return fighter;
    }
    public Fighter getEnemyFighter() {
        return attackTarget;
    }
    public Position getPrevPosition() {
        return from;
    }
    public Position getCurrPosition() {
        return to;
    }

    @Override
    public String toString() {
        return "Fighter: " + fighter +
                ", HP before: " + hpBefore +
                ", HP after: " + hpAfter +
                ", attack target: " + attackTarget +
                ", previous position: " + from +
                ", current position: " + to;
    }
}
