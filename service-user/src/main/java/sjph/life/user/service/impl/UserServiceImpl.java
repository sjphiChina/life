/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sjph.life.user.service.impl;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import sjph.life.user.cache.UserCacheHandler;
import sjph.life.user.client.PersonRestTemplateClient;
import sjph.life.user.database.dao.UserDao;
import sjph.life.user.dto.UserDto;
import sjph.life.user.exception.UserNotFoundException;
import sjph.life.user.model.User;
import sjph.life.user.service.UserService;
import sjph.life.user.utils.UserContextHolder;

/**
 * @author shaohuiguo
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired(required = true)
    private UserDao             userDao;

    @Autowired(required = true)
    private UserCacheHandler    userCacheHandler;
    
    @Autowired(required = true)
    private PersonRestTemplateClient         personRestTemplateClient;

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
        LOGGER.debug("findUser, correlationId: {}, threadId: {}", UserContextHolder.getContext().getCorrelationId(), Thread.currentThread().getId());
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

    //currently we use SEMAPHORE strategy for hystrix, since with default THREAD strategy, 
    //the userContext cannot be passed into children thread somehow, will dig it when debug is 
    //enabled later.
    @Override
//    @HystrixCommand(fallbackMethod = "buildFallbackPersonNetwork",
//            commandProperties={
//                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
//                     @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="15000")}
//    )
    public String findPersonNetwork(String userId) throws UserNotFoundException {
        LOGGER.debug("findPersonNetwork, correlationId: {}, threadId: {}", UserContextHolder.getContext().getCorrelationId(), Thread.currentThread().getId());
        //randomlyRunLong();
        return personRestTemplateClient.getNetwork(userId);
    }

    @SuppressWarnings("unused")
    private String buildFallbackPersonNetwork(String userId){
        String message = "We see connection timeout, and no network detail of userId="+userId+" is returned.";
        LOGGER.debug(message);
        return message;
    }

    // for test
    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((13 - 1) + 1) + 1;

        if (randomNum == 3)
            sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
