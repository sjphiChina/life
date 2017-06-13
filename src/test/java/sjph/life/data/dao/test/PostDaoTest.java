package sjph.life.data.dao.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import sjph.life.data.dao.PostDao;
import sjph.life.data.model.Post;

@ContextConfiguration(locations = { "classpath:post-dao-test-context.xml" })
public class PostDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("no.transactional.annotation.PostDao")
    private PostDao postDao;

    /**
     * It tests createLaunchPreRecoveryRecord method.
     */
    @Test
    public void testCreateLaunchPreRecoveryRecord() {
        Post post = new Post(1l, "Hello World", new Date(), new Date(), 1l);
        Assert.assertEquals(postDao.createPost(post), 1);
    }
}
