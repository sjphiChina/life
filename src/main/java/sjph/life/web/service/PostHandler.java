package sjph.life.web.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.model.Post;

/**
 * @author shaohuiguo
 *
 */
@Service
public class PostHandler {
    private static final Logger log = LogManager.getLogger(PostHandler.class);

    @Autowired(required = true)
    private PostDao             postDao;

    public void createPost(String content, Long userId, String userName) {
        Post post = new Post(content, userId, new Date(), new Date(), userName);
        log.info("Create Post: " + post.toString());
        long id = postDao.createPost(post);
        post.setId(id);
        log.info("Created Post: " + post.toString());
    }

    public List<Post> listPosts() {
        List<Post> list = postDao.findPosts(true);

        return list;
    }
}
