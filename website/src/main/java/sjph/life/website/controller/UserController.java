package sjph.life.website.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sjph.life.website.exception.UserNotFoundException;
import sjph.life.website.model.User;
import sjph.life.website.model.UserDto;
import sjph.life.website.model.UserState;
import sjph.life.website.service.RelationshipService;
import sjph.life.website.service.UserService;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired(required = true)
    private UserService         userService;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    @RequestMapping("/")
    public String showUser(@RequestParam("id") String userId, Model model) {
        UserDto user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public String followUser(@RequestParam("userId") String userId, Model model) {
        // command is a reserved request attribute name, now use <form> tag to show object data
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof AuthenticatedUser) {
//            User loginedUser = ((AuthenticatedUser) principal).getUserOfLife();
//            //UserDto loginedUserDto = new UserDto(loginedUser);
//            //model.addAttribute("loginedUser", loginedUserDto);
//            UserDto following = userService.findUser(userId);
//            model.addAttribute("user", following);
//            relationshipService.follow(String.valueOf(loginedUser.getId()), userId);
//            model.addAttribute("followed", true);
//            return "redirect:/" + following.getUserName();
//        }
        LOGGER.error("Cannot found user, userId=" + userId);
        throw new UserNotFoundException("Cannot found user, userId=" + userId);
    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    public String unfollowUser(@RequestParam("userId") String userId, Model model) {
        //UserDto userDto = null;
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof AuthenticatedUser) {
//            User loginedUser = ((AuthenticatedUser) principal).getUserOfLife();
//            //UserDto loginedUserDto = new UserDto(loginedUser);
//            //model.addAttribute("loginedUser", loginedUserDto);
//            UserDto followingDto = userService.findUser(userId);
//            //model.addAttribute("user", userDto);
//            relationshipService.unFollow(String.valueOf(loginedUser.getId()), userId);
//            model.addAttribute("followed", false);
//            return "redirect:/" + followingDto.getUserName();
//        }
        LOGGER.error("Cannot found user, userId=" + userId);
        throw new UserNotFoundException("Cannot found user, userId=" + userId);
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("HttpServletRequest content: ", request.getPathInfo());
        }
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        user.setCreatedDate(new Date());
        user.setModifiedDate(new Date());
        user.setUserState(UserState.ACTIVE);
        long userId = userService.createUser(user);
        MultipartFile profileImage = user.getProfileImage();
        // String rootDirectory = request.getSession().getServletContext().getRealPath("/");

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
