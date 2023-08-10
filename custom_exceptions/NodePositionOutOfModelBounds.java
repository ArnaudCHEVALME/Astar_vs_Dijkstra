package custom_exceptions;

public class NodePositionOutOfModelBounds extends Exception {

    public NodePositionOutOfModelBounds(String errorMessage) {
        super(errorMessage);
    }
}
