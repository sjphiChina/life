package sjph.life;


import org.springframework.context.ApplicationEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: emeeks
 * Date: Aug 30, 2006
 * Time: 2:24:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractWebContext extends XmlWebApplicationContext {

    protected Resource getResourceByPath(String path) {
        // try servlet context first
        ServletContextResource scr = new ServletContextResource(getServletContext(), path);
        if (scr.exists()) {
            return scr;
        }
        else {
            return new ClassPathResource(path);
        }
    }

    public void publishEvent(ApplicationEvent event) {
        super.publishEvent(event);
    }
}
