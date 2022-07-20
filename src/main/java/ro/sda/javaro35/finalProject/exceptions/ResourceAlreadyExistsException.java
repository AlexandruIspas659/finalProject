package ro.sda.javaro35.finalProject.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException (String message) {
        super(message);
    }
}
