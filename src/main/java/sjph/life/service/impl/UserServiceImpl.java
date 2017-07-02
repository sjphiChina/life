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

    @Override
    public long createUser(User user) {
        long id = userDao.createUser(user);
        user.setId(id);
        LOGGER.info("Created User: " + user.toString());
        return user.getId();
    }

    @Override
    public User findUser(long userId) {
        return userDao.findUser(userId);
    }

    @Override
    public User findUser(String email) {
        return userDao.findUser(email);
    }

    @Override
    public boolean updateUser(User user) {
        if (userDao.updateUser(user) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

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
