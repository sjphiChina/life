package sjph.life.website.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import sjph.life.website.client.AuthenticationRestTemplateClient;

public class LifeWebsiteAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private AuthenticationRestTemplateClient authenticationRestTemplateClient;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        this.logger.debug("Passed authentication in website, will get token from Authentication service, then forward the page ");
        AuthenticatedTokenUserDeligate authenticatedTokenUser = authenticationRestTemplateClient.getAuthenticatedToken(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
