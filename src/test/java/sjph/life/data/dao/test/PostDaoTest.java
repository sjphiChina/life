package sjph.life.data.dao.test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import sjph.life.data.dao.PostDao;
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
        Instant instant = Instant.now();

        System.out.println("Instant : " + instant);

        ZonedDateTime time = instant.atZone(ZoneId.of("America/Los_Angeles"));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HHmmss");

        System.out.println("LocalDateTime : " + format.format(time));
        Post post = new Post(1l, "Hello World", 1l, time, time);
        Assert.assertEquals(postDao.createPost(post), 1l);
    }

    @Test
    public void testFindPost() {
        Post post = new Post(1l, "Hello World", 1l, new ZonedDateTime(), new ZonedDateTime());
        Assert.assertEquals(postDao.findPost(1l), 1);
    }
}
