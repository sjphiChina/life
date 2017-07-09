package sjph.life.platform.cache.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sjph.life.platform.cache.CacheService;

/**
 * @author shaohuiguo
 *
 */
@Service("redisCacheService")
public class RedisCacheServiceImpl implements CacheService {

    private static final Logger LOGGER = LogManager.getLogger(RedisCacheServiceImpl.class);
    // inject the actual template
    @Autowired(required = true)
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String>  template;

    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
//    @Autowired(required = true)
//    private ListOperations<String, String> listOps;

    @Override
    public void addValue(String key, String value) {
        LOGGER.info("Add value: " + value);
        template.boundValueOps(key).set(value);

    }

    @Override
    public String getValue(String key) {
        return template.boundValueOps(key).get();
    }

}
