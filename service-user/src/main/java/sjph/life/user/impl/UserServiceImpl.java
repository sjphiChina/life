package sjph.life.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.User;
import sjph.life.model.dao.UserDao;
import sjph.life.service.UserCacheHandler;
import sjph.life.service.UserService;
import sjph.life.service.dto.UserDto;
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

    @Autowired(required = true)
    private UserCacheHandler    userCacheHandler;

    @Override
    public Long createUser(User user) {
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        long id = userDao.createUser(user);
        user.setId(id);
        UserDto userDto = new UserDto(user);
        String auth = userCacheHandler.addUser(userDto);
        LOGGER.info("Created User: " + user.toString() + " auth: " + auth);
        return user.getId();
    }

    @Override
    public UserDto findUser(String userId) throws UserNotFoundException {
        UserDto userDto = userCacheHandler.findUser(userId);
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        User user = userDao.findUser(Long.valueOf(userId));
        userDto = new UserDto(user);
        userCacheHandler.addUser(userDto);
        if (user != null) {
            return new UserDto(user);
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
    public UserDto findUserByUserName(String userName) throws UserNotFoundException {
        String userId = userCacheHandler.findUserId(userName);
        UserDto userDto = userCacheHandler.findUser(String.valueOf(userId));
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        User user = userDao.findUserByUserName(userName);
        userDto = new UserDto(user);
        userCacheHandler.addUser(userDto);
        if (user != null) {
            return new UserDto(user);
        }
        throw new UserNotFoundException("Cannot find user: userName=" + userName);
    }

//    @Override
//    public String findUserIdByUserName(String userName) throws UserNotFoundException {
//        String userId = userCacheHandler.findUserId(userName);
//        if (userId != null) {
//            return userId;
//        }
//        User user = userDao.findUserByUserName(userName);
//        UserDto userDto = new UserDto(user);
//        userCacheHandler.addUser(userDto);
//        return String.valueOf(user.getId());
//    }

    @Override
    public boolean updateUser(User user) {
        if (userDao.updateUser(user) == 1) {
            UserDto userDto = new UserDto(user);
            userCacheHandler.addUser(userDto);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        if (userDao.deleteUser(Long.valueOf(userId)) == 1) {
            userCacheHandler.deleteUser(userId);
            return true;
        }
        return false;
    }
}
