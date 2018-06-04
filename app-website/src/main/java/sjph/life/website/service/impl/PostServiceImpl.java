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
package sjph.life.website.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjph.life.website.client.PostRestTemplateClient;
import sjph.life.website.exception.PostNotFoundException;
import sjph.life.website.model.Post;
import sjph.life.website.model.PostDto;
import sjph.life.website.service.PostService;
import sjph.life.website.service.Range;

/**
 * @author Shaohui Guo
 *
 */
@Service
public class PostServiceImpl implements PostService {
    private static final Logger      LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired(required = true)
    private PostRestTemplateClient                  postRestTemplateClient;

    @Override
    public Long createPost(Post post) {
        LOGGER.info("Create Post: " + post.toString());
        // post.setContent(encodeText(post.getContent()));
        long id = postRestTemplateClient.createPost(post);
        post.setId(id);
        LOGGER.info("Created Post: " + post.toString());
        return post.getId();
    }

    @Override
    public PostDto findPost(String postId) {
        return postRestTemplateClient.findPost(postId);
    }

    @Override
    public Collection<PostDto> listPosts(Range range) {
        Collection<PostDto> postList = postRestTemplateClient.listPosts(range);
        return postList;
    }

    @Override
    public Collection<PostDto> listUserTimeline(String userId, Range range) {
        Collection<PostDto> postDtoList = postRestTemplateClient.listUserTimeline(userId, range);
        
        return postDtoList;
    }

    @Override
    public Collection<PostDto> listUserPosts(String userId, Range range) {
        // TODO need more robust logic: lots of null pointer checking
        Collection<PostDto> postDtoList = postRestTemplateClient.listUserPosts(userId, range);
        return postDtoList;
    }

    @Override
    public boolean updatePost(Post post) {
        return postRestTemplateClient.updatePost(post);
    }

    // TODO will do displaying remove in the future, no need to really remove the post from db
    @Override
    public boolean deletePost(PostDto postDto) {
        return postRestTemplateClient.deletePost(postDto);
    }

    @Override
    public boolean deletePosts(String userId) {

        return postRestTemplateClient.deletePosts(userId);
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
