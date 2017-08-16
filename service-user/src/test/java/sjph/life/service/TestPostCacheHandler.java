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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisMap;

import sjph.life.platform.cache.redis.JacksonHashMapperWarpper;
import sjph.life.service.dto.PostDto;
import sjph.life.service.dto.PostDtoSchema;

/**
 * @author Shaohui Guo
 *
 */
@RunWith(Parameterized.class)
public class TestPostCacheHandler {

    RedisTemplate<String, String>             template;
    RedisConnectionFactory        factory;
    //Jackson2HashMapper            mapper;
    private HashMapper<PostDto, String, String> postMapper;
    // global timeline
    private RedisList<String>                         timeline;

    /**
     * @param factory factory
     * @throws Exception Exception
     */
    public TestPostCacheHandler(RedisConnectionFactory factory) throws Exception {

        this.factory = factory;
        if (factory instanceof InitializingBean) {
            ((InitializingBean) factory).afterPropertiesSet();
        }

        ConnectionFactoryTracker.add(factory);
    }

    /**
     * @return Collection <RedisConnectionFactory>
     */
    @Parameters
    public static Collection<RedisConnectionFactory> params() {

        return Arrays.<RedisConnectionFactory> asList(new JedisConnectionFactory());
    }

    /**
     * 
     */
    @AfterClass
    public static void cleanUp() {
        ConnectionFactoryTracker.cleanUp();
    }

    /**
     * 
     */
    @Before
    public void setUp() {

        this.template = new StringRedisTemplate();
        this.template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        timeline = new DefaultRedisList<>(KeyUtils.timeline(), template);
        //this.mapper = new Jackson2HashMapper(true);
        postMapper = new DecoratingStringHashMapper<PostDto>(
                new JacksonHashMapperWarpper<PostDto>(PostDto.class));
    }

//    /**
//     * 
//     */
//    @Test // DATAREDIS-423
//    public void shouldWriteReadHashCorrectly() {
//
//        PostDto post = new PostDto("test wish", "2", "sjphiChina");
//
//        template.opsForHash().putAll("JON-SNOW", mapper.toHash(post));
//
//        PostDto result = (PostDto) mapper
//                .fromHash(template.<String, Object> opsForHash().entries("JON-SNOW"));
//        Assert.assertThat(result, Is.is(post));
//    }

    /**
     * 
     */
//    @Test // DATAREDIS-423
//    public void testQuerySort() {
//
//        String content_1 = "test wish with luck";
//        String userId_1 = "Shaohui";
//        String id_1 = "postid_1";
//        String createdDate = "20170721";
//        String userNameDisplaying = "sjphiChina";
//        PostDto post_1 = new PostDto(content_1, userId_1, userNameDisplaying);
//        post_1.setId(id_1);
//        post_1.setCreatedDate(createdDate);
//        Map<String, Object> postHash_1 = mapper.toHash(post_1);
//        RedisMap<String, Object> map = new DefaultRedisMap<>(KeyUtils.post(post_1.getId()), template);
//        map.putAll(postHash_1);
//
//        getUserTimelineList(post_1.getUserId()).addFirst(post_1.getId());
//         
//         
//         
//        Collection<PostDto> collection = convertPidsToPosts(KeyUtils.timeline(post_1.getUserId()), new Range());
//        Iterator<PostDto> iterator = collection.iterator();
//        PostDto result_1 = iterator.next();
//        Assert.assertThat(result_1, Is.is(post_1));
//    }
    @Test // DATAREDIS-423
    public void testQuerySort() {

        String content_1 = "test wish with luck";
        String userId_1 = "Shaohui";
        String id_1 = "postid_1";
        String createdDate = "20170721";
        String userNameDisplaying = "sjphiChina";
        PostDto post_1 = new PostDto(content_1, userId_1, userNameDisplaying);
        post_1.setId(id_1);
        post_1.setCreatedDate(createdDate);
        Map<String, String> postHash_1 = postMapper.toHash(post_1);
        RedisMap<String, String> map = new DefaultRedisMap<>(KeyUtils.post(post_1.getId()), template);
        map.putAll(postHash_1);

        getUserTimelineList(post_1.getUserId()).addFirst(post_1.getId());
//      PostDto result = postMapper.fromHash(template.<String, String> opsForHash().entries(KeyUtils.post(post_1.getId())));
//Assert.assertThat(result, Is.is(post_1));
         
        Collection<PostDto> collection = convertPidsToPosts(KeyUtils.timeline(post_1.getUserId()), new Range());
        Iterator<PostDto> iterator = collection.iterator();
        PostDto result_1 = iterator.next();
        Assert.assertThat(result_1, Is.is(post_1));
    }

    private RedisList<String> getUserTimelineList(String userId) {
        return new DefaultRedisList<>(KeyUtils.timeline(userId), template);
    }
    
    private Collection<PostDto> convertPidsToPosts(String key, Range range) {
        String pid = "pid:*->";
        final String pidKey = "#";
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
                .get(pid + userNameDisplaying).build();
                //.limit(range.begin, range.end).build();
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
        //RedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //Collection<PostDto> sort = template.sort(query, bulkMapper, redisSerializer);
        Collection<PostDto> sort = template.sort(query, bulkMapper);
        return sort;
    }
}
