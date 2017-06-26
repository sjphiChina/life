package sjph.life.rest.controller;

import java.util.Date;
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

import sjph.life.model.Post;
import sjph.life.model.service.PostService;

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
    private PostService         postService;

    // TODO add exception handler
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createPost(@RequestBody Post post) {
        // disable this, I will move the encode/decode logic to client side, server side just use
        // the same code
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        // end
        post.setUserId(userId);
        post.setUserName(userName);
        post.setCreatedDate(new Date());
        post.setModifiedDate(new Date());
        return postService.createPost(post);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Post> showPosts() {
        List<Post> list = postService.listPosts();
        logger.info("The size of all posts is " + list.size());
        return list;
    }

    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<Post> showPosts(@PathVariable String userId) {
        List<Post> list = postService.listPosts(Long.valueOf(userId));
        logger.info("The size of all posts is " + list.size());
        return list;
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public Post showPost(@PathVariable(value = "postId") String postId) {
        Post post = postService.findPost(Long.valueOf(postId));
        return post;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePost(@RequestBody Post post) {
        post.setModifiedDate(new Date());
        postService.updatePost(post);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@PathVariable(value = "postId") String postId) {
        postService.deletePost(Long.valueOf(postId));
    }

    // private String encodeText(String text, String encodingSet) {
    // try {
    // return TextCodingHelper.encodeText(text, WebConfig.CHARACTER_ENCODING_SET);
    // }
    // catch (UnsupportedEncodingException e) {
    // String message = "Cannot encode the content passed.";
    // logger.error(message, e);
    // throw new RequestFailedException(message, e);
    // }
    // }
}
