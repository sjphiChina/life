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
package sjph.life.website.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.website.client.PersonRestTemplateClient;
import sjph.life.website.exception.UserNotFoundException;
import sjph.life.website.model.User;
import sjph.life.website.model.UserDto;
import sjph.life.website.service.UserService;

/**
 * @author shaohuiguo
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired(required = true)
    private PersonRestTemplateClient         personRestTemplateClient;

    @Override
    public Long createUser(User user) {
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        long id = personRestTemplateClient.createUser(user);
        LOGGER.info("Created User: " + user.toString());
        return user.getId();
    }

    @Override
    public UserDto findUser(String userId) throws UserNotFoundException {
        UserDto userDto = personRestTemplateClient.findUser(userId);
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        
        throw new UserNotFoundException("Cannot find user: userId=" + userId);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = personRestTemplateClient.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Cannot find user: email=" + email);
    }

    @Override
    public UserDto findUserByUserName(String userName) throws UserNotFoundException {
        UserDto userDto = personRestTemplateClient.findUserByUserName(userName);
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        throw new UserNotFoundException("Cannot find user: userName=" + userName);
    }

    @Override
    public boolean updateUser(User user) {
        return personRestTemplateClient.updateUser(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        
        return personRestTemplateClient.deleteUser(userId);
    }

}
