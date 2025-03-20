package main_action;

import models.Fighter;
import services.Position;

public class FighterResult {
    private int damage;
    private int enemyHpAfter;
    private Fighter fighter;
    private Fighter attackTarget;
    private Position from;
    private Position to;
    private int startHp;
    

    public FighterResult(int startHp) {
        this.startHp = startHp;
    }

    public int getStartHp() {
        return startHp;
    }

    public void setDamage(int val) {
        damage = val;
    }
    public void setEnemyHpAfter(int val) {
        enemyHpAfter = val;
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

    public int getDamage() {
        return damage;
    }
    public int getEnemyHpAfter() {
        return enemyHpAfter;
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
    public String toString() { // add diff lines, don't print extra info if they aren't fighting
        return "Fighter: " + fighter +
                ", went from " + from +
                " to " + to +
                " to hit " + attackTarget +
                " and inflict " + damage +
                " damage. The enemy had " + enemyHpAfter +
                " after " + fighter + "'s attack";
                
    }
}
