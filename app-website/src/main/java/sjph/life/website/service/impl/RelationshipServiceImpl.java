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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.website.client.NetworkRestTemplateClient;
import sjph.life.website.model.Friendship;
import sjph.life.website.service.RelationshipService;



/**
 * @author Shaohui Guo
 *
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired(required = true)
    NetworkRestTemplateClient networkRestTemplateClient;

    @Override
    public void follow(String userId, String followingId) {
        networkRestTemplateClient.follow(userId, followingId);
    }

    @Override
    public Collection<String> getFollwers(String userId) {
        Collection<String> followers = networkRestTemplateClient.getFollower(userId);
        return followers;
    }

    @Override
    public Collection<String> getFollwees(String userId) {
        Collection<String> followings = networkRestTemplateClient.getFollowing(userId);
        return followings;
    }

    @Override
    public int getNumberOfFollower(String userId) {
        Collection<String> followers = networkRestTemplateClient.getFollower(userId);
        if (followers!=null) {
            return followers.size();
        }
        return 0;
    }

    @Override
    public int deleteFollwer(String userId, String followerId) {
        
        return 0;
    }

    @Override
    public int unFollow(String userId, String followingId) {
        networkRestTemplateClient.unfollow(userId, followingId);
        return 1;
    }

    @Override
    public boolean isFollowUser(String userId, String followingId) {
        return false;
        // return relationshipDao.isFollowUser(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public Friendship getNetwork(String userId) {
        Friendship friendship = networkRestTemplateClient.getFriendship(userId);
        return friendship;
    }
}
