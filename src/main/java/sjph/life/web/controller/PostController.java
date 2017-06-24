package sjph.life.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sjph.life.data.model.Post;
import sjph.life.web.service.PostHandler;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("posts")
public class PostController {

    Long                userId   = 1l;
    String              userName = "sjph";

    @Autowired(required = true)
    private PostHandler postHandler;

    @RequestMapping("/list")
    public String showPosts(Model model) {
        model.addAttribute("posts", postHandler.listPosts());
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
                contentImage.transferTo(
                        new File(rootDirectory + "resources/images/" + postId + ".png"));
            }
            catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }

        return "redirect:/posts/list";
    }

    @RequestMapping("/post")
    public String getPost(@RequestParam("id") String postId, Model model) {
       model.addAttribute("post", postHandler.getPost(Long.valueOf(postId)));
       return "post";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("emp") Post post) {
        // write code to save emp object
        // here, we are displaying emp object to prove emp has data
        System.out.println(post.getContent());
        postHandler.createPost(post.getContent(), userId, userName);
        // return new ModelAndView("empform","command",emp);//will display object data
        return new ModelAndView("redirect:/postsView");// will redirect to viewemp request mapping
    }
}
