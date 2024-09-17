package main_action;

import java.util.ArrayList;

public class TurnResult {
    private ArrayList<FighterResult> fighterResults = new ArrayList<>();

    public int getNumResults() {
        return fighterResults.size();
    }

    public FighterResult getFighterResult(int i) {
        return fighterResults.get(i);
    }

    public ArrayList<FighterResult> getFighterResults() { return fighterResults; }

    public void addFighterResult(FighterResult fighterResult) {
        fighterResults.add(fighterResult);
    }

}
