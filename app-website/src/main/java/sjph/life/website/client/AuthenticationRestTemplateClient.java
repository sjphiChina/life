package sjph.life.website.client;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.website.exception.UserNotFoundException;
import sjph.life.website.security.AuthenticatedTokenUserDeligate;
import sjph.life.website.security.AuthenticationResponse;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class AuthenticationRestTemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestTemplateClient.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Autowired
    RestTemplate                networkRestTemplate;
    
    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String encodedAuth = Base64Utils.encodeToString((username + ":" + password).getBytes(UTF_8));
              String authHeader = "Basic " + new String( encodedAuth );
              set( "Authorization", authHeader );
           }};
     }

    /**
     * @param email
     * @return the User requested
     * @throws UserNotFoundException
     */
    public AuthenticatedTokenUserDeligate getAuthenticatedToken(Authentication authentication) throws UserNotFoundException {
        LOGGER.debug("<<<< The requested user authentication is: " + authentication.getName() + ", password="+authentication.getCredentials().toString()+", details="+ authentication.getDetails().toString());
        try {
            HttpHeaders headers = createHeaders("life-app", "thisissecret");
            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("grant_type", "password");
            map.add("scope", "webclient");
            map.add("username", authentication.getName());
            map.add("password", authentication.getCredentials().toString());
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<AuthenticationResponse> restExchange = networkRestTemplate.exchange(
                    "http://lifeauthentication/auth/oauth/token", HttpMethod.POST, request,
                    AuthenticationResponse.class);

            AuthenticationResponse authenticationResponse = restExchange.getBody();
            AuthenticatedTokenUserDeligate authenticatedTokenUserDeligate = (AuthenticatedTokenUserDeligate) authentication;
            authenticatedTokenUserDeligate.setAccess_token(authenticationResponse.getAccess_token());
            authenticatedTokenUserDeligate.setRefresh_token(authenticationResponse.getRefresh_token());
            authenticatedTokenUserDeligate.setUserName(authentication.getName());
            authenticatedTokenUserDeligate.setExpires_in(authenticationResponse.getExpires_in());
            authenticatedTokenUserDeligate.setScope(authenticationResponse.getScope());
            authenticatedTokenUserDeligate.setToken_type(authenticationResponse.getToken_type());
            LOGGER.debug("<<<< The requested user authenticatedTokenUserDeligate is: " + authenticatedTokenUserDeligate.toString());
            
            return authenticatedTokenUserDeligate;
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return null;
    }

    
}
