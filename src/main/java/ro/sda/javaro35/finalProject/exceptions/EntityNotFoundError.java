package ro.sda.javaro35.finalProject.exceptions;

public class EntityNotFoundError extends RuntimeException{
    public EntityNotFoundError(String message) {
        super(message);
    }
}