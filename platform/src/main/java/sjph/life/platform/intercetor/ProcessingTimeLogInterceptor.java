package sjph.life.platform.intercetor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shaohuiguo
 *
 */
public class ProcessingTimeLogInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER   = LogManager.getLogger(ProcessingTimeLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        String path = request.getRequestURL() + queryString;

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        LOGGER.info(String.format("%s millisecond taken to process the request %s.",
                (endTime - startTime), path));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception exceptionIfAny) {
        String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        String path = request.getRequestURL() + queryString;

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        LOGGER.info(String.format("%s millisecond taken to finish the request %s.",
                (endTime - startTime), path));
    }
}
