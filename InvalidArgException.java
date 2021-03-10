/**
 *  @author joshua.s.george@stonybrook.edu
 *  ID: 112839378
 *  Rec: 02
 *
 *  <p>
 *      This Exception class to be thrown for invalid arguments
 *  </p>
 */
public class InvalidArgException extends Exception {
    /**
     * Creates InvalidArgException object
     * @param message Message to be displayed
     */
    public InvalidArgException(String message) {
        super(message);
    }
}
