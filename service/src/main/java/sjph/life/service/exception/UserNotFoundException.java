package sjph.life.service.exception;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3912082979984666982L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, RuntimeException runtimeException) {
        super(message, runtimeException);
    }
}
