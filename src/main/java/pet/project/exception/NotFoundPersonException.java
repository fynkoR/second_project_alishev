package pet.project.exception;

public class NotFoundPersonException extends RuntimeException {
    public NotFoundPersonException(int id) {
        super("This person with id + " + id + " not found");
    }
}
