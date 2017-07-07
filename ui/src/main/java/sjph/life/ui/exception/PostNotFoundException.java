package sjph.life.ui.exception;

/**
 * @author shaohuiguo
 *
 */
public class PostNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2800032977224642983L;

    private long              postId;
    private String            message;
    private RuntimeException  runtimeException;

    public PostNotFoundException(long postId, String message, RuntimeException runtimeException) {
        this.postId = postId;
        this.message = message;
        this.runtimeException = runtimeException;
    }

    public long getPostId() {
        return postId;
    }

    public String getMessage() {
        return message;
    }
}
