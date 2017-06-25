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
     * @return
     */
    long createPost(Post post);

    /**
     * @param postId
     * @return
     */
    Post findPost(long postId);

    /**
     * @return
     */
    List<Post> listPosts();

    /**
     * @param userId
     * @return
     */
    List<Post> listPosts(Long userId);

    /**
     * @param post
     * @return
     */
    boolean updatePost(Post post);

    /**
     * @param postId
     * @return
     */
    boolean deletePost(Long postId);

    /**
     * @param userId
     * @return
     */
    boolean deletePosts(Long userId);
}
