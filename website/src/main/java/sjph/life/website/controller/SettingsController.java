package sjph.life.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shaohuiguo
 *
 */
@Controller
public class SettingsController {

    @RequestMapping("/settings")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to Web Store!");
        model.addAttribute("tagline", "The one and only amazing web store");

        return "settings";
    }
}
