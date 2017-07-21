/**
 * 
 */
package sjph.life.platform.cache;

/**
 * Will reconsider how to define and use this interface.
 * 
 * @author shaohuiguo
 * @param <K> 
 * @param <V> 
 *
 */
public interface CacheService<K, V> {

    /**
     * Add String value to cache.
     * 
     * @param key the key
     * @param value the String value
     */
    void addValue(K key, V value);

    /**
     * Get String value from cache
     * 
     * @param key the key
     * @return the String value
     */
    V getValue(K key);
}
