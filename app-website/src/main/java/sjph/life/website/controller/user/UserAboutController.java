package sjph.life.website.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sjph.life.model.user.UserDto;
import sjph.life.website.exception.UserNotFoundException;
import sjph.life.website.service.UserService;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@Controller
@RequestMapping("{username}/about")
public class UserAboutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAboutController.class);

    @Autowired(required = true)
    private UserService         userService;

    @RequestMapping(method = RequestMethod.GET)
    public String showAbout(@PathVariable String username, Model model) {
        UserDto user = getUser(username);
        model.addAttribute("user", user);
        return "userAbout";
    }

    private UserDto getUser(String username) {
        try {
            UserDto userDto = userService.findUserByUserName(username);
            return userDto;
        }
        catch (UserNotFoundException e) {
            // TODO right now just return null, in future, add security check logic
            LOGGER.error(
                    "Cannot find user, userName=" + username + ", exception: " + e.getMessage());
            return null;
        }
    }
}
