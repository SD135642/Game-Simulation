package builders;

import exceptions.DescriptorsNotFoundException;
import exceptions.WrongFormatException;
import models.Arena;
import models.Fighter;
import models.Team;
import models.Tile;
import services.Position;

import java.io.FileInputStream;
import java.io.FileNotFoundException;  //fix later
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BuilderFromFiles {
    private Arena arena;
    private List<Fighter> fighters;
    private List<Team> teams;

    public void build() throws FileNotFoundException {
        arena = buildArena();
        fighters = buildFighters();
        teams = returnTeams();
    }
    public Arena getArena() {
        return arena;
    }
    public List<Fighter> getFighters() {
        return Collections.unmodifiableList(fighters);
    }
    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    private Arena buildArena() {
        try {
            Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream("data/arena.txt")));
            Arena arena = createArenaObject(scanner);
            fillTiles(arena, scanner);
            scanner.close();
            return arena;
        } catch(FileNotFoundException e) {
            throw new DescriptorsNotFoundException("data");
        }
    }

    private Arena createArenaObject(Scanner scanner) {
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        return new Arena(width, height);
    }

    private void fillTiles(Arena arena, Scanner scanner) {
        scanner.nextLine();
        for (int rowNumber = 0; rowNumber < arena.getHeight(); rowNumber++) {
            String row = scanner.nextLine();
            parseTilesRow(arena, rowNumber, row);
        }
    }

    private void parseTilesRow(Arena arena, int rowNumber, String row) {
        for (int j = 0; j < arena.getWidth(); j++) {
            if (row.charAt(j) == '#') {
                arena.setTile(j, rowNumber, new Tile(false, new Position(j, rowNumber)));
            } else if (row.charAt(j) == '.') {
                arena.setTile(j, rowNumber, new Tile(true, new Position(j, rowNumber)));
            } else {
                throw new WrongFormatException(j, rowNumber, row.charAt(j));
            }
        }
    }

    private List<Team> returnTeams() throws FileNotFoundException {
        Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream("data/teams.txt")));
        ArrayList<Team> teams = new ArrayList<>();
        while (scanner.hasNext()) {
            String teamName = scanner.next();
            Team team = new Team(teamName);
            teams.add(team);
            int teamSize = Integer.parseInt(scanner.next());
            for (int i = 0; i < teamSize; i++) {
                String name = scanner.next();
                for (int k = 0; k < fighters.size(); k++) {
                    if (fighters.get(k).getFighterName().equals(name)) {
                        team.addFighter(fighters.get(k));
                    }
                }
            }
        }
        return teams;
    }

    private List<Fighter> buildFighters() throws FileNotFoundException {
        Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream("data/fighters.txt")));
        ArrayList<Fighter> fighters = new ArrayList<>();
       while (scanner.hasNext()) {
           String fighterName = scanner.nextLine();
           int maxHp = scanner.nextInt();
           int armor = scanner.nextInt();
           int damage = scanner.nextInt();
           int attack = scanner.nextInt();
           int defense = scanner.nextInt();
           scanner.nextLine();

           Fighter fighter = new Fighter(fighterName, maxHp, armor, damage, attack, defense);
           fighters.add(fighter);
       }
        scanner.close();
        return fighters;
    }
}
