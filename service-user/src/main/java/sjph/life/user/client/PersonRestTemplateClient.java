package sjph.life.user.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.user.controller.UserController;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class PersonRestTemplateClient {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    
    @Autowired
    RestTemplate networkRestTemplate;

    public String getNetwork(String userId) {

        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getNetwork", HttpMethod.GET, null,
                    String.class, userId);

            return restExchange.getBody();
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
            return "";
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
            return "";
        }
    }
}
