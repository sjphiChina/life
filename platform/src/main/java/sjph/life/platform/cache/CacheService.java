/**
 * 
 */
package sjph.life.platform.cache;

/**
 * @author shaohuiguo
 *
 */
public interface CacheService {

    void addValue(String key, String value);
    String getValue(String key);
}
