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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.model.dao.RelationshipDao;
import sjph.life.service.RelationshipService;

/**
 * @author Shaohui Guo
 *
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired(required = true)
    private RelationshipDao relationshipDao;

    @Override
    public void follow(String userId, String followerId) {
        relationshipDao.createRelationship(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public List<Long> getFollwers(String userId) {
        return relationshipDao.getFollwers(Long.valueOf(userId));
    }

    @Override
    public List<Long> getFollwees(String followerId) {
        return relationshipDao.getFollwees(Long.valueOf(followerId));
    }

    @Override
    public Long getNumberOfFollower(String userId) {
        return relationshipDao.getNumberOfFollower(Long.valueOf(userId));
    }

    @Override
    public int deleteFollwer(String userId, String followerId) {
        return relationshipDao.deleteFollwer(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public int unFollow(String userId, String followerId) {
        return relationshipDao.deleteFollwee(Long.valueOf(userId), Long.valueOf(followerId));
    }

    @Override
    public boolean isFollowUser(String userId, String followerId) {
        return relationshipDao.isFollowUser(Long.valueOf(userId), Long.valueOf(followerId));
    }
}
