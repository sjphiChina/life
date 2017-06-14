package sjph.life.data.dao;

import java.util.List;

import sjph.life.data.model.Post;

/**
 * @author shaoguo
 *
 */
public interface PostDao {

    /**
     * @param post
     * @return the ID of {@link Post} table record created
     */
    Long createPost(Post post);

    /**
     * @param id
     * @return
     */
    int deletePost(Long id);

    /**
     * @param postId post id
     * @return
     */
    Post findPost(Long postId);

    /**
     * @param userId user id
     * @return
     */
    List<Post> findPosts(Long userId);

    /**
     * @param post
     * @return
     */
    Post updatePost(Post post);
}
