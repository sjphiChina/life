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
    User findUserByEmail(String email);

    /**
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);

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
