package sjph.life.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shaohuiguo
 *
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        System.out.println("/login-------Work hard, Good luck!");
        return "login";
        //String message = "luck";
        //return new ModelAndView("Login", "name", message);
    }

    /**
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    public ModelAndView loginSubmit(HttpServletRequest request, HttpServletResponse res) {
        System.out.println("Work hard, Good luck!");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        System.out.println("password: " + password);
        if (password.equals("admin")) {
            String message = name;
            return new ModelAndView("greetingview", "name", message);
        }
        else {
            return new ModelAndView("errorpage", "message", "Sorry, username or password error");
        }
    }
}
