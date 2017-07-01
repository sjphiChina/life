/**
 * 
 */
package sjph.life.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.User;
import sjph.life.model.dao.UserDao;
import sjph.life.model.service.UserService;

/**
 * @author shaohuiguo
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired(required = true)
    private UserDao             userDao;

    /*
     * (non-Javadoc)
     * 
     * @see sjph.life.model.service.UserService#createUser(sjph.life.model.User)
     */
    @Override
    public long createUser(User user) {
        long id = userDao.createUser(user);
        user.setId(id);
        LOGGER.info("Created User: " + user.toString());
        return user.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see sjph.life.model.service.UserService#findUser(long)
     */
    @Override
    public User findUser(long userId) {
        return userDao.findUser(userId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see sjph.life.model.service.UserService#findUser(java.lang.String)
     */
    @Override
    public User findUser(String email) {
        return userDao.findUser(email);
    }

    /*
     * (non-Javadoc)
     * 
     * @see sjph.life.model.service.UserService#updateUser(sjph.life.model.User)
     */
    @Override
    public boolean updateUser(User user) {
        if (userDao.updateUser(user) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see sjph.life.model.service.UserService#deleteUser(long)
     */
    @Override
    public boolean deleteUser(long userId) {
        if (userDao.deleteUser(userId) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

}
