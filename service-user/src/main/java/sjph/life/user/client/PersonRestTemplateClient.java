package sjph.life.user.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import sjph.life.user.controller.UserController;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class PersonRestTemplateClient {

    private static final Logger LOGGER = LogManager.getLogger(PersonRestTemplateClient.class);
    
    @Autowired
    RestTemplate networkRestTemplate;

//    @HystrixCommand(fallbackMethod = "buildFallbackNetwork",
//            commandProperties={
//                     @HystrixProperty(name="execution.isolation.thread.timeoutInMillisecondes", value="15000")}
//    )
     public String getNetwork(String userId) {

        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getnetwork", HttpMethod.GET, null,
                    String.class, userId);

            return restExchange.getBody();
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return "Person's network info is unavailable.";
    }
    
    @SuppressWarnings("unused")
    private String buildFallbackNetwork(String userId){
        String message = "We see connection timeout to friendship and no network detail of userId="+userId+" is returned.";
        LOGGER.debug(message);
        return message;
    }
}
