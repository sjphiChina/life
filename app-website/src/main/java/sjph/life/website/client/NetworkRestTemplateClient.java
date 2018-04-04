package sjph.life.website.client;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.website.model.Friendship;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class NetworkRestTemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkRestTemplateClient.class);

    @Autowired
    RestTemplate                networkRestTemplate;

    public void follow(@PathVariable("userId") String userId,
            @PathVariable("followingId") String followingId) {
        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getnetwork", HttpMethod.GET, null,
                    String.class, userId);
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }

    }

    @RequestMapping(value = "/unfollow/{followingId}", method = RequestMethod.PUT)
    public void unfollow(@PathVariable("userId") String userId,
            @PathVariable("followingId") String followingId) {
        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getnetwork", HttpMethod.GET, null,
                    String.class, userId);

        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }

    }

    @RequestMapping(value = "/getfollowers", method = RequestMethod.GET)
    public Collection<String> getFollower(@PathVariable("userId") String userId) {
        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getfollowers", HttpMethod.GET, null,
                    String.class, userId);

            String followerString = restExchange.getBody();
            String[] followerArray = followerString.split(",");
            Collection<String> followerList = new ArrayList<>(followerArray.length);
            for (String follower: followerArray) {
                followerList.add(follower);
            }
            return followerList;
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/getfollowings", method = RequestMethod.GET)
    public Collection<String> getFollowing(@PathVariable("userId") String userId) {
        try {
            ResponseEntity<String> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getfollowings", HttpMethod.GET, null,
                    String.class, userId);

            String followingString = restExchange.getBody();
            String[] followingArray = followingString.split(",");
            Collection<String> followingList = new ArrayList<>(followingArray.length);
            for (String following: followingArray) {
                followingList.add(following);
            }
            return followingList;
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return new ArrayList<>();
    }
    
    // @HystrixCommand(fallbackMethod = "buildFallbackNetwork",
    // commandProperties={
    // @HystrixProperty(name="execution.isolation.thread.timeoutInMillisecondes", value="15000")}
    // )
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

    public Friendship getFriendship(String userId) {

        try {
            ResponseEntity<Friendship> restExchange = networkRestTemplate.exchange(
                    "http://lifefriendship/v1/friendship/{userId}/getnetwork", HttpMethod.GET, null,
                    Friendship.class, userId);

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
