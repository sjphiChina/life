package sjph.life.web.service;

import java.util.List;

import sjph.life.data.model.Post;

/**
 * @author shaohuiguo
 *
 */
public interface PostService {

    /**
     * @param post
     * @return the ID of {@link Post} table record created
     */
    long createPost(Post post);

    /**
     * @param postId
     * @return the Post requested
     */
    Post findPost(long postId);

    /**
     * @return a list of Post
     */
    List<Post> listPosts();

    /**
     * @param userId
     * @return a list of Post by userId
     */
    List<Post> listPosts(Long userId);

    /**
     * @param post
     * @return true if the post specified was updated successfully else false
     */
    boolean updatePost(Post post);

    /**
     * @param postId
     * @return true if the post specified was updated successfully else false
     */
    boolean deletePost(Long postId);

    /**
     * @param userId
     * @return true if posts specified by userId were updated successfully else false
     */
    boolean deletePosts(Long userId);
}
