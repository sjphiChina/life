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
package sjph.life.platform.cache.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Shaohui Guo
 *
 */
@Configuration
public class RedisCacheConfig {

    /**
     * Define the cache client name. Format:"Life-" + ${application.name} + "-" + ${server.id}.
     * For example, for rest app, the name is: Life-Rest-abc123.
     */
    private static String        CACHE_CLIENT_NAME = "Life-";

    // temp use, will get it from config file
    //private static final String  HOST              = "localhost";
    //private static final Integer PORT              = 6379;
    //private static final String  AUTH_PAS          = "admin";
    private static final Integer MAX_TOTAL         = 200;
    private static final Integer MAX_IDLE          = 100;
    private static final Long    MAX_WAIT          = 60000l;
    //private static final Integer TIME_OUT          = 6000000;

    /**
     * @return a customized {@link JedisPoolConfig}
     */
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(MAX_TOTAL);
        jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
        jedisPoolConfig.setMaxIdle(MAX_IDLE);
        return jedisPoolConfig;
    }

    /**
     * @return a customized {@link JedisConnectionFactory}.
     */
    @Bean("generalJedisConnectionFactory")
    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        // TODO just use temp name right now, will use config later
        jedisConnectionFactory.setClientName(CACHE_CLIENT_NAME + "test-01");
        jedisConnectionFactory.setPoolConfig(getJedisPoolConfig());
        return jedisConnectionFactory;
    }

    /**
     * @return a {@link RedisTemplate}.
     */
    @Bean("jedisRedisTemplate")
    public RedisTemplate<String, String> getRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
