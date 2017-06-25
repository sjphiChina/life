package sjph.life.web.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sjph.life.data.model.Post;
import sjph.life.web.exception.PostNotFoundException;
import sjph.life.web.service.PostHandler;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("posts")
public class PostController {

    private static final Logger logger   = Logger.getLogger(PostController.class);

    Long                        userId   = 1l;
    String                      userName = "sjph";

    @Autowired(required = true)
    private PostHandler         postHandler;

    @RequestMapping("/list")
    public String showPosts(Model model) {
        List<Post> list = postHandler.listPosts();
        logger.info("The size of all posts is " + list.size());
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
        long postId = postHandler.createPost(post.getContent(), userId, userName);
        MultipartFile contentImage = post.getContentImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        if (contentImage != null && !contentImage.isEmpty()) {
            try {
//                contentImage.transferTo(
//                        new File(rootDirectory + "resources/images/" + postId + ".png"));
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
        model.addAttribute("post", postHandler.findPost(Long.valueOf(postId)));
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
}
