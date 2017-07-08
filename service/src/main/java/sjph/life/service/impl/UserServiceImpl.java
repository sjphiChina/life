package sjph.life.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.User;
import sjph.life.model.dao.UserDao;
import sjph.life.service.UserService;
import sjph.life.service.exception.UserNotFoundException;

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
    public User findUser(long userId) throws UserNotFoundException {
        User user = userDao.findUser(userId);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Cannot find user: userId=" + userId);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Cannot find user: email=" + email);
    }

    @Override
    public User findUserByUserName(String userName) throws UserNotFoundException {
        User user = userDao.findUserByUserName(userName);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Cannot find user: userName=" + userName);
    }

    @Override
    public boolean updateUser(User user) {
        if (userDao.updateUser(user) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(long userId) {
        if (userDao.deleteUser(userId) == 1) {
            return true;
        }
        return false;
    }
}
