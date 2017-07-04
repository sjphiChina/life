package sjph.life.ui.controller.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sjph.life.model.Post;
import sjph.life.model.User;
import sjph.life.security.authentication.AuthenticatedUser;
import sjph.life.service.PostService;
import sjph.life.service.RelationshipService;
import sjph.life.service.UserService;
import sjph.life.service.exception.UserNotFoundException;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("{username}")
public class UserTimelineController {

    private static final Logger LOGGER = LogManager.getLogger(UserTimelineController.class);

    @Autowired(required = true)
    private PostService         postService;
    @Autowired(required = true)
    private UserService         userService;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProfile(@PathVariable String username, Model model) {
        User user = getUser(username);
        model.addAttribute("user", user);
        List<Post>list = postService.listPosts(user.getId());
        model.addAttribute("posts", list);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            User loginedUser = ((AuthenticatedUser) principal).getUserOfLife();
            LOGGER.info("loginedUser: ", loginedUser.getUserName());
            model.addAttribute("loginedUser", loginedUser);
            // TODO need to refine this, add it to user object
            long numberOfFollower = relationshipService.getNumberOfFollower(user.getId());
            model.addAttribute("numberOfFollower", numberOfFollower);
            if (loginedUser.getId() != user.getId()) {
                boolean followed = relationshipService.isFollowUser(user.getId(), loginedUser.getId());
                LOGGER.warn("Check if followed: " + followed);
                model.addAttribute("followed", followed);
            }
        }
        return "userTimeline";
    }

//    @RequestMapping("/")
//    public String showUser(@RequestParam("id") String userId, Model model) {
//        User user = userService.findUser(Long.valueOf(userId));
//        model.addAttribute("user", user);
//        return "user";
//    }

    private User getUser(String username) {
        try {
            User user = userService.findUserByUserName(username);
            return user;
        }
        catch (UserNotFoundException e) {
            // TODO right now just return null, in future, add security check logic
            return null;
        }
    }
}
