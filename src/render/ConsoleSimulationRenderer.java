package render;

import main_action.FighterResult;
import main_action.SimulationResult;
import main_action.TurnResult;
import models.Arena;
import models.Fighter;
import models.Team;
import models.Tile;
import java.util.Scanner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import services.Position;


public class ConsoleSimulationRenderer implements SimulationRenderer {
    @Override
    public void render(SimulationResult simulationResult) {
        for (int i = 0; i < simulationResult.getNumResults(); i++) {
            TurnResult turnResult = simulationResult.getTurnResults(i);
            Scanner scanner = new Scanner(System.in);
            Arena arena = simulationResult.getSimulation().getArena();
            printArena(arena, turnResult);
            scanner.nextLine();
        }
        
        
    }

    public void printArena(Arena arena, TurnResult turnResult) {
        int height = arena.getHeight();
        int width = arena.getWidth();
        ArrayList<FighterResult> fighterResults = turnResult.getFighterResults();
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                boolean putFighter = false;
                for (var fighterResult : fighterResults) {
                    Position pos = new Position(i, j);
                    if (fighterResult.getPrevPosition().equals(pos)) {
                        if (fighterResult.getStartHp() < 1) {
                            System.out.print("+"); // print an emoji!
                        } else {
                            System.out.print(fighterResult.getFighter().getFighterName().charAt(0));
                        }
                        putFighter = true;
                    } 
                }
                if (arena.getTile(i, j).isAccessible() && !putFighter) {   
                    System.out.print(".");
                } else if (!putFighter) {
                    System.out.print("#");
                }
            }
            System.out.print('\n');
        }
    }
}
