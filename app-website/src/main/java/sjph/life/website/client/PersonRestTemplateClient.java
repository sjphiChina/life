package sjph.life.website.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.model.user.User;
import sjph.life.model.user.UserDto;
import sjph.life.website.exception.UserNotFoundException;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class PersonRestTemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestTemplateClient.class);

    @Autowired
    RestTemplate                networkRestTemplate;

    /**
     * @param user
     * @return the ID of {@link User} table record created
     */
    public Long createUser(User user) {
        return 1l;
    }

    /**
     * @param userId
     * @return the User requested
     * @throws UserNotFoundException
     */
    public UserDto findUser(String userId) throws UserNotFoundException {
        LOGGER.info("<<<< The requested user email is: " + userId);
        try {
            ResponseEntity<UserDto> restExchange = networkRestTemplate.exchange(
                    "http://lifeuser/v1/user/id/{userId}/", HttpMethod.GET, null,
                    UserDto.class, userId);

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

    /**
     * @param email
     * @return the User requested
     * @throws UserNotFoundException
     */
    public User findUserByEmail(String email) throws UserNotFoundException {
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

    /**
     * @param userName
     * @return the User requested
     * @throws UserNotFoundException
     */
    public UserDto findUserByUserName(String userName) {
        LOGGER.info("<<<< The requested user userName is: " + userName);
        try {
            ResponseEntity<UserDto> restExchange = networkRestTemplate.exchange(
                    "http://lifeuser/v1/user/username/{userName}/info", HttpMethod.GET, null,
                    UserDto.class, userName);

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

    /**
     * @param user
     * @return true if the user specified was updated successfully else false
     */
    public boolean updateUser(User user) {
        return true;
    }

    /**
     * @param userId
     * @return true if the user specified was updated successfully else false
     */
    public boolean deleteUser(String userId) {
        return true;
    }
}
