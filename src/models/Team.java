package models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<Fighter> fighters;

    public Team(String name) {
        this.name = name;
        this.fighters = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public List<Fighter> getFighters(){
        return new ArrayList<Fighter>(fighters);  //copies the list of fighters to prevent user modification
    }

    public void addFighter(Fighter fighter) {
        fighters.add(fighter);
        fighter.setTeamName(name);
    }
    public void removeFighter(Fighter fighter) {
        fighters.remove(fighter);
    }
    public Fighter getFighter(int index) {
        return fighters.get(index);
    }
    public int getTeamSize() {
        return fighters.size();
    }

    public boolean hasAliveFighter() {
        for (int i = 0; i < fighters.size(); i++) {
            if (fighters.get(i).isAlive()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFighter(String name) {
        for (int i = 0; i < fighters.size(); i++) {
            if (fighters.get(i).getFighterName() == name) {  //assumes unique names
                return true;
            }
        }
        return false;
    }

    public boolean allFightersDead() {
        return !hasAliveFighter();
    }


    //class builder - 3 methods: get arena/fighters/teams

}
