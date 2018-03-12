package sjph.life.user.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sjph.life.user.client.PersonRestTemplateClient;
import sjph.life.user.dto.UserDto;
import sjph.life.user.model.User;
import sjph.life.user.service.UserService;
import sjph.life.user.utils.UserContextHolder;


/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
@RestController
@RequestMapping(value="v1/user/authentication/{email}")
public class UserAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired(required = true)
    private UserService         userService;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public User findUser(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        logger.info("<<<<Find user: " + user.toString());
        return user;
    }

    @RequestMapping(value="/info",method = RequestMethod.GET)
    public String showUserInfo(@PathVariable("userId") String userId) {
        logger.debug("user access, correlationId: {}", UserContextHolder.getContext().getCorrelationId());
        
        UserDto user = userService.findUser(userId);
        String network = userService.findPersonNetwork(userId);
        return user.toString() + " " + network;
    }

}
