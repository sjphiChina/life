package sjph.life.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shaohuiguo
 *
 */
@Controller
public class HomeController {

    /**
     * @param model model
     * @return url
     */
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to Web Store!");
        model.addAttribute("tagline", "The one and only amazing web store");
        model.addAttribute("name", "郭韶慧");
        return "welcome";
        //return "redirect:/posts/list";
    }
}
