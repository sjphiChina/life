/**
 * 
 */
package sjph.life.platform.cache.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author shaohuiguo
 *
 */
@Configuration
public class RedisCacheConfig {

    /**
     * @return a {@link JedisConnectionFactory}
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    /**
     * @return a {@link JedisConnectionFactory}
     */
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
