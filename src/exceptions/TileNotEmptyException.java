package exceptions;

public class TileNotEmptyException extends RuntimeException {
    public TileNotEmptyException(int x, int y) {
        super(String.format("Tile at (%d, %d) not empty.", x, y));
    }
}
