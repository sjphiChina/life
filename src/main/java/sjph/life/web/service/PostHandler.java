package sjph.life.web.service;

import java.util.Date;

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

    @Autowired(required=true)
    private PostDao             postDao;

    public void createPost(PostBean postBean) {
        Post post = new Post(postBean.getContent(), 1l, new Date(), new Date());
        log.info("Create Post: " + post.toString());
        long id = postDao.createPost(post);
        post.setId(id);
        log.info("Created Post: " + post.toString());
    }

}
