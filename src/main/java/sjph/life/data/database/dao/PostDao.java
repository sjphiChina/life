package sjph.life.data.database.dao;

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
     * @param isDesc 
     * @return a list of Post
     */
    List<Post> findPosts(boolean isDesc);

    /**
     * @param postId post id
     * @return a Post
     */
    Post findPost(Long postId);

    /**
     * @param userId user id
     * @return a list of Post
     */
    List<Post> findPosts(Long userId);

    /**
     * @param post
     * @return the number of affected rows
     */
    int updatePost(Post post);

    /**
     * @param id postId
     * @return the number of affected rows
     */
    int deletePost(Long id);

    /**
     * @param userId
     * @return the number of affected rows
     */
    int deletePostByUserId(Long userId);
}
