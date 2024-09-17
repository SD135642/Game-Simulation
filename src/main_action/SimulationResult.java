package main_action;
import models.Arena;
import java.util.ArrayList;

public class SimulationResult {
    private Simulation simulation;
    private ArrayList <TurnResult> turnResults = new ArrayList<>();

    public SimulationResult(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public int getNumResults() {
        return turnResults.size();
    }

    public TurnResult getTurnResults(int i) {
        return turnResults.get(i);
    }

    public void addTurnResults(TurnResult turnResult) {
        turnResults.add(turnResult);
    }
}
