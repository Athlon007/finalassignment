package nl.inholland.konradfigura.finalassignment.Model.Exceptions;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
       super(message);
    }
}
