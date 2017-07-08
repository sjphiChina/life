/**
 * 
 */
package sjph.life.website.exception;

/**
 * @author shaohuiguo
 *
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4602935224216701624L;

    /**
     * @param message customized message
     * @param t Throwable
     */
    public UserNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param message message customized message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
