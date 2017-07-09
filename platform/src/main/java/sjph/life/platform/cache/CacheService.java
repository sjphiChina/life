/**
 * 
 */
package sjph.life.platform.cache;

/**
 * @author shaohuiguo
 *
 */
public interface CacheService {

    /**
     * Add String value to cache.
     * 
     * @param key the key
     * @param value the String value
     */
    void addValue(String key, String value);

    /**
     * Get String value from cache
     * 
     * @param key the key
     * @return the String value
     */
    String getValue(String key);
}
