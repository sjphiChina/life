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
     * @return the user id
     */
    long createUser(User user);

/** READ operation */
    /**
     * @param userId
     * @return one user object
     */
    User findUser(Long userId);

    /**
     * @param email
     * @return one user object
     */
    User findUserByEmail(String email);

    /**
     * @param userName
     * @return one user object
     */
    User findUserByUserName(String userName);

/** UPDATE operation */
    /**
     * @param user
     * @return the affected row number
     */
    int updateUser(User user);

/** DELETE operation */
    /**
     * @param userId
     * @return the affected row number
     */
    int deleteUser(Long userId);
}
