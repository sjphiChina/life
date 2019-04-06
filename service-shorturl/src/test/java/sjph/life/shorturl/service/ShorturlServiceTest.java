/**
 * 
 */
package sjph.life.shorturl.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shaohuiguo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShorturlServiceTest {

    @Autowired
    private ShorturlService shorturlService;

    @Test
    public void testShortConversionFullProcess() {
        long id = 11157;
        long result = shorturlService.converShortUrlToId(shorturlService.convertLongToShort(id));
        Assert.assertEquals(11157, result);
    }
}
