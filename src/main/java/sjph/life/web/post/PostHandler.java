package sjph.life.web.post;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.model.Post;

/**
 * @author shaohuiguo
 *
 */
public class PostHandler {
    private static final Logger log = LogManager.getLogger(PostHandler.class);

    private PostDao             postDao;

    @Required
    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

    public void createPost(PostBean postBean) {
        Post post = new Post(postBean.getContent(), 1l, new Date(), new Date());
        log.info("Create Post: " + post.toString());
        long id = postDao.createPost(post);
        post.setId(id);
        log.info("Created Post: " + post.toString());
    }

}
