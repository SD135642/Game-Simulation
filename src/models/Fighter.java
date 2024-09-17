package models;

import main_action.Simulation;
import services.Position;

public class Fighter {
    private final String name;
    private int currHp;
    private final int maxHp;   //cannot be modified after initialization
    private final int armor;
    private final int damage;
    private final int attack;
    private final int defense;
    private int x_coordinate;
    private int y_coordinate;
    private String teamName;

    public Fighter(
        String name,
        int maxHp,
        int armor,
        int damage,
        int attack,
        int defense
    ) {
        this.name = name;
        this.currHp  = maxHp;  //one can be calculated from another
        this.maxHp   = maxHp;
        this.armor   = armor;
        this.damage  = damage;
        this.attack  = attack;
        this.defense = defense;
    }
    public String getFighterName() {
        return name;
    }
    public int getCurrentHealth() {
        return currHp;
    }
    public int getMaximumHealth() {
        return maxHp;
    }
    public int getArmor() {
        return armor;
    }
    public int getDamage() {
        return damage;
    }
    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getXCoordinate() { return x_coordinate; }
    public int getYCoordinate() { return y_coordinate; }
    public void setXCoordinate(int x) { x_coordinate = x; }
    public void setYCoordinate(int y) { y_coordinate = y; }




    public boolean isAlive() {
        return currHp > 0;
    }

    public void setTeamName(String name) {
        teamName = name;
    }
    public String getTeamName() { return teamName; }
    public void takeDamage(int newHealth) {
        currHp = newHealth;
    }

    public void move(Simulation simulation, int xCoor, int yCoor) {
        x_coordinate = xCoor;
        y_coordinate = yCoor;
    }

    @Override
    public String toString() {
        String fighter = name + ", Health: " + currHp + ", Damage: " + damage + ", Attack: " + attack;
        return fighter;
    }

    public void fight(Simulation simulation, Position pos1) {
        Arena arena = simulation.getArena();
        Tile tile = arena.getTile(pos1.getX(), pos1.getY());
        Fighter enemyFighter = tile.getFighter().get();

        int enemyHealth = enemyFighter.getCurrentHealth();
        int realDamage = getDamage() - enemyFighter.getArmor();
        if (realDamage > 0) {
            enemyHealth = enemyHealth - realDamage;
        } else if (realDamage < 0) {
            enemyHealth -= 1;
        }
        enemyFighter.takeDamage(enemyHealth);

    }

}
