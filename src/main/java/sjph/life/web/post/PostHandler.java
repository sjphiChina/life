package sjph.life.web.post;

import org.springframework.beans.factory.annotation.Required;

import sjph.life.data.database.dao.PostDao;

/**
 * @author shaohuiguo
 *
 */
public class PostHandler {

    private PostDao postDao;

    @Required
    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

}
