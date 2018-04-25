package sjph.life.website.client;

import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.website.model.Post;
import sjph.life.website.model.PostDto;
import sjph.life.website.security.AuthenticatedTokenUserDeligate;
import sjph.life.website.service.Range;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class PostRestTemplateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRestTemplateClient.class);

    @Autowired
    RestTemplate                postRestTemplate;

    public Long createPost(Post post) {
        try {
            AuthenticatedTokenUserDeligate authenticatedTokenUserDeligate = (AuthenticatedTokenUserDeligate)SecurityContextHolder.getContext().getAuthentication();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authenticatedTokenUserDeligate.getToken_type()+" " + authenticatedTokenUserDeligate.getAccess_token());
            HttpEntity<Post> entity = new HttpEntity<>(post, headers);
            LOGGER.info(">>>>>>>>>>>>>>entity: " + entity.toString());
            ResponseEntity<Long> restExchange = postRestTemplate.exchange(
                    "http://lifepost/v1/post", HttpMethod.POST, entity, Long.class);
            LOGGER.info(">>>>>>>>>>>>>>Returned body: "+restExchange.getBody());
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

    public PostDto findPost(String postId) {
        return null;
    }

    public Collection<PostDto> listPosts(Range range) {
        try {
            AuthenticatedTokenUserDeligate authenticatedTokenUserDeligate = (AuthenticatedTokenUserDeligate)SecurityContextHolder.getContext().getAuthentication();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authenticatedTokenUserDeligate.getToken_type()+" " + authenticatedTokenUserDeligate.getAccess_token());
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            LOGGER.info(">>>>>>>>>>>>>>entity: " + entity.toString());
            
            ResponseEntity<Collection<PostDto>> restExchange = postRestTemplate.exchange(
                    "http://lifepost/v1/post/list", HttpMethod.GET, entity,new ParameterizedTypeReference<Collection<PostDto>>(){});
            LOGGER.info(">>>>>>>>>>>>>>Returned body: "+restExchange.getBody());
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

    public Collection<PostDto> listUserTimeline(String userId, Range range) {
        Collection<PostDto> postDtoList = null;
        try {
            AuthenticatedTokenUserDeligate authenticatedTokenUserDeligate = (AuthenticatedTokenUserDeligate)SecurityContextHolder.getContext().getAuthentication();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authenticatedTokenUserDeligate.getToken_type()+" " + authenticatedTokenUserDeligate.getAccess_token());
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            LOGGER.info(">>>>>>>>>>>>>>entity: " + entity.toString());
            ResponseEntity<Collection<PostDto>> restExchange = postRestTemplate.exchange(
                    "http://lifepost/v1/post/timeline/{userId}", HttpMethod.GET, entity,new ParameterizedTypeReference<Collection<PostDto>>(){}, userId);
            LOGGER.info(">>>>>>>>>>>>>>Returned body: "+restExchange.getBody());
            return restExchange.getBody();
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return postDtoList;
    }

    public Collection<PostDto> listUserPosts(String userId, Range range) {
        Collection<PostDto> postDtoList = null;
        try {
            AuthenticatedTokenUserDeligate authenticatedTokenUserDeligate = (AuthenticatedTokenUserDeligate)SecurityContextHolder.getContext().getAuthentication();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authenticatedTokenUserDeligate.getToken_type()+" " + authenticatedTokenUserDeligate.getAccess_token());
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            LOGGER.info(">>>>>>>>>>>>>>entity: " + entity.toString());
            ResponseEntity<Collection<PostDto>> restExchange = postRestTemplate.exchange(
                    "http://lifepost/v1/post/{userId}/list", HttpMethod.GET, entity,new ParameterizedTypeReference<Collection<PostDto>>(){}, userId);
            LOGGER.info(">>>>>>>>>>>>>>Returned body: "+restExchange.getBody());
            return restExchange.getBody();
        }
        catch (RestClientException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        catch (IllegalStateException e) {
            LOGGER.error("Cannot finish the request: ", e);
        }
        return postDtoList;
    }

    public boolean updatePost(Post post) {
        return false;
    }

    // TODO will do displaying remove in the future, no need to really remove the post from db
    public boolean deletePost(PostDto postDto) {
        return false;
    }

    public boolean deletePosts(String userId) {
        return true;
        // if (postDao.deletePosts(Long.valueOf(userId)) >= 1) {
        // return true;
        // }
        // return false;
    }
}
