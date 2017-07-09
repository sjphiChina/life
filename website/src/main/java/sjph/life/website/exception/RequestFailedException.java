package sjph.life.website.exception;

/**
 * @author shaohuiguo
 *
 */
public class RequestFailedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8088998331811289905L;

    /**
     * @param message customized message
     * @param t Throwable
     */
    public RequestFailedException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param message message customized message
     */
    public RequestFailedException(String message) {
        super(message);
    }
}
