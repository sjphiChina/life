package sjph.life.service;

import sjph.life.model.User;
import sjph.life.service.exception.UserNotFoundException;

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
     * @throws UserNotFoundException 
     */
    User findUser(long userId) throws UserNotFoundException;

    /**
     * @param email
     * @return the User requested
     * @throws UserNotFoundException 
     */
    User findUserByEmail(String email) throws UserNotFoundException;

    /**
     * @param userName
     * @return the User requested
     * @throws UserNotFoundException 
     */
    User findUserByUserName(String userName) throws UserNotFoundException;

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
