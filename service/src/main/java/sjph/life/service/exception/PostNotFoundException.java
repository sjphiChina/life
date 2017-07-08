package sjph.life.service.exception;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3912082979984666982L;

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, RuntimeException runtimeException) {
        super(message, runtimeException);
    }
}
