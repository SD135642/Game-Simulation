package exceptions;

public class WrongFormatException extends RuntimeException {
    public WrongFormatException(int x, int y, char invalidChar) {
        super(wrongFormatMessage(x, y, invalidChar));
    }

    private static String wrongFormatMessage(int x, int y, char invalidChar) {
        return "An invalid char " + invalidChar + " was found at column " + x + " and row " + y;
    }
}
