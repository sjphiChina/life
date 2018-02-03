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
package sjph.life.friendship.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.friendship.cache.RelationshipCacheHandler;
import sjph.life.friendship.database.dao.FriendshipRepository;
import sjph.life.friendship.event.model.NetworkChangeModel;
import sjph.life.friendship.event.source.NetworkSourceBean;
import sjph.life.friendship.model.Friendship;
import sjph.life.friendship.service.RelationshipService;



/**
 * @author Shaohui Guo
 *
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired(required = true)
    NetworkSourceBean networkSourceBean;
    
    @Autowired(required = true)
    //private RelationshipDao          relationshipDao;
    FriendshipRepository friendshipRepository;

    @Autowired(required = true)
    private RelationshipCacheHandler relationshipCacheHandler;

    @Override
    public void follow(String userId, String followingId) {
        //relationshipDao.createRelationship(Long.valueOf(userId), Long.valueOf(followerId));
        friendshipRepository.addFollowing(userId, followingId);
        relationshipCacheHandler.follow(userId, followingId);
        networkSourceBean.publishNetworkChange(NetworkChangeModel.ACTION.FOLLOW.name(), userId);
    }

    @Override
    public Collection<String> getFollwers(String userId) {
        Collection<String> followers = relationshipCacheHandler.getFollowers(userId);
        if (followers != null && followers.size() != 0) {
            return followers;
        }
        //List<Long> followersDb = relationshipDao.getFollwers(Long.valueOf(userId));
//        if (followersDb != null && followersDb.size() != 0) {
//            followers = new ArrayList<>(followersDb.size());
//            for (Long followerIdLong : followersDb) {
//                String followerId = String.valueOf(followerIdLong);
//                followers.add(followerId);
//                relationshipCacheHandler.follow(followerId, userId);
//            }
//        }
        followers = new LinkedList<>();
        Friendship friendship = friendshipRepository.findById(userId);
        Set<String> followersSet = friendship.getFollowers();
        if (followersSet != null && followersSet.size()!=0) {
            for (String followerId: followersSet) {
                followers.add(followerId);
                relationshipCacheHandler.follow(followerId, userId);
            }
        }
        return followers;
    }

    @Override
    public Collection<String> getFollwees(String userId) {
        Collection<String> followings = relationshipCacheHandler.getFollowing(userId);
        if (followings != null && followings.size() != 0) {
            return followings;
        }
//        List<Long> followeesDb = relationshipDao.getFollwees(Long.valueOf(userId));
//        if (followeesDb != null && followeesDb.size() != 0) {
//            followings = new ArrayList<>(followeesDb.size());
//            for (Long followeeIdLong : followeesDb) {
//                String followeeId = String.valueOf(followeeIdLong);
//                followings.add(followeeId);
//                relationshipCacheHandler.follow(userId, followeeId);
//            }
//        }
        followings = new LinkedList<>();
        Friendship friendship = friendshipRepository.findById(userId);
        Set<String> followingsSet = friendship.getFollowing();
        if (followingsSet != null && followingsSet.size()!=0) {
            for (String followingId: followingsSet) {
                followings.add(followingId);
                relationshipCacheHandler.follow(userId, followingId);
            }
        }
        return followings;
    }

    @Override
    public int getNumberOfFollower(String userId) {
        int number = relationshipCacheHandler.getFollowersNumber(userId);
        if (number != 0) {
            return number;
        }
        //return relationshipDao.getNumberOfFollower(Long.valueOf(userId));
        Friendship friendship = friendshipRepository.findById(userId);
        Set<String> followersSet = friendship.getFollowers();
        if (followersSet != null) {
            for (String followerId: followersSet) {
                relationshipCacheHandler.follow(followerId, userId);
            }
            return followersSet.size();
        } 
        return 0;
    }

    @Override
    public int deleteFollwer(String userId, String followerId) {
        //return relationshipDao.deleteFollwer(Long.valueOf(userId), Long.valueOf(followerId));
        Friendship user_friendship = friendshipRepository.findById(userId);
        Set<String> user_followersSet = user_friendship.getFollowers();
        user_followersSet.remove(followerId);
        Friendship follower_friendship = friendshipRepository.findById(followerId);
        Set<String> follower_followingSet = follower_friendship.getFollowing();
        follower_followingSet.remove(userId);
        relationshipCacheHandler.stopFollowing(followerId, userId);
        return 1;
    }

    @Override
    public int unFollow(String userId, String followingId) {
        //int result = relationshipDao.deleteFollwee(Long.valueOf(userId), Long.valueOf(followerId));
//        Friendship user_friendship = friendshipRepository.findById(userId);
//        Set<String> user_followingsSet = user_friendship.getFollowing();
//        user_followingsSet.remove(followingId);
//        Friendship following_friendship = friendshipRepository.findById(followingId);
//        Set<String> following_followersSet = following_friendship.getFollowers();
//        following_followersSet.remove(userId);
        friendshipRepository.unFollow(userId, followingId);
        relationshipCacheHandler.stopFollowing(userId, followingId);
        networkSourceBean.publishNetworkChange(NetworkChangeModel.ACTION.UNFOLLOW.name(), userId);
        return 1;
    }

    @Override
    public boolean isFollowUser(String userId, String followingId) {
        return relationshipCacheHandler.isFollowing(userId, followingId);
        // return relationshipDao.isFollowUser(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public Friendship getNetwork(String userId) {
        Friendship friendship = friendshipRepository.findById(userId);
        return friendship;
    }
}
