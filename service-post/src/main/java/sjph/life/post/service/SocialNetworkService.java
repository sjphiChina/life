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
package sjph.life.post.service;

import java.util.Collection;

/**
 * Provides services for relationship.
 * 
 * @author Shaohui Guo
 *
 */
public interface SocialNetworkService {

    /**
     * @param userId
     * @param followingId
     */
    void follow(String userId, String followingId);

    /**
     * @param userId
     * @return a list of follower' id
     */
    Collection<String> getFollower(String userId);

    /**
     * @param userId
     * @return a list of following' id
     */
    Collection<String> getFollowing(String userId);

    /**
     * @param userId
     * @return the number of followers with userId
     */
    long getNumberOfFollower(String userId);

    /**
     * @param userId
     * @param followerId
     */
    void deleteFollwer(String userId, String followerId);

    /**
     * @param userId 
     * @param followingId
     */
    void unFollow(String userId, String followingId);

    /**
     * @param userId
     * @param followingId
     * @return true if userId is following followingId
     */
    boolean isFollowUser(String userId, String followingId);

//    /**
//     * @param userId
//     * @return the {@link Friendship} object
//     */
//    Friendship getNetwork(String userId);
}
