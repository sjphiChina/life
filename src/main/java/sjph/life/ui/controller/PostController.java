package sjph.life.ui.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sjph.life.model.Post;
import sjph.life.model.User;
import sjph.life.security.authentication.AuthenticatedUser;
import sjph.life.service.PostService;
import sjph.life.service.RelationshipService;
import sjph.life.ui.exception.PostNotFoundException;
import sjph.life.ui.exception.RequestFailedException;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("posts")
public class PostController {

    private static final Logger LOGGER   = LogManager.getLogger(PostController.class);

    @Autowired(required = true)
    private PostService         postService;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    @RequestMapping("/list")
    public String showPosts(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> list = null;
        if (principal instanceof AuthenticatedUser) {
            User user = ((AuthenticatedUser) principal).getUserOfLife();
            model.addAttribute("loginedUser", user);
            list = postService.listPostsAll(user.getId());
            // TODO need to refine this, add it to user object
            long numberOfFollower = relationshipService.getNumberOfFollower(user.getId());
            model.addAttribute("numberOfFollower", numberOfFollower);
        }
        else {
            list = postService.listPosts();
        }
        LOGGER.info("The size of all posts is " + list.size());
        model.addAttribute("posts", list);
        Post post = new Post();
        model.addAttribute("post", post);
        return "posts";
    }

    @RequestMapping("/list/{user}")
    public String showPosts(@RequestParam("id") String userId, Model model) {
        Long userIdLong = Long.valueOf(userId);
        List<Post> list = postService.listPosts(userIdLong);
        LOGGER.info("The size of all posts is " + list.size());
        model.addAttribute("posts", list);
        return "posts";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddPostForm(Model model) {
        // command is a reserved request attribute name, now use <form> tag to show object data
        Post post = new Post();
        model.addAttribute("post", post);
        return "addPost";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddPostForm(@ModelAttribute("post") Post post,
            HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof AuthenticatedUser) {
            user = ((AuthenticatedUser) principal).getUserOfLife();
        }
        else {
            throw new RequestFailedException("Cannot find user.");
        }
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        post.setUserId(user.getId());
        post.setUserName(user.getUserName());
        post.setCreatedDate(new Date());
        post.setModifiedDate(new Date());
        long postId = postService.createPost(post);
        MultipartFile contentImage = post.getContentImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        if (contentImage != null && !contentImage.isEmpty()) {
            try {
                // contentImage.transferTo(
                // new File(rootDirectory + "resources/images/" + postId + ".png"));
                contentImage.transferTo(
                        new File("/data/local/life/data/images/posts/" + postId + ".png"));
            }
            catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }

        return "redirect:/posts/list";
    }

    @RequestMapping("/post")
    public String getPost(@RequestParam("id") String postId, Model model) {
        model.addAttribute("post", postService.findPost(Long.valueOf(postId)));
        return "post";
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, PostNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidProductId", exception.getPostId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
        mav.setViewName("postNotFound");
        return mav;
    }

    @ExceptionHandler(RequestFailedException.class)
    public ModelAndView handleError(HttpServletRequest req, RequestFailedException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
        mav.setViewName("postNotFound");
        return mav;
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
