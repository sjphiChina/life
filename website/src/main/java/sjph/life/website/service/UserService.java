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
package sjph.life.website.service;

import sjph.life.model.user.User;
import sjph.life.model.user.UserDto;
import sjph.life.website.exception.UserNotFoundException;

/**
 * Provides services for {@link UserDto}.
 * 
 * @author Shaohui Guo
 *
 */
public interface UserService {

    /**
     * @param user
     * @return the ID of {@link User} table record created
     */
    Long createUser(User user);

    /**
     * @param userId
     * @return the User requested
     * @throws UserNotFoundException
     */
    UserDto findUser(String userId) throws UserNotFoundException;

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
    UserDto findUserByUserName(String userName) throws UserNotFoundException;

    /**
     * @param user
     * @return true if the user specified was updated successfully else false
     */
    boolean updateUser(User user);

    /**
     * @param userId
     * @return true if the user specified was updated successfully else false
     */
    boolean deleteUser(String userId);
}
