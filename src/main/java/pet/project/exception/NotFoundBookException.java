package pet.project.exception;

public class NotFoundBookException extends RuntimeException {
    public NotFoundBookException(int id) {
        super("This book with id: " + id + " not found");
    }
}
