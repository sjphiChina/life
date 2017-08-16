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
package sjph.life.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import sjph.life.platform.cache.redis.JacksonHashMapperWarpper;
import sjph.life.service.dto.PostDto;
import sjph.life.service.dto.PostDtoSchema;

/**
 * A place to handle all cache operations to {@link PostDto}.
 * 
 * @author Shaohui Guo
 *
 */
@Service
public class PostCacheHandler {

    @Autowired(required = true)
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate<String, String>             template;

    // global timeline
    private RedisList<String>                         timeline;

//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private final HashMapper<PostDto, String, String> postMapper = new DecoratingStringHashMapper(
//            new Jackson2HashMapper(true));
    @SuppressWarnings("unused")
    private final HashMapper<PostDto, String, String> postMapper = new DecoratingStringHashMapper<PostDto>(
            new JacksonHashMapperWarpper<PostDto>(PostDto.class));

    @PostConstruct
    private void init() {
        timeline = new DefaultRedisList<>(KeyUtils.timeline(), template);
    }

    /**
     * @param postDto the object of {@link PostDto}
     */
    public void addPost(PostDto postDto) {
        String postId = postDto.getId();
        // get postDto hash map
        Map<String, String> postHash = postMapper.toHash(postDto);
        // cache: [pid:1, [content:Work hard, Good luck!, userId:1, ...]]
        getPostMap(postId).putAll(postHash);
        // add the post id to user's timeline
        getUserTimelineList(postDto.getUserId()).addFirst(postId);
        // add the post id to global timeline
        timeline.addFirst(postId);
    }

    /**
     * @param postDtoList a list of {@link PostDto}
     */
    public void loadPosts(Collection<PostDto> postDtoList) {
        for (PostDto postDto: postDtoList) {
            String postId = postDto.getId();
            // get postDto hash map
            Map<String, String> postHash = postMapper.toHash(postDto);
            // cache: [pid:1, [content:Work hard, Good luck!, userId:1, ...]]
            getPostMap(postId).putAll(postHash);
            // add the post id to user's timeline
            getUserTimelineList(postDto.getUserId()).addLast(postId);
            // add the post id to global timeline
            timeline.addLast(postId);
        }
    }

    /**
     * @param postId the post id of post
     * @return true if the post exists
     */
    public boolean isPostValid(String postId) {
        return template.hasKey(KeyUtils.post(postId));
    }

    /**
     * @param postId the post id of post
     * @return the {@link PostDto} object
     */
    public PostDto getPost(String postId) {
        PostDto postDto = null;
        RedisMap<String, String> postMap = getPostMap(postId);
        if (postMap != null && !postMap.isEmpty()) {
            postDto = postMapper.fromHash(postMap);
        }
        return postDto;
    }

    /**
     * @param range the range for post selection
     * @return a collection of global timeline
     */
    public Collection<PostDto> getTimeline(Range range) {
        return convertPidsToPosts(KeyUtils.timeline(), range);
    }

    /**
     * @param userId the user id of user
     * @param range the range for post selection
     * @return a list of {@link PostDto} which belongs user's timeline given the range
     */
    public Collection<PostDto> getUserTimeline(String userId, Range range) {
        return convertPidsToPosts(KeyUtils.timeline(userId), range);

    }

    /**
     * @param userId the user id of user
     * @param range the range for post selection
     * @return a list of {@link PostDto} which belongs the user and user's following given the range
     */
    @Deprecated
    public Collection<PostDto> getUserPosts(String userId, Range range) {
        // TODO lots of things to do for deep consideration
        return convertPidsToPosts(KeyUtils.posts(userId), range);
    }

    /**
     * @param userId the user id of user
     * @param range the range for post selection
     * @return true if the user timeline has more posts
     */
    public boolean hasMoreUserTimeline(String userId, Range range) {
        return getUserTimelineList(userId).size() > range.end + 1;
    }

    /**
     * @param range the range for post selection
     * @return true if the global timeline has more posts
     */
    public boolean hasMoreTimeline(Range range) {
        return timeline.size() > range.end + 1;
    }

    /**
     * @param postDto the post will be deleted
     */
    public void deletePost(PostDto postDto) {
        template.delete(KeyUtils.post(postDto.getId()));
        RedisList<String> postList = getUserTimelineList(postDto.getUserId());
        postList.remove(postDto.getId());
    }

    /**
     * IMPORTANT: It deletes all posts belong to the user.
     * 
     * @param userId the user id of user
     */
    public void deletePost(String userId) {
        RedisList<String> postList = getUserTimelineList(userId);
        for (Object postId : postList) {
            template.delete(KeyUtils.post(String.valueOf(postId)));
        }
        postList.clear();
    }

    private RedisMap<String, String> getPostMap(String postId) {
        return new DefaultRedisMap<>(KeyUtils.post(postId), template);
    }

    private RedisList<String> getUserTimelineList(String userId) {
        return new DefaultRedisList<>(KeyUtils.timeline(userId), template);
    }

    private Collection<PostDto> convertPidsToPosts(String key, Range range) {
        String pid = "pid:*->";
        //final String pidKey = "#";
        final String id = PostDtoSchema.ID.getDescription();
        final String content = PostDtoSchema.CONTENT.getDescription();
        final String userId = PostDtoSchema.USER_ID.getDescription();
        final String createDate = PostDtoSchema.CREATED_DATE.getDescription();
        final String userNameDisplaying = PostDtoSchema.USER_NAME_DISPLAYING.getDescription();

        //@formatter:off
        SortQuery<String> query = SortQueryBuilder.sort(key).noSort()
                //.get(pidKey)
                .get(pid + id)
                .get(pid + content)
                .get(pid + userId)
                .get(pid + createDate)
                .get(pid + userNameDisplaying)
                .limit(range.begin, range.end).build();
        //@formatter:on

        BulkMapper<PostDto, String> bulkMapper = new BulkMapper<PostDto, String>() {
            @Override
            public PostDto mapBulk(List<String> bulk) {
                Map<String, String> map = new LinkedHashMap<>();
                Iterator<String> iterator = bulk.iterator();
                //iterator.next();
                map.put(id, String.valueOf(iterator.next()));
                map.put(content, String.valueOf(iterator.next()));
                map.put(userId, String.valueOf(iterator.next()));
                map.put(createDate, String.valueOf(iterator.next()));
                map.put(userNameDisplaying, String.valueOf(iterator.next()));
                return postMapper.fromHash(map);
            }
        };
        Collection<PostDto> sort = template.sort(query, bulkMapper);
        return sort;
    }
}
