package render;

import main_action.FighterResult;
import main_action.SimulationResult;
import main_action.TurnResult;
import models.Arena;
import models.Fighter;
import models.Team;
import models.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ConsoleSimulationRenderer implements SimulationRenderer {
    int miliseconds;
    Arena arena;
    SimulationResult simulationResult;

    public ConsoleSimulationRenderer(int miliseconds) {
        this.miliseconds = miliseconds;
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public void fillBuffer(ArrayList<StringBuilder> buffer, TurnResult turnResult) {
        fillArena(buffer);
        fillFightersData(buffer);
        fillActionLog(buffer, turnResult);
    }

    public void fillActionLog(ArrayList<StringBuilder> buffer, TurnResult turnResult) {
        int rowNum = buffer.size();
        addBufferRow(buffer, rowNum);
        StringBuilder moveResults = new StringBuilder();

        ArrayList<FighterResult> results = turnResult.getFighterResults();
        for (int i = 0; i < results.size(); i++) {
            moveResults = findActiveFighter(turnResult, i);
            buffer.add(moveResults);
        }
    }

    public StringBuilder findActiveFighter(TurnResult turnResult, int i) {
        StringBuilder moveResults = new StringBuilder();

        FighterResult result = turnResult.getFighterResult(i);
        if (result.getPrevPosition() != result.getCurrPosition()) {
            moveResults.append(result); //toString(), add separate stringbuilders
        }
        if (result.getHpBefore() != result.getHpAfter()) {
            moveResults.append(result);
        }
        return moveResults;
    }

    public void fillFightersData(ArrayList<StringBuilder> buffer) {
        List<Fighter> fighters = simulationResult.getSimulation().getFighters();
        List<Team> teams = simulationResult.getSimulation().getTeams();
        int rowNum = 0;

        for (int k = 0; k < teams.size(); k++) {
            addBufferRow(buffer, rowNum);

            Team team = teams.get(k);
            String name = team.getName();
            buffer.get(rowNum).append(name);
            rowNum++;
            addBufferRow(buffer, rowNum);
            for (int j = 0; j < team.getFighters().size(); j++) {
                Fighter fighter = team.getFighter(j);
                buffer.get(rowNum).append(fighter.toString());
                rowNum++;
                addBufferRow(buffer, rowNum);
            }
        }

    }
    public void addBufferRow(ArrayList<StringBuilder> buffer, int rowNum) {
        if (rowNum == buffer.size()) {
            buffer.add(new StringBuilder());
            for (int j = 0; j < arena.getWidth() + 2; j++) {
                buffer.get(rowNum).append(' ');
            }
        }
    }

    public void fillArena(ArrayList<StringBuilder> buffer) {
        int height = arena.getHeight();
        int width = arena.getWidth();
        for (int i = 0; i < height + 2; i++) {
            StringBuilder row = new StringBuilder(width+2);
            buffer.add(row);
        }
        fillWalls(buffer);
        fillFields(buffer);
        fillFighters(buffer);
    }

    public void fillFighters(ArrayList<StringBuilder> buffer) {
        List<Fighter> fighters = simulationResult.getSimulation().getFighters();
        for (int i = 0; i < fighters.size(); i++) {
            int xCoor = fighters.get(i).getXCoordinate();
            int yCoor = fighters.get(i).getYCoordinate();
            buffer.get(yCoor+1).setCharAt(xCoor+1, '\uF425');
        }
    }

    public void fillFields(ArrayList<StringBuilder> buffer) {
        int height = arena.getHeight();
        int width = arena.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean access = arena.getTile(i, j).isAccessible();
                if (access) {
                    buffer.get(i).setCharAt(j, ' ');
                } else {
                    buffer.get(i).setCharAt(j, '\u25FC');
                }
            }
        }
    }

    public void fillWalls(ArrayList<StringBuilder> buffer) {
        int height = arena.getHeight();
        int width = arena.getWidth();
        for (int i = 0; i < height+2; i++) {
            for (int j = 0; j < width+2; j++) {
                if (i == 0) {
                    buffer.get(0).setCharAt(j, '_');
                }
                if (j == 0) {
                    buffer.get(i).setCharAt(0, '|');
                }
                if (j == height-1) {
                    buffer.get(i).setCharAt(j, '|');
                }
                if (i == width-1) {
                    buffer.get(i).setCharAt(j, '_');
                }
            }
        }
    }

    @Override
    public void render(SimulationResult simulationResult) {
        int numberOfTurnResults = simulationResult.getNumResults();
        ArrayList<StringBuilder> buffer = new ArrayList<>();
        for (int i = 0; i < numberOfTurnResults; i++) {
            fillBuffer(buffer, simulationResult.getTurnResults(i));
        }
        clearScreen();
        for (int i = 0; i < buffer.size(); i++) {
            StringBuilder newLine = buffer.get(i);
            System.out.println(newLine.toString());
        }
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}
