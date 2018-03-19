package sjph.life.security.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.model.user.User;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class PersonRestTemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestTemplateClient.class);

    @Autowired
    RestTemplate                networkRestTemplate;

    // @HystrixCommand(fallbackMethod = "buildFallbackNetwork",
    // commandProperties={
    // @HystrixProperty(name="execution.isolation.thread.timeoutInMillisecondes", value="15000")}
    // )
    public User findUserByEmail(String email) {
        LOGGER.info("<<<< The requested user email is: " + email);
        try {
            ResponseEntity<User> restExchange = networkRestTemplate.exchange(
                    "http://lifeuser/v1/user/authentication/{email}/", HttpMethod.GET, null,
                    User.class, email);

            return restExchange.getBody();
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return null;
    }

    @SuppressWarnings("unused")
    private String buildFallbackNetwork(String userId) {
        String message = "We see connection timeout to friendship and no network detail of userId="
                + userId + " is returned.";
        LOGGER.debug(message);
        return message;
    }
}
