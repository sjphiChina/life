package sjph.life.data.dao;

import sjph.life.data.model.Post;

/**
 * @author shaoguo
 *
 */
public interface PostDao {

    /**
     * @param post
     * @return
     */
    int createPost(Post post);

    /**
     * @param id
     * @return
     */
    int deletePost(Long id);

    /**
     * @param id
     * @return
     */
    Post findPost(Long id);

    /**
     * @param post
     * @return
     */
    Post updatePost(Post post);
}
