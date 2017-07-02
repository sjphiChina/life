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
     * @param userId
     * @param followerId
     * @return the affected rows
     */
    int deleteFollwer(Long userId, Long followerId);
}
