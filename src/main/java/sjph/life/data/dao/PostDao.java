package sjph.life.data.dao;

import java.util.List;

import sjph.life.data.model.Post;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public interface PostDao {

    /**
     * @param post
     * @return the ID of {@link Post} table record created
     */
    Long createPost(Post post);

    /**
     * @return
     */
    List<Post> findPosts();

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
    int updatePost(Post post);

    /**
     * @param id postId
     * @return
     */
    int deletePost(Long id);

    /**
     * @param userId
     * @return
     */
    int deletePostByUserId(Long userId);
}
