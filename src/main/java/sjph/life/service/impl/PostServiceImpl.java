package sjph.life.service.impl;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sjph.life.model.Post;
import sjph.life.model.dao.PostDao;
import sjph.life.platform.util.algorithm.MergeSort;
import sjph.life.service.PostService;
import sjph.life.service.RelationshipService;
import sjph.life.ui.exception.PostNotFoundException;

/**
 * @author shaohuiguo
 *
 */
@Service
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LogManager.getLogger(PostServiceImpl.class);

    @Autowired(required = true)
    private PostDao             postDao;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    @Override
    public long createPost(Post post) {
        LOGGER.info("Create Post: " + post.toString());
        // post.setContent(encodeText(post.getContent()));
        long id = postDao.createPost(post);
        post.setId(id);
        LOGGER.info("Created Post: " + post.toString());
        return id;
    }

    @Override
    public Post findPost(long postId) {
        try {
            Post post = postDao.findPost(postId);
            // post.setContent(decodeText(post.getContent()));
            return post;
        }
        catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(postId, "No post found.", e);
        }
    }

    @Override
    public List<Post> listPosts() {
        List<Post> list = postDao.listPosts(true);
        // for (Post post : list) {
        // post.setContent(decodeText(post.getContent()));
        // }
        return list;
    }

    @Override
    public List<Post> listPosts(Long userId) {
        return postDao.listPosts(userId, true);
    }

    @Override
    public List<Post> listPostsAll(Long userId) {
        List<Post> list = postDao.listPosts(userId, true);
        List<Long> followeeList = relationshipService.getFollwees(userId);
        List<Post>[] arrayList = new List[followeeList.size() + 1];
        // There are two ways to this merge:
        // 1. PriorityQueue, merge the head of each list and traverse
        // 2. Merge all sorted list
        // Now use the 2.
        int index = 0;
        arrayList[index++] = list;
        for (long followeeId : followeeList) {
            List<Post> followerPostList = postDao.listPosts(followeeId, true);
            arrayList[index++] = followerPostList;
        }
        MergeSort<Post> mergeSort = new MergeSort<>(new Comparator<Post>() {
            @Override
            public int compare(Post a, Post b) {
                long diff = b.getCreatedDate().getTime() - a.getCreatedDate().getTime();
                if (diff >= 0) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        List<Post> result = mergeSort.mergeKLists(arrayList);
        return result;
    }

    @Override
    public boolean updatePost(Post post) {
        // post.setContent(encodeText(post.getContent()));
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

    // private String encodeText(String text) {
    // try {
    // if (text != null) {
    // text = TextCodingHelper.encodeText(text, ServiceConfig.CHARACTER_ENCODING_SET);
    // }
    // return text;
    // }
    // catch (UnsupportedEncodingException e) {
    // String message = "Cannot finish request.";
    // logger.error(message, e);
    // throw new ServiceRequestFailedException(message, e);
    // }
    // }
    //
    // private String decodeText(String text) {
    // try {
    // if (text != null) {
    // return TextCodingHelper.decodeText(text, ServiceConfig.CHARACTER_ENCODING_SET);
    // }
    // return text;
    // }
    // catch (UnsupportedEncodingException e) {
    // String message = "Cannot finish request.";
    // logger.error(message, e);
    // throw new ServiceRequestFailedException(message, e);
    // }
    // }
}
