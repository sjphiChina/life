package sjph.life.post.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Since Spring security doesn't work (see issue#13), temporarily add this filter to do user
 * authentication for all http requests except GET request.
 *
 * @author Shaohui Guo
 */
@Component
public class InspectHeaderFilter implements Filter {

    private static final Logger logger        = LoggerFactory.getLogger(InspectHeaderFilter.class);
    private static String       AUTHORIZATION = "Authorization";
    private static String       BEARER_TYPE   = "Bearer";

    @Autowired
    RestTemplate                restTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logger.info(">>>>>>>>>>>>>>I AM HITTING THE header filter of Post: "
                + httpServletRequest.getRequestURL() + " remoteAddr: "
                + httpServletRequest.getRemoteAddr() + " remoteHost: "
                + httpServletRequest.getRemoteHost());
        if (!httpServletRequest.getMethod().equals(HttpMethod.GET.toString())) {
            String token = extractHeaderToken(httpServletRequest);
            if (token != null) {
                Map<String, Object> map = authenticateToken(token);
                checkAuthenticationResult(map, token);
            }
            else {
                throw new IOException(
                        "Operation is denied since access token is empty or invalid.");
            }
        }
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce
                                            // that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }

    private Map<String, Object> authenticateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(AUTHORIZATION, BEARER_TYPE + " " + token);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> restExchange = restTemplate.exchange(
                    "http://lifeauthentication/auth/user", HttpMethod.GET, entity, Map.class);
            return restExchange.getBody();
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    private void checkAuthenticationResult(Map<String, Object> map, String accessToken)
            throws IOException {
        if (map.containsKey("error")) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("userinfo returned error: " + map.get("error"));
            }
            throw new IOException("The access token is invalid: " + accessToken);
        }
    }
}
