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
package sjph.life.post.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sjph.life.post.Range;
import sjph.life.post.cache.PostCacheHandler;
import sjph.life.post.database.dao.PostDao;
import sjph.life.post.dto.PostDto;
import sjph.life.post.exception.PostNotFoundException;
import sjph.life.post.model.Post;
import sjph.life.post.service.PostService;
import sjph.life.post.service.SocialNetworkService;
import sjph.life.util.time.DateUtil;
import sjph.life.util.time.LifeDateFormat;

/**
 * @author Shaohui Guo
 *
 */
@Service
public class PostServiceImpl implements PostService {
    private static final Logger  LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired(required = true)
    private PostDao              postDao;
    @Autowired(required = true)
    private SocialNetworkService socialNetworkService;
    @Autowired(required = true)
    private PostCacheHandler     postCacheHandler;

    @Override
    public Long createPost(Post post) {
        LOGGER.info("Create Post: " + post.toString());
        // post.setContent(encodeText(post.getContent()));
        long id = postDao.createPost(post);
        post.setId(id);
        DateUtil dateUtil = new DateUtil(LifeDateFormat.YMDHMSAZ);
        postCacheHandler.addPost(convertPostToPostDto(post, dateUtil));
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
            DateUtil dateUtil = new DateUtil(LifeDateFormat.YMDHMSAZ);
            PostDto postDto = convertPostToPostDto(post, dateUtil);
            postCacheHandler.addPost(postDto);
            // post.setContent(decodeText(post.getContent()));
            return postDto;
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
            DateUtil dateUtil = new DateUtil(LifeDateFormat.YMDHMSAZ);
            postDtoList = convertPostToPostDto(postList, dateUtil);
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
            DateUtil dateUtil = new DateUtil(LifeDateFormat.YMDHMSAZ);
            postDtoList = convertPostToPostDto(postList, dateUtil);
            postCacheHandler.loadPosts(postDtoList);
        }
        // for (Post post : list) {
        // post.setContent(decodeText(post.getContent()));
        // }
        return postDtoList;
    }

    @Override
    public Collection<PostDto> listUserPosts(String userId, Range range) {
        return listUserTimeline(userId, range);
        // temporarily comment following due to #10
        // // TODO need more robust logic: lots of null pointer checking
        // Collection<PostDto> postDtoList = postCacheHandler.getUserTimeline(userId, range);
        // if (postDtoList == null || postDtoList.isEmpty()) {
        // Collection<Post> list = postDao.listPosts(Long.valueOf(userId), true);
        // postDtoList = convertPostToPostDto(list);
        // postCacheHandler.loadPosts(postDtoList);
        // }
        // Collection<String> followeeList = socialNetworkService.getFollowing(userId);
        // if (followeeList != null && !followeeList.isEmpty()) {
        // // There are two ways to this merge:
        // // 1. PriorityQueue, merge the head of each list and traverse
        // // 2. Merge all sorted list
        // // Now use the 2.
        // // TODO all just for temp change
        // @SuppressWarnings("unchecked")
        // // avoid it for -1.
        // //Collection<PostDto>[] postDtoArray = new ArrayList[followeeList.size() + 1];
        // Collection<PostDto>[] postDtoArray = new ArrayList[followeeList.size()];
        // int index = 0;
        // if (postDtoList != null && !postDtoList.isEmpty()) {
        // postDtoArray[index++] = postDtoList;
        // } else {
        // postDtoArray[index++] = new LinkedList<>();
        // }
        // for (String followingId : followeeList) {
        // // temp fix for java.lang.NumberFormatException: For input string: "["-1""
        // // this is the design issue, will fix it later
        // if (followingId.contains("[") || followingId.contains("]")) {
        // LOGGER.error(">>>>>>>>>>Bad followingId: " + followingId);
        // continue;
        // }
        // Collection<PostDto> tempList = listUserTimeline(followingId, range);
        // if (tempList != null && !tempList.isEmpty()) {
        // postDtoArray[index++] = tempList;
        // }
        // }
        // MergeSort<PostDto> mergeSortPostDto = new MergeSort<>(new Comparator<PostDto>() {
        // @Override
        // public int compare(PostDto a, PostDto b) {
        // long diff = Long.valueOf(b.getCreatedDate()) - Long.valueOf(a.getCreatedDate());
        // if (diff >= 0) {
        // return 1;
        // }
        // return -1;
        // }
        // });
        // Collection<PostDto> result = null;
        // if (postDtoArray.length > 1) {
        // result = mergeSortPostDto.mergeKLists(postDtoArray);
        // } else {
        // result = postDtoArray[0];
        // }
        // return result;
        // }
        // return postDtoList;
    }

    @Override
    public boolean updatePost(Post post) {
        DateUtil dateUtil = new DateUtil(LifeDateFormat.YMDHMSAZ);
        postCacheHandler.deletePost(convertPostToPostDto(post, dateUtil));
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

    private PostDto convertPostToPostDto(Post post, DateUtil dateUtil) {
        return new PostDto(post, dateUtil);
    }

    private Collection<PostDto> convertPostToPostDto(Collection<Post> postList, DateUtil dateUtil) {
        Collection<PostDto> postDtoList = new ArrayList<>(postList.size());
        for (Post post : postList) {
            postDtoList.add(convertPostToPostDto(post, dateUtil));
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
