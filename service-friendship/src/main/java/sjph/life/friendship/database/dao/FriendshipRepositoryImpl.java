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
package sjph.life.friendship.database.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;
//
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import sjph.life.friendship.model.Friendship;

/**
 * Current test
 * 
 * @author Shaohui Guo
 *
 */
@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

//    @Override
//    public Friendship findById(String userId) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void save(Friendship friendship) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    @Override
//    public void update(Friendship friendship) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    @Override
//    public void addFollowing(String userId, String followingId) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    @Override
//    public void unFollow(String userId, String followingId) {
//        // TODO Auto-generated method stub
//        
//    }

    @Autowired
    @Qualifier("cassandraOperations")
    private CassandraOperations cassandraOperations;

//     @Override
//     public Friendship findOne(String id) {
//     // TODO Auto-generated method stub
//     Select select = QueryBuilder.select().from("friendship");
//     select.where(QueryBuilder.eq("id", id));
//     Friendship friendship = cassandraOperations.selectOne(select, Friendship.class);
//     if (friendship != null) {
//     return friendship;
//     }
//     friendship = new Friendship(id, new HashSet<>(), new HashSet<>());
//     return save(friendship);
//     }

    @Override
    public void addFollowing(String userId, String followingId) {
        Friendship user_friendship = findById(userId);
        Set<String> followingsSet = user_friendship.getFollowing();
        followingsSet.add(followingId);
        update(user_friendship);

        Friendship following_friendship = findById(followingId);
        Set<String> followersSet = following_friendship.getFollowers();
        followersSet.add(userId);
        update(following_friendship);
    }

    @Override
    public void unFollow(String userId, String followingId) {
        Friendship user_friendship = findById(userId);
        Set<String> followingsSet = user_friendship.getFollowing();
        followingsSet.remove(followingId);
        update(user_friendship);

        Friendship following_friendship = findById(followingId);
        Set<String> followersSet = following_friendship.getFollowers();
        followersSet.remove(userId);
        update(following_friendship);
    }

    @Override
    public Friendship findById(String userId) {
        Select select = QueryBuilder.select().from("friendship");
        select.where(QueryBuilder.eq("user_id", userId));
        Friendship friendship = cassandraOperations.selectOne(select, Friendship.class);
        if (friendship != null) {
            return friendship;
        }
        // Following is pretty bad due to current spring cassandra custom conversion api, so just 
        // for the time being use.
        Set<String> followingsSet = new HashSet<>();
        followingsSet.add("-1");
        Set<String> followersSet = new HashSet<>();
        followersSet.add("-1");
        friendship = new Friendship(userId, followingsSet, followersSet);
        save(friendship);
        return friendship;
    }

    @Override
    public void save(Friendship friendship) {
        cassandraOperations.insert(friendship);
    }

    @Override
    public void update(Friendship friendship) {
        cassandraOperations.update(friendship);
    }

}
