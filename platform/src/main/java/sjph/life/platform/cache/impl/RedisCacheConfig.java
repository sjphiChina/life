/**
 * 
 */
package sjph.life.platform.cache.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author shaohuiguo
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
    private static final String  HOST              = "localhost";
    private static final Integer PORT              = 6379;
    private static final String  AUTH_PAS          = "admin";
    private static final Integer MAX_TOTAL         = 200;
    private static final Integer MAX_IDLE          = 100;
    private static final Long    MAX_WAIT          = 60000l;
    private static final Integer TIME_OUT          = 6000000;

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
    @Bean
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
    @Bean("redisJedisTemplate")
    public RedisTemplate<?, ?> getRedisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
