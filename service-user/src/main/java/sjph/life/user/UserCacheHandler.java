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
package sjph.life.user;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import sjph.life.platform.cache.redis.JacksonHashMapperWarpper;
import sjph.life.service.dto.UserDtoSchema;
import sjph.life.user.dto.UserDto;

/**
 * A place to handle all cache operations to {@link UserDto}.
 * 
 * @author Shaohui Guo
 *
 */
@Service
public class UserCacheHandler {

    @Autowired(required = true)
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate<String, String>             template;
    private ValueOperations<String, String>           valueOps;

    // global users
    private RedisList<String>                         users;
    // @SuppressWarnings({ "unchecked", "rawtypes" })
    // private final HashMapper<UserDto, String, String> userMapper = new
    // DecoratingStringHashMapper(
    // new Jackson2HashMapper(false));

    @SuppressWarnings("unused")
    private final HashMapper<UserDto, String, String> userMapper = new DecoratingStringHashMapper<UserDto>(
            new JacksonHashMapperWarpper<UserDto>(UserDto.class));

    @PostConstruct
    private void init() {
        valueOps = template.opsForValue();
        users = new DefaultRedisList<>(KeyUtils.users(), template);
    }

    /**
     * @param userDto a {@link UserDto} object
     * @return the authenticated value for the user
     */
    public String addUser(UserDto userDto) {
        // cache: [user:sjphiChina:uid, 1]
        valueOps.set(KeyUtils.user(userDto.getUserName()), userDto.getId());
        // get userDto hash map
        Map<String, String> userDtoHash = userMapper.toHash(userDto);
        // cache: [uid:1, [userName:sjphiChina, email:sjph.guo@gmail.com, ...]]
        getUserMap(userDto.getId()).putAll(userDtoHash);
        // add userId to users list
        users.addFirst(userDto.getId());
        return addAuth(userDto.getUserName());
    }

    /**
     * @param userName the user name of user
     * @return the user id of user
     */
    public String findUserId(String userName) {
        return String.valueOf(valueOps.get(KeyUtils.user(userName)));
    }

    /**
     * @param userName the user name of user
     * @return true if cache has the user name
     */
    public boolean isUserValid(String userName) {
        return template.hasKey(KeyUtils.user(userName));
    }

    /**
     * @param userName the user name of user
     * @return the authenticated value for the user
     */
    public String addAuth(String userName) {
        String userId = findUserId(userName);
        // add random auth key relation
        String authValue = UUID.randomUUID().toString();
        valueOps.set(KeyUtils.auth(userId), authValue);
        valueOps.set(KeyUtils.authKey(authValue), userId);
        return authValue;
    }

    /**
     * @param userId the user id of user
     */
    public void deleteUser(String userId) {
        String uidKey = KeyUtils.uid(userId);
        String userName = String.valueOf(valueOps.get(uidKey));
        template.delete(Arrays.asList(uidKey, KeyUtils.user(userName)));
        deleteAuth(userName);
    }

    /**
     * @param userName the user name of user
     */
    public void deleteAuth(String userName) {
        String userId = findUserId(userName);
        String authKey = KeyUtils.auth(userId);
        String auth = String.valueOf(valueOps.get(authKey));
        template.delete(Arrays.asList(authKey, KeyUtils.authKey(auth)));
    }

    /**
     * @param userId the user id of user
     * @return the {@link UserDto} object
     */
    public UserDto findUser(String userId) {
        UserDto userDto = null;
        RedisMap<String, String> userMap = getUserMap(userId);
        // TODO now I map the UserDto manually,
        if (userMap != null && !userMap.isEmpty()) {
            userDto = new UserDto();
            for (Entry<String, String> entry : userMap.entrySet()) {
                if (UserDtoSchema.get(entry.getKey()) != null) {
                    if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.ID)) {
                        userDto.setId(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.EMAIL)) {
                        userDto.setEmail(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.USER_NAME)) {
                        userDto.setUserName(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.CREATED_DATE)) {
                        userDto.setCreatedDate(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey())
                            .equals(UserDtoSchema.MODIFIED_DATE)) {
                        userDto.setModifiedDate(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.FIRST_NAME)) {
                        userDto.setFirstName(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.LAST_NAME)) {
                        userDto.setLastName(entry.getValue());
                    }
                    else if (UserDtoSchema.get(entry.getKey()).equals(UserDtoSchema.PORTRAY)) {
                        userDto.setPortray(entry.getValue());
                    }
                }
            }
            // userDto = userMapper.fromHash(userMap);
        }
        return userDto;
    }

    /**
     * @param authValue the authentication value of user
     * @return the {@link UserDto} object of user
     */
    public UserDto findUserForAuth(String authValue) {
        String userId = String.valueOf(valueOps.get(KeyUtils.authKey(authValue)));
        return findUser(userId);
    }

    private RedisMap<String, String> getUserMap(String userId) {
        return new DefaultRedisMap<>(KeyUtils.uid(userId), template);
    }
}
