/**
 * 
 */
package sjph.life.model.dao;

import sjph.life.model.User;

/**
 * @author shaoguo
 *
 */
public interface UserDao {

    //@formatter:off
/** CREATE operation */
    /**
     * @param user
     * @return
     */
    long createUser(User user);

/** READ operation */
    /**
     * @param userId
     * @return
     */
    User findUser(Long userId);

    /**
     * @param email
     * @return
     */
    User findUser(String email);

/** UPDATE operation */
    /**
     * @param user
     * @return
     */
    int updateUser(User user);

/** DELETE operation */
    /**
     * @param userId
     * @return
     */
    int deleteUser(Long userId);
}
