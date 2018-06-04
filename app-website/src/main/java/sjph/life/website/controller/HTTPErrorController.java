package sjph.life.website.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shaohui Guo
 *
 */
@Controller
public class HTTPErrorController extends AbstractErrorController {

    @Autowired
    ObjectMapper                objectMapper;

    private static final Logger LOGGER     = LoggerFactory.getLogger(HTTPErrorController.class);
    private static final String ERROR_PATH = "/error";

    public HTTPErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getErrorPath() {
        LOGGER.error(">>>>>>>>>>Reach HTTPErrorController");
        return "/errors/404";
    }

    @RequestMapping(ERROR_PATH)
    public ModelAndView getErrorPath(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.error(">>>>>>>>>>Reach getErrorPath");
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(request, false));
        Throwable cause = getCause(request);
        int status = (Integer) model.get("status");
        // 错误信息
        String message = (String) model.get("message");
        // 友好提示
        String errorMessage = getErrorMessage(cause);

        String requestPath = (String) model.get("path");

        // 后台打印日志信息方方便查错
        LOGGER.info(status + ":" + message, cause);
        LOGGER.info("requestPath" + ":" + requestPath);

        // 后台打印日志信息方方便查错
        LOGGER.info(message, cause);
        response.setStatus(status);
        if (!isJsonRequest(request)) {
            ModelAndView view = new ModelAndView("404");
            view.addAllObjects(model);
            view.addObject("status", status);
            view.addObject("errorMessage", errorMessage);
            view.addObject("cause", cause);
            return view;

        }
        Map error = new HashMap();
        error.put("success", false);
        error.put("errorMessage", getErrorMessage(cause));
        error.put("message", message);
        writeJson(response, error);
        return null;

    }

    protected boolean isJsonRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri.endsWith(".json")) {
            return true;
        }
        return (request.getHeader("accept").contains("application/json")
                || (request.getHeader("X-Requested-With") != null
                        && request.getHeader("X-Requested-With").contains("XMLHttpRequest")));
    }

    protected void writeJson(HttpServletResponse response, Map error) {
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
        catch (IOException e) {
            // ignore
        }
    }

    protected String getErrorMessage(Throwable ex) {
        /* 不给前端显示详细错误 */
        return "服务器错误,请联系管理员";
    }

    protected Throwable getCause(HttpServletRequest request) {
        Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = ((ServletException) error).getCause();
            }
        }
        return error;
    }
}
