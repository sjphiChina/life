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
package sjph.life.website.controller;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sjph.life.model.user.User;
import sjph.life.security.authentication.AuthenticatedUser;
import sjph.life.website.exception.PostNotFoundException;
import sjph.life.website.exception.RequestFailedException;
import sjph.life.website.model.Post;
import sjph.life.website.model.PostDto;
import sjph.life.website.service.PostService;
import sjph.life.website.service.Range;
import sjph.life.website.service.RelationshipService;

/**
 * Controller for {@link PostDto} operations.
 * 
 * @author Shaohui Guo
 *
 */
@Controller
@RequestMapping("posts")
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired(required = true)
    private PostService         postService;
    @Autowired(required = true)
    private RelationshipService relationshipService;

    /**
     * @param model model
     * @return view of posts
     */
    @RequestMapping("/list")
    public String showPosts(Model model) {
        Collection<PostDto> list = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            User user = ((AuthenticatedUser) principal).getUserOfLife();
            model.addAttribute("loginedUser", user);
            list = postService.listUserPosts(String.valueOf(user.getId()), new Range());
            // TODO need to refine this, add it to user object
            long numberOfFollower = relationshipService
                    .getNumberOfFollower(String.valueOf(user.getId()));
            model.addAttribute("numberOfFollower", numberOfFollower);
        }
        else {
            list = postService.listPosts(new Range());
        }
            if (list != null && list.size() != 0) {

                LOGGER.info("The size of all posts is " + list.size());
                model.addAttribute("posts", list);
            } else {
                model.addAttribute("posts", new LinkedList<PostDto>());
            }
        Post post = new Post();
        model.addAttribute("post", post);
        return "posts";
    }

    /**
     * @param userId string of user id
     * @param model model
     * @return view of posts
     */
    @RequestMapping("/list/{user}")
    public String showPosts(@RequestParam("id") String userId, Model model) {
        Collection<PostDto> list = postService.listUserTimeline(userId, new Range());
        LOGGER.info("The size of all posts is " + list.size());
        model.addAttribute("posts", list);
        return "posts";
    }

    /**
     * @param model model
     * @return view of addPost
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddPostForm(Model model) {
        // TODO will switch to PostDto later
        // command is a reserved request attribute name, now use <form> tag to show object data
        Post post = new Post();
        model.addAttribute("post", post);
        return "addPost";
    }

    /**
     * @param post attribute of {@link Post} object
     * @param request HttpServletRequest
     * @return url of posts/list
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddPostForm(@ModelAttribute("post") Post post,
            HttpServletRequest request) {
        //if (LOGGER.isDebugEnabled()) {
            LOGGER.info("HttpServletRequest content: ", request.getPathInfo());
        //}
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof AuthenticatedUser) {
            user = ((AuthenticatedUser) principal).getUserOfLife();
        }
        else {
            throw new RequestFailedException("Cannot find user.");
        }
        // encodeText(post.getContent(), WebConfig.CHARACTER_ENCODING_SET);
        // Here I still use the original content, the code above is just for checking.
        post.setUserId(user.getId());
        post.setUserName(user.getUserName());
        post.setCreatedDate(new Date());
        post.setModifiedDate(new Date());
        long postId = postService.createPost(post);
        MultipartFile contentImage = post.getContentImage();
        // String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        if (contentImage != null && !contentImage.isEmpty()) {
            try {
                // contentImage.transferTo(
                // new File(rootDirectory + "resources/images/" + postId + ".png"));
                contentImage.transferTo(
                        new File("/data/local/life/data/images/posts/" + postId + ".png"));
            }
            catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }

        return "redirect:/posts/list";
    }

    /**
     * @param postId string of post id
     * @param model model
     * @return view of post
     */
    @RequestMapping("/post")
    public String getPost(@RequestParam("id") String postId, Model model) {
        model.addAttribute("post", postService.findPost(postId));
        return "post";
    }

    /**
     * @param reqest HttpServletRequest
     * @param exception PostNotFoundException
     * @return ModelAndView
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest reqest, PostNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        // mav.addObject("invalidProductId", exception.getPostId());
        mav.addObject("exception", exception);
        mav.addObject("url", reqest.getRequestURL() + "?" + reqest.getQueryString());
        mav.setViewName("postNotFound");
        return mav;
    }

    /**
     * @param request HttpServletRequest
     * @param exception RequestFailedException
     * @return ModelAndView
     */
    @ExceptionHandler(RequestFailedException.class)
    public ModelAndView handleError(HttpServletRequest request, RequestFailedException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
        mav.setViewName("postNotFound");
        return mav;
    }

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
