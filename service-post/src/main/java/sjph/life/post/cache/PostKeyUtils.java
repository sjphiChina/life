/**
 * 
 */
package sjph.life.service;

import sjph.life.model.Post;

/**
 * Simple class keeping all the key patterns related to {@link Post} to avoid the proliferation of
 * Strings through the code.
 * 
 * @author shaohuiguo
 *
 */
public abstract class PostKeyUtils {

    static String post(String pid) {
        return "pid:" + pid;
    }

    static String timeline() {
        return "timeline";
    }

    static String globalPostId() {
        return "global:pid";
    }
}
