package sjph.life.website.controller.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sjph.life.model.User;
import sjph.life.service.UserService;
import sjph.life.service.exception.UserNotFoundException;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("{username}/about")
public class UserAboutController {

    private static final Logger LOGGER = LogManager.getLogger(UserAboutController.class);

    @Autowired(required = true)
    private UserService         userService;

    @RequestMapping(method = RequestMethod.GET)
    public String showAbout(@PathVariable String username, Model model) {
        User user = getUser(username);
        model.addAttribute("user", user);
        return "userAbout";
    }

    private User getUser(String username) {
        try {
            User user = userService.findUserByUserName(username);
            return user;
        }
        catch (UserNotFoundException e) {
            // TODO right now just return null, in future, add security check logic
            LOGGER.error(
                    "Cannot find user, userName=" + username + ", exception: " + e.getMessage());
            return null;
        }
    }
}
