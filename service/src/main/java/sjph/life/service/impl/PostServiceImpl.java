/*
 * Copyright 2017 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sjph.life.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sjph.life.model.Post;
import sjph.life.model.dao.PostDao;
import sjph.life.platform.util.algorithm.MergeSort;
import sjph.life.service.PostCacheHandler;
import sjph.life.service.PostService;
import sjph.life.service.Range;
import sjph.life.service.RelationshipService;
import sjph.life.service.dto.PostDto;
import sjph.life.service.exception.PostNotFoundException;

/**
 * @author Shaohui Guo
 *
 */
@Service
public class PostServiceImpl implements PostService {
    private static final Logger      LOGGER = LogManager.getLogger(PostServiceImpl.class);

    @Autowired(required = true)
    private PostDao                  postDao;
    @Autowired(required = true)
    private RelationshipService      relationshipService;
    @Autowired(required = true)
    private PostCacheHandler         postCacheHandler;

    @Override
    public Long createPost(Post post) {
        LOGGER.info("Create Post: " + post.toString());
        // post.setContent(encodeText(post.getContent()));
        long id = postDao.createPost(post);
        post.setId(id);
        postCacheHandler.addPost(new PostDto(post));
        LOGGER.info("Created Post: " + post.toString());
        return post.getId();
    }

    @Override
    public PostDto findPost(String postId) {
        try {
            if (postCacheHandler.isPostValid(postId)) {
                return postCacheHandler.getPost(postId);
            }
            Post post = postDao.findPost(Long.valueOf(postId));
            postCacheHandler.addPost(new PostDto(post));
            // post.setContent(decodeText(post.getContent()));
            return new PostDto(post);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("No post found.", e);
        }
    }

    @Override
    public Collection<PostDto> listPosts(Range range) {
        Collection<PostDto> postDtoList = postCacheHandler.getTimeline(range);
        if (postDtoList != null && !postDtoList.isEmpty()) {
            return postDtoList;
        }
        Collection<Post> postList = postDao.listPosts(true);
        if (postList != null && !postList.isEmpty()) {
            postDtoList = convertPostToPostDto(postList);
            postCacheHandler.loadPosts(postDtoList);
        }
        // for (Post post : list) {
        // post.setContent(decodeText(post.getContent()));
        // }
        return postDtoList;
    }

    @Override
    public Collection<PostDto> listUserTimeline(String userId, Range range) {
        Collection<PostDto> postDtoList = postCacheHandler.getUserTimeline(userId, range);
        if (postDtoList != null && !postDtoList.isEmpty()) {
            return postDtoList;
        }
        Collection<Post> postList = postDao.listPosts(Long.valueOf(userId), true);
        if (postList != null && !postList.isEmpty()) {
            postDtoList = convertPostToPostDto(postList);
            postCacheHandler.loadPosts(postDtoList);
        }
        // for (Post post : list) {
        // post.setContent(decodeText(post.getContent()));
        // }
        return postDtoList;
    }

    @Override
    public Collection<PostDto> listUserPosts(String userId, Range range) {
        // TODO need more robust logic: lots of null pointer checking
        Collection<PostDto> postDtoList = postCacheHandler.getUserTimeline(userId, range);
        if (postDtoList == null || postDtoList.isEmpty()) {
            Collection<Post> list = postDao.listPosts(Long.valueOf(userId), true);
            postDtoList = convertPostToPostDto(list);
            postCacheHandler.loadPosts(postDtoList);
        }
        Collection<String> followeeList = relationshipService.getFollwees(userId);
        if (followeeList != null && !followeeList.isEmpty()) {
            // There are two ways to this merge:
            // 1. PriorityQueue, merge the head of each list and traverse
            // 2. Merge all sorted list
            // Now use the 2.
            @SuppressWarnings("unchecked")
            Collection<PostDto>[] postDtoArray = new ArrayList[followeeList.size() + 1];
            int index = 0;
            postDtoArray[index++] = postDtoList;
            for (String followingId : followeeList) {
                Collection<PostDto> tempList = listUserTimeline(followingId, range);
                postDtoArray[index++] = tempList;
            }
            MergeSort<PostDto> mergeSortPostDto = new MergeSort<>(new Comparator<PostDto>() {
                @Override
                public int compare(PostDto a, PostDto b) {
                    long diff = Long.valueOf(b.getCreatedDate()) - Long.valueOf(a.getCreatedDate());
                    if (diff >= 0) {
                        return 1;
                    }
                    return -1;
                }
            });
            Collection<PostDto> result = mergeSortPostDto.mergeKLists(postDtoArray);
            return result;
        }
        return postDtoList;
    }

    @Override
    public boolean updatePost(Post post) {
        postCacheHandler.deletePost(new PostDto(post));
        // post.setContent(encodeText(post.getContent()));
        if (postDao.updatePost(post) == 1) {
            return true;
        }
        return false;
    }

    // TODO will do displaying remove in the future, no need to really remove the post from db
    @Override
    public boolean deletePost(PostDto postDto) {
        postCacheHandler.deletePost(postDto);
        if (postDao.deletePost(Long.valueOf(postDto.getId())) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePosts(String userId) {
        postCacheHandler.deletePost(userId);
        return true;
        // if (postDao.deletePosts(Long.valueOf(userId)) >= 1) {
        // return true;
        // }
        // return false;
    }

    private Collection<PostDto> convertPostToPostDto(Collection<Post> postList) {
        Collection<PostDto> postDtoList = new ArrayList<>(postList.size());
        for (Post post : postList) {
            postDtoList.add(new PostDto(post));
        }
        return postDtoList;
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
