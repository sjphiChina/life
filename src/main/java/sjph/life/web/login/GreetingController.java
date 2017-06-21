/**
 * 
 */
package sjph.life.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author shaohuiguo
 *
 */
@Controller
public class GreetingController {

    /**
     * @param name name
     * @param model ?
     * @return page
     */
//    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
//    public String greeting(Model model, RedirectAttributes redirectAttributes) {
//        System.out.println("greeting() == Work hard, Good luck!");
//        model.addAttribute("greeting", "Welcome to Web Store!");
//        model.addAttribute("tagline", "The one and only amazing web store");
//
//        redirectAttributes.addFlashAttribute("greeting", "Welcome to Web Store!");
//        redirectAttributes.addFlashAttribute("tagline", "The one and only amazing web store");
//        
//        //return "redirect:/life/greetingview";
//        return "life/greetingview";
//    }
    
    @RequestMapping(value = "/greetingview")
    public String greetingview() {
        System.out.println("greetingview() == Work hard, Good luck!");
        return "greetingview";
    }
}
