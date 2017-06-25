package sjph.life.web.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.model.Post;
import sjph.life.web.exception.PostNotFoundException;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Service
public class PostServiceImpl {
    private static final Logger log = Logger.getLogger(PostHandler.class);

    @Autowired(required = true)
    private PostDao             postDao;

    public long createPost(String content, Long userId, String userName) {
        Post post = new Post(content, userId, new Date(), new Date(), userName);
        log.info("Create Post: " + post.toString());
        long id = postDao.createPost(post);
        post.setId(id);
        log.info("Created Post: " + post.toString());
        return id;
    }

    public Post findPost(long postId) {
        try {
            return postDao.findPost(postId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(postId, "No post found.", e);
        }
    }

    public List<Post> listPosts() {
        List<Post> list = postDao.listPosts(true);
        return list;
    }

    public List<Post> listPosts(Long userId) {
        List<Post> list = postDao.listPosts(userId, true);
        return list;
    }

    public boolean updatePost(Post post) {
        post.setModifiedDate(new Date());
        if (postDao.updatePost(post) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deletePost(Long postId) {
        if (postDao.deletePost(postId) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deletePosts(Long userId) {
        if (postDao.deletePosts(userId) >= 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
