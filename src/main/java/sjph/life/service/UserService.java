package sjph.life.service;

import sjph.life.model.User;

/**
 * @author shaohuiguo
 *
 */
public interface UserService {

    /**
     * @param user
     * @return the ID of {@link User} table record created
     */
    long createUser(User user);

    /**
     * @param userId
     * @return the User requested
     */
    User findUser(long userId);

    /**
     * @param email
     * @return the User requested
     */
    User findUser(String email);

    /**
     * @param user
     * @return true if the user specified was updated successfully else false
     */
    boolean updateUser(User user);

    /**
     * @param userId
     * @return true if the user specified was updated successfully else false
     */
    boolean deleteUser(long userId);
}
