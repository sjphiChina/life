package sjph.life.data.dao.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.model.Post;

/**
 * @author shaohuiguo
 *
 */
@ContextConfiguration(locations = { "classpath:post-dao-test-context.xml" })
public class PostDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("no.transactional.annotation.PostDao")
    private PostDao postDao;

    /**
     * It tests createPost method.
     */
    @Test
    public void testCreatePost() {
//        Instant instant = Instant.now();
//        System.out.println("Instant : " + instant);
//        ZonedDateTime time = instant.atZone(ZoneId.of("America/Los_Angeles"));
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HHmmss");
//        System.out.println("LocalDateTime : " + format.format(time));
        Date date = new Date();
        Post post = new Post("Hello World!", 1l, date, date);
        long id = postDao.createPost(post);
        Assert.assertEquals(id, 8l);
    }

    @Test
    public void testFindPost() {
        Post post = postDao.findPost(4l);
        
        Assert.assertEquals(post.getContent(), "Hello World!");
    }
}
