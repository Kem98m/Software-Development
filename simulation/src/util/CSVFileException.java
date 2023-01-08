package util;

/**
 * Exception handler class for the CSVReader class
 */
public class CSVFileException extends Exception {

    private String error;

    /**
     * Constructor for the exception class
     * @param message The message you want to send to the user
     */
    public CSVFileException(String message) {
        super(message);
        error = message;
    }

    /**
     * Alternate constructor for the exception class that also gives a throwable
     * @param message Message to send to user
     * @param cause Cause of the error
     */
    public CSVFileException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return error;
    }
}
