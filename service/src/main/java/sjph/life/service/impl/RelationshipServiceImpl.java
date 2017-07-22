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
package sjph.life.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.dao.RelationshipDao;
import sjph.life.service.RelationshipCacheHandler;
import sjph.life.service.RelationshipService;

/**
 * @author Shaohui Guo
 *
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired(required = true)
    private RelationshipDao          relationshipDao;

    @Autowired(required = true)
    private RelationshipCacheHandler relationshipCacheHandler;

    @Override
    public void follow(String userId, String followerId) {
        relationshipDao.createRelationship(Long.valueOf(userId), Long.valueOf(followerId));
        relationshipCacheHandler.follow(userId, followerId);
    }

    @Override
    public Collection<String> getFollwers(String userId) {
        Collection<String> followers = relationshipCacheHandler.getFollowers(userId);
        if (followers != null && followers.size() != 0) {
            return followers;
        }
        List<Long> followersDb = relationshipDao.getFollwers(Long.valueOf(userId));
        if (followersDb != null && followersDb.size() != 0) {
            followers = new ArrayList<>(followersDb.size());
            for (Long followerIdLong : followersDb) {
                String followerId = String.valueOf(followerIdLong);
                followers.add(followerId);
                relationshipCacheHandler.follow(followerId, userId);
            }
        }
        return followers;
    }

    @Override
    public Collection<String> getFollwees(String userId) {
        Collection<String> followees = relationshipCacheHandler.getFollowing(userId);
        if (followees != null && followees.size() != 0) {
            return followees;
        }
        List<Long> followeesDb = relationshipDao.getFollwees(Long.valueOf(userId));
        if (followeesDb != null && followeesDb.size() != 0) {
            followees = new ArrayList<>(followeesDb.size());
            for (Long followeeIdLong : followeesDb) {
                String followeeId = String.valueOf(followeeIdLong);
                followees.add(followeeId);
                relationshipCacheHandler.follow(userId, followeeId);
            }
        }
        return followees;
    }

    @Override
    public int getNumberOfFollower(String userId) {
        int number = relationshipCacheHandler.getFollowersNumber(userId);
        if (number != 0) {
            return number;
        }
        return relationshipDao.getNumberOfFollower(Long.valueOf(userId));
    }

    @Override
    public int deleteFollwer(String userId, String followerId) {
        return relationshipDao.deleteFollwer(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public int unFollow(String userId, String followerId) {
        int result = relationshipDao.deleteFollwee(Long.valueOf(userId), Long.valueOf(followerId));
        relationshipCacheHandler.stopFollowing(followerId, userId);
        return result;
    }

    @Override
    public boolean isFollowUser(String userId, String followerId) {
        return relationshipCacheHandler.isFollowing(userId, followerId);
        // return relationshipDao.isFollowUser(Long.valueOf(userId), Long.valueOf(followerId));
    }
}
