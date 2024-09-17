package models;

public class Arena {
    private Tile[][] arena;

    public Arena(int width, int height) {
        arena = new Tile[height][width];
    }

    public void setTile(int x, int y, Tile tile) {
        arena[y][x] = tile;
    }

    public int getHeight() {
        return arena.length;
    }

    public int getWidth() {
        if (arena == null || arena.length == 0) {
            return 0;
        }
        return arena[0].length;
    }

    public Tile getTile(int x, int y) {
        return arena[y][x];
    }


}
