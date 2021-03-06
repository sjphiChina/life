package sjph.life.app.website.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author shaohuiguo
 *
 */
public class DispatcherServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootApplicationContextConfig.class, WebApplicationContextConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //return new Class[] { WebApplicationContextConfig.class };
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
