package sjph.life.website.client;

import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import sjph.life.website.model.Post;
import sjph.life.website.model.PostDto;
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
        return null;
    }

    public PostDto findPost(String postId) {
        return null;
    }

    public Collection<PostDto> listPosts(Range range) {
        try {
            ResponseEntity<Collection<PostDto>> restExchange = postRestTemplate.exchange(
                    "http://lifepost/v1/post/list", HttpMethod.GET, null,new ParameterizedTypeReference<Collection<PostDto>>(){});

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
        return postDtoList;
    }

    public Collection<PostDto> listUserPosts(String userId, Range range) {
        return null;
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
