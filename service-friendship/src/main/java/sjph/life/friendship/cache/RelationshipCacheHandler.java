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
package sjph.life.friendship.cache;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.support.collections.DefaultRedisSet;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Service;

/**
 * A place to handle all cache operations to relationship.
 * 
 * @author Shaohui Guo
 *
 */
@Service
public class RelationshipCacheHandler {

    @Autowired(required = true)
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate<String, String> template;

    /**
     * @param userId the user id of user
     * @param targetUserId the user id of target user
     * @return true if follow the target user
     */
    public boolean isFollowing(String userId, String targetUserId) {
        return getUserfollowingSet(userId).contains(targetUserId);
    }

    /**
     * @param userId the user id of user
     * @return a collection of followers of the user
     */
    public Collection<String> getFollowers(String userId) {
        //return covertUserIdToNames(KeyUtils.followers(userId));
        return getUserfollowersSet(userId);
    }

    /**
     * @param userId the user id of user
     * @return the number of followers
     */
    public int getFollowersNumber(String userId) {
        return getUserfollowersSet(KeyUtils.followers(userId)).size();
    }

    /**
     * @param userId the user id of user
     * @return a collection of following of the user
     */
    public Collection<String> getFollowing(String userId) {
        //return covertUserIdToNames(KeyUtils.following(userId));
        return getUserfollowingSet(userId);
    }

    /**
     * @param userId the user id of user
     * @return the number of following
     */
    public int getFollowingNumber(String userId) {
        return getUserfollowingSet(KeyUtils.following(userId)).size();
    }

    /**
     * @param userId the user id of user
     * @param targetUserId the user id of target user
     */
    public void follow(String userId, String targetUserId) {

        getUserfollowingSet(userId).add(targetUserId);
        getUserfollowersSet(targetUserId).add(userId);
    }

    /**
     * @param userId the user id of user
     * @param targetUserId the user id of target user
     */
    public void stopFollowing(String userId, String targetUserId) {

        getUserfollowingSet(userId).remove(targetUserId);
        getUserfollowersSet(targetUserId).remove(userId);
    }

    /**
     * @param userId the user id of user
     * @param targetUserId the user id of target user
     * @return a list of user ids who are following both user and target user
     */
    public List<String> getCommonFollowers(String userId, String targetUserId) {
        RedisSet<String> followersOfTargetUser = getUserfollowersSet(targetUserId);
        String keyOfAlsoFollowed = KeyUtils.alsoFollowed(userId, targetUserId);
        RedisSet<String> tempSet = getUserfollowersSet(userId)
                .intersectAndStore(followersOfTargetUser, keyOfAlsoFollowed);
        tempSet.expire(5, TimeUnit.MINUTES);
        return covertUserIdToNames(tempSet.getKey());
    }

    /**
     * @param userId the user id of user
     * @param targetUserId the user id of target user
     * @return a list of user ids whom are followed by both user and target user
     */
    public List<String> getAlsoFollowed(String userId, String targetUserId) {
        RedisSet<String> followingOfTargetUser = getUserfollowingSet(targetUserId);
        String keyOfCommonFollowers = KeyUtils.commonFollowers(userId, targetUserId);
        RedisSet<String> tempSet = getUserfollowingSet(userId)
                .intersectAndStore(followingOfTargetUser, keyOfCommonFollowers);
        tempSet.expire(5, TimeUnit.MINUTES);
        return covertUserIdToNames(tempSet.getKey());
    }

    private List<String> covertUserIdToNames(String key) {
        return template.sort(SortQueryBuilder.sort(key).noSort().get("uid:*->userName").build());
    }

    private RedisSet<String> getUserfollowingSet(String userId) {
        return new DefaultRedisSet<>(KeyUtils.following(userId), template);
    }

    private RedisSet<String> getUserfollowersSet(String userId) {
        return new DefaultRedisSet<>(KeyUtils.followers(userId), template);
    }
}
