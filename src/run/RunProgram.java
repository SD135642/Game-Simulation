package run;

import builders.BuilderFromFiles;
import exceptions.DescriptorsNotFoundException;
import main_action.Simulation;
import main_action.SimulationResult;

import java.io.FileNotFoundException;

public class RunProgram {
    public static void main(String[] args) {
        BuilderFromFiles builder = new BuilderFromFiles();
        try {
            builder.build();
        } catch(FileNotFoundException e) {
            throw new DescriptorsNotFoundException("data");
        }
        Simulation simulation = new Simulation(builder.getTeams(), builder.getArena(), builder.getFighters());
        SimulationResult simulationResult = simulation.run();

    }

}
