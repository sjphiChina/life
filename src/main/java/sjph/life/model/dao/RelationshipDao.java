/**
 * 
 */
package sjph.life.model.dao;

import java.util.List;

/**
 * @author shaohuiguo
 *
 */
public interface RelationshipDao {

    /**
     * @param userId
     * @param followerId
     */
    void createRelationship(Long userId, Long followerId);

    /**
     * @param userId
     * @return a list of followers' id
     */
    List<Long> getFollwers(Long userId);

    /**
     * @param followerId
     * @return a list of followees' id
     */
    List<Long> getFollwees(Long followerId);

    /**
     * @param userId
     * @return the number of followers per userId
     */
    Long getNumberOfFollower(Long userId);

    /**
     * @param userId
     * @param followerId
     * @return the affected rows
     */
    int deleteFollwer(Long userId, Long followerId);

    /**
     * @param userId
     * @param followerId
     * @return the affected rows
     */
    int deleteFollwee(Long userId, Long followerId);
}
