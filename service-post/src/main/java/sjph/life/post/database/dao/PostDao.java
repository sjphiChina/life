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
package sjph.life.post.database.dao;

import java.util.List;

import sjph.life.post.model.Post;

/**
 * @author Shaohui Guo
 *
 */
public interface PostDao {

    //@formatter:off
/** CREATE operation */
    /**
     * @param post
     * @return the ID of {@link Post} table record created
     */
    Long createPost(Post post);

/** READ operation */
    /**
     * @param postId post id
     * @return the Post requested
     */
    Post findPost(Long postId);

/**
     * @param isDescOrder
     * @return a list of Post by desc order if isDescOrder is true else asc
     */
    List<Post> listPosts(boolean isDescOrder);

/**
     * @param userId user id
     * @param isDescOrder
     * @return a list of Post by userId and desc order if isDescOrder is true else asc
     */
    List<Post> listPosts(Long userId, boolean isDescOrder);

/** UPDATE operation */
    /**
     * @param post
     * @return the number of affected rows
     */
    int updatePost(Post post);

/** DELETE operation */
    /**
     * @param postId postId
     * @return the number of affected rows
     */
    int deletePost(Long postId);

    /**
     * @param userId
     * @return the number of affected rows
     */
    int deletePosts(Long userId);
    //@formatter:on
}
