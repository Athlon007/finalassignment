package nl.inholland.konradfigura.finalassignment.model.exceptions;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
       super(message);
    }
}
