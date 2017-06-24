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
@Service
public class PostHandler {
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

    public List<Post> listPosts() {
        List<Post> list = postDao.findPosts(true);
        return list;
    }

    public Post getPost(long postId) {
        try {
            return postDao.findPost(postId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(postId, "No post found.", e);
        }
    }
}
