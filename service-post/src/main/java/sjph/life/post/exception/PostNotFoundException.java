package sjph.life.post.exception;

/**
 * @author shaohuiguo
 *
 */
public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3912082979984666982L;

    /**
     * @param message customized message
     */
    public PostNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message customized message
     * @param runtimeException RuntimeException
     */
    public PostNotFoundException(String message, RuntimeException runtimeException) {
        super(message, runtimeException);
    }
}
