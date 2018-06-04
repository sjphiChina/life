/**
 * 
 */
package sjph.life.model.legacy;

/**
 * Currently I just use sql to implement this logic and related, will switch to nosql later.
 * 
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
public class Relationship {

    private final Long userId;
    private final Long followerId;

    public Relationship(Long userId, Long followerId) {
        super();
        this.userId = userId;
        this.followerId = followerId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

}
