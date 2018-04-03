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
package sjph.life.post.controller;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sjph.life.post.Range;
import sjph.life.post.dto.PostDto;
import sjph.life.post.model.Post;
import sjph.life.post.service.PostService;

/**
 * Rest controller for {@link PostDto} operations.
 * 
 * @author Shaohui Guo
 *
 */
@RestController
@RequestMapping(value = "v1/post")
public class PostRestController {

    private static final Logger LOGGER   = LoggerFactory.getLogger(PostRestController.class);

    Long                        userId   = 1l;
    String                      userName = "sjph";

    @Autowired(required = true)
    private PostService         postService;

    // TODO add exception handler
    /**
     * @param post body of {@link Post}
     * @return id of post created
     */
    @RequestMapping(method = RequestMethod.POST)
    //@ResponseStatus(value = HttpStatus.CREATED)
    public Long createPost(@RequestBody Post post) {
        // TODO  will switch to PostDto later
        // disable this, I will move the encode/decode logic to client side, server side just use
        // the same code
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        // end
        post.setCreatedDate(new Date());
        post.setModifiedDate(new Date());
        return postService.createPost(post);
    }

    /**
     * @return a list of {@link PostDto}
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "Work hard, Good luck!";
    }
    
    
    /**
     * @return a list of {@link PostDto}
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<PostDto> showPosts() {
        Collection<PostDto> list = postService.listPosts(new Range());
        LOGGER.info("The size of all posts is " + list.size());
        return list;
    }

    /**
     * @param id the user id of user
     * @return a list of {@link PostDto}
     */
    @RequestMapping(value = "/timeline/{userId}", method = RequestMethod.GET)
    public Collection<PostDto> showUserTimeline(@PathVariable("userId") String id) {
        Collection<PostDto> list = postService.listUserTimeline(id, new Range());
        LOGGER.info("The size of all posts is " + list.size());
        return list;
    }

    /**
     * @param postId post id
     * @return {@link Post}
     */
    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public PostDto showPost(@PathVariable(value = "postId") String postId) {
        PostDto post = postService.findPost(postId);
        return post;
    }

    /**
     * @param post the updated {@link Post}
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePost(@RequestBody Post post) {
        post.setModifiedDate(new Date());
        postService.updatePost(post);
    }

//    /**
//     * @param postId the id of {@link Post} to be deleted
//     */
//    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void deletePost(@PathVariable(value = "postId") String postId) {
//        PostDto postDto = postService.findPost(postId);
//        postService.deletePost(postDto);
//    }

    // private String encodeText(String text, String encodingSet) {
    // try {
    // return TextCodingHelper.encodeText(text, WebConfig.CHARACTER_ENCODING_SET);
    // }
    // catch (UnsupportedEncodingException e) {
    // String message = "Cannot encode the content passed.";
    // logger.error(message, e);
    // throw new RequestFailedException(message, e);
    // }
    // }
}
