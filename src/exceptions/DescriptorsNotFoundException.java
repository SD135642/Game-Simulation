package exceptions;

public class DescriptorsNotFoundException extends RuntimeException {
    public DescriptorsNotFoundException(String simulationName) {
        super("Descriptors with name " + simulationName + " not found.");
    }
}
