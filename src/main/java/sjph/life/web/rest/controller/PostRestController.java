package sjph.life.web.rest.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sjph.life.data.model.Post;
import sjph.life.web.service.PostHandler;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@RestController
@RequestMapping(value = "rest/posts")
public class PostRestController {

    private static final Logger logger   = Logger.getLogger(PostRestController.class);

    Long                        userId   = 1l;
    String                      userName = "sjph";

    @Autowired(required = true)
    private PostHandler         postHandler;
// TODO add exception handler
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Post> showPosts() {
        List<Post> list = postHandler.listPosts();
        logger.info("The size of all posts is " + list.size());
        return list;
    }

    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<Post> showPosts(@PathVariable String userId) {
        List<Post> list = postHandler.listPosts(Long.valueOf(userId));
        logger.info("The size of all posts is " + list.size());
        return list;
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public Post showPost(@PathVariable(value = "postId") String postId) {
        Post post = postHandler.findPost(Long.valueOf(postId));
        return post;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPost(@RequestBody Post post) {
        postHandler.createPost(post.getContent(), userId, userName);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePost(@RequestBody Post post) {
        postHandler.updatePost(post);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@PathVariable(value = "postId") String postId) {
        postHandler.deletePost(Long.valueOf(postId));
    }
}
