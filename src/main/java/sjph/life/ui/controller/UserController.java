package sjph.life.ui.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sjph.life.model.User;
import sjph.life.model.state.UserState;
import sjph.life.security.authentication.AuthenticatedUser;
import sjph.life.service.RelationshipService;
import sjph.life.service.UserService;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired(required = true)
    private UserService         userService;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    @RequestMapping("/")
    public String showUser(@RequestParam("id") String userId, Model model) {
        User user = userService.findUser(Long.valueOf(userId));
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public String followUser(@RequestParam("userId") String userId, Model model) {
        User user = null;
        // command is a reserved request attribute name, now use <form> tag to show object data
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            User loginedUser = ((AuthenticatedUser) principal).getUserOfLife();
            model.addAttribute("loginedUser", loginedUser);
            User follower = loginedUser;
            User followee = userService.findUser(Long.valueOf(userId));
            user = followee;
            model.addAttribute("user", user);
            relationshipService.createRelationship(followee.getId(), follower.getId());
            model.addAttribute("followed", true);
        }
        // TODO later handle null pointer issue
        return "redirect:/" + user.getUserName();
    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    public String unfollowUser(@RequestParam("userId") String userId, Model model) {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            User loginedUser = ((AuthenticatedUser) principal).getUserOfLife();
            model.addAttribute("loginedUser", loginedUser);
            User follower = loginedUser;
            User followee = userService.findUser(Long.valueOf(userId));
            user = followee;
            model.addAttribute("user", user);
            relationshipService.deleteFollwee(followee.getId(), follower.getId());
            model.addAttribute("followed", false);
        }
        return "redirect:/" + user.getUserName();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getAddUserForm(Model model) {
        // command is a reserved request attribute name, now use <form> tag to show object data
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processAddPostForm(@ModelAttribute("user") User user,
            HttpServletRequest request) {
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        user.setCreatedDate(new Date());
        user.setModifiedDate(new Date());
        user.setUserState(UserState.ACTIVE);
        long userId = userService.createUser(user);
        MultipartFile profileImage = user.getProfileImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                // contentImage.transferTo(
                // new File(rootDirectory + "resources/images/" + postId + ".png"));
                profileImage.transferTo(
                        new File("/data/local/life/data/images/users/" + userId + ".png"));
            }
            catch (Exception e) {
                throw new RuntimeException("User Image saving failed", e);
            }
        }

        return "redirect:/posts/list";
    }
}
