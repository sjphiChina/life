package sjph.life.website.exception;

/**
 * @author shaohuiguo
 *
 */
public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2800032977224642983L;

    /**
     * @param message customized message
     * @param runtimeException RuntimeException
     */
    public PostNotFoundException(String message, RuntimeException runtimeException) {
        super(message, runtimeException);
    }
}
