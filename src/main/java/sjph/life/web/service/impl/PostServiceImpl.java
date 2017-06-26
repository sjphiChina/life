package sjph.life.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.model.Post;
import sjph.life.platform.service.text.TextCodingHelper;
import sjph.life.web.exception.PostNotFoundException;
import sjph.life.web.service.PostService;
import sjph.life.web.service.ServiceConfig;
import sjph.life.web.service.exception.ServiceRequestFailedException;

/**
 * @author shaohuiguo
 *
 */
@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = Logger.getLogger(PostServiceImpl.class);

    @Autowired(required = true)
    private PostDao             postDao;

    @Override
    public long createPost(Post post) {
        logger.info("Create Post: " + post.toString());
        long id = postDao.createPost(post);
        post.setId(id);
        logger.info("Created Post: " + post.toString());
        return id;
    }

    @Override
    public Post findPost(long postId) {
        try {
            Post post = postDao.findPost(postId);
            return post;
        }
        catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(postId, "No post found.", e);
        }
    }

    @Override
    public List<Post> listPosts() {
        List<Post> list = postDao.listPosts(true);
        return list;
    }

    @Override
    public List<Post> listPosts(Long userId) {
        List<Post> list = postDao.listPosts(userId, true);
        return list;
    }

    @Override
    public boolean updatePost(Post post) {
        if (postDao.updatePost(post) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deletePost(Long postId) {
        if (postDao.deletePost(postId) == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deletePosts(Long userId) {
        if (postDao.deletePosts(userId) >= 1) {
            return true;
        }
        else {
            return false;
        }
    }

//    private String encodeText(String text) {
//        try {
//            if (text != null) {
//                text = TextCodingHelper.encodeText(text, ServiceConfig.CHARACTER_ENCODING_SET);
//            }
//            return text;
//        }
//        catch (UnsupportedEncodingException e) {
//            String message = "Cannot finish request.";
//            logger.error(message, e);
//            throw new ServiceRequestFailedException(message, e);
//        }
//    }
//
//    private String decodeText(String text) {
//        try {
//            if (text != null) {
//                return TextCodingHelper.decodeText(text, ServiceConfig.CHARACTER_ENCODING_SET);
//            }
//            return text;
//        }
//        catch (UnsupportedEncodingException e) {
//            String message = "Cannot finish request.";
//            logger.error(message, e);
//            throw new ServiceRequestFailedException(message, e);
//        }
//    }
}
