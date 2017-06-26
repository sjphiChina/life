package sjph.life.rest.exception;

/**
 * @author shaohuiguo
 *
 */
public class ServiceRequestFailedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8088998331811289905L;

    /**
     * @param message
     * @param t
     */
    public ServiceRequestFailedException(String message, Throwable t) {
        super(message, t);
    }
}
