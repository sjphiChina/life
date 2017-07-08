package sjph.life.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shaohuiguo
 *
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    // @RequestMapping("/login")
    // public String login(Model model) {
    // System.out.println("/login-------Work hard, Good luck!");
    // return "login";
    // //String message = "luck";
    // //return new ModelAndView("Login", "name", message);
    // }
    //
    // /**
    // * @param request
    // * @param res
    // * @return
    // */
    // @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    // public ModelAndView loginSubmit(HttpServletRequest request, HttpServletResponse res) {
    // System.out.println("Work hard, Good luck!");
    // String name = request.getParameter("name");
    // String password = request.getParameter("password");
    // System.out.println("password: " + password);
    // if (password.equals("admin")) {
    // String message = name;
    // return new ModelAndView("greetingview", "name", message);
    // }
    // else {
    // return new ModelAndView("errorpage", "message", "Sorry, username or password error");
    // }
    // }
}
