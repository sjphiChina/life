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
package sjph.life.post.service;

import java.util.Collection;

import sjph.life.post.Range;
import sjph.life.post.dto.PostDto;
import sjph.life.post.model.Post;

/**
 * Provides services for {@link PostDto}.
 * 
 * @author Shaohui Guo
 *
 */
public interface PostService {

    /**
     * @param post
     * @return the id of {@link Post} created
     */
    Long createPost(Post post);

    /**
     * @param postId
     * @return the PostDto requested
     */
    PostDto findPost(String postId);

    /**
     * @param range the range for post selection
     * @return a list of PostDto
     */
    Collection<PostDto> listPosts(Range range);

    /**
     * Get the user's timeline given the user id and range selected.
     * 
     * @param userId
     * @param range the range for post selection
     * @return a list of PostDto
     */
    Collection<PostDto> listUserTimeline(String userId, Range range);

    /**
     * Get all posts of the specific user and following given the user id and range selected.
     * 
     * @param userId
     * @param range the range for post selection
     * @return a list of PostDto
     */
    Collection<PostDto> listUserPosts(String userId, Range range);

    /**
     * @param postDto
     * @return true if the post specified was updated successfully
     */
    boolean updatePost(Post postDto);

    /**
     * @param postDto
     * @return true if the post specified was deleted successfully
     */
    boolean deletePost(PostDto postDto);

    /**
     * @param userId
     * @return true if posts specified by userId were deleted successfully
     */
    boolean deletePosts(String userId);
}
