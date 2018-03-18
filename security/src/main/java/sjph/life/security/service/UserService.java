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
package sjph.life.security.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.User;
import sjph.life.model.UserDto;
import sjph.life.model.UserNotFoundException;
import sjph.life.security.client.PersonRestTemplateClient;


/**
 * @author shaohuiguo
 *
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    
    @Autowired(required = true)
    private PersonRestTemplateClient         personRestTemplateClient;

    public UserDto findUser(String userId) throws UserNotFoundException {
        UserDto userDto = personRestTemplateClient.findUser(userId);
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        
        throw new UserNotFoundException("Cannot find user: userId=" + userId);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = personRestTemplateClient.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Cannot find user: email=" + email);
    }

    public UserDto findUserByUserName(String userName) throws UserNotFoundException {
        UserDto userDto = personRestTemplateClient.findUserByUserName(userName);
        if (userDto != null) {
            LOGGER.info("Using cache to get UserDto: " + userDto.toString());
            return userDto;
        }
        throw new UserNotFoundException("Cannot find user: userName=" + userName);
    }


}
