package sjph.life.data.database.dao;

import java.util.List;

import sjph.life.data.model.Post;

/**
 * @author shaoguo
 *
 */
public interface PostDao {

    //@formatter:off
/** CREATE operation */
    /**
     * @param post
     * @return the ID of {@link Post} table record created
     */
    Long createPost(Post post);

/** READ operation */
    /**
     * @param postId post id
     * @return a Post
     */
    Post findPost(Long postId);

/**
     * @param isDescOrder
     * @return a list of Post
     */
    List<Post> listPosts(boolean isDescOrder);

/**
     * @param userId user id
     * @param isDescOrder
     * @return a list of Post
     */
    List<Post> listPosts(Long userId, boolean isDescOrder);

/** UPDATE operation */
    /**
     * @param post
     * @return the number of affected rows
     */
    int updatePost(Post post);

/** DELETE operation */
    /**
     * @param postId postId
     * @return the number of affected rows
     */
    int deletePost(Long postId);

    /**
     * @param userId
     * @return the number of affected rows
     */
    int deletePosts(Long userId);
    //@formatter:on
}
