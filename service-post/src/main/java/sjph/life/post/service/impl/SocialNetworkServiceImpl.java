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
package sjph.life.post.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.post.client.SocialNetworkRestClient;
import sjph.life.post.service.SocialNetworkService;



/**
 * @author Shaohui Guo
 *
 */
@Service
public class SocialNetworkServiceImpl implements SocialNetworkService {

    @Autowired(required = true)
    SocialNetworkRestClient socialNetworkRestClient;

    @Override
    public void follow(String userId, String followingId) {
        socialNetworkRestClient.follow(userId, followingId);
    }

    @Override
    public Collection<String> getFollower(String userId) {
        
        return socialNetworkRestClient.getFollower(userId);
    }

    @Override
    public Collection<String> getFollowing(String userId) {
        
        return socialNetworkRestClient.getFollowing(userId);
    }

    @Override
    public long getNumberOfFollower(String userId) {
        int number = socialNetworkRestClient.getFollower(userId).size();
        return number;
    }

    @Override
    public void deleteFollwer(String userId, String followerId) {
    }

    @Override
    public void unFollow(String userId, String followingId) {
        socialNetworkRestClient.unfollow(userId, followingId);
    }

    @Override
    public boolean isFollowUser(String userId, String followingId) {
        return false;
    }

//    @Override
//    public Friendship getNetwork(String userId) {
//        Friendship friendship = friendshipRepository.findById(userId);
//        return friendship;
//    }
}
