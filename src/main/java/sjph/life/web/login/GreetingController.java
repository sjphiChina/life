/**
 * 
 */
package sjph.life.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name,
            ModelMap model) {
        System.out.println("Work hard, Good luck!");
        model.addAttribute("name", name);
        return "greetingview";
    }
}
