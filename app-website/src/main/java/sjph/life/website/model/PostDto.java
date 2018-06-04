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
package sjph.life.website.model;

import java.io.Serializable;

/**
 * DTO for {@link Post}. It is also used by cache.
 * 
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public class PostDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2768777320418358806L;

    private String            id;
    private String            content;
    private String            userId;
    private String            createdDate;
    private String            userNameDisplaying;

    public PostDto() {
    }

    public PostDto(String content, String userId, String userNameDisplaying) {
        this.content = content;
        this.userId = userId;
        this.userNameDisplaying = userNameDisplaying;
    }

    public PostDto(Post post) {
        this.id = String.valueOf(post.getId());
        this.content = post.getContent();
        this.userId = String.valueOf(post.getUserId());
        this.createdDate = String.valueOf(post.getCreatedDate().getTime());
        this.userNameDisplaying = post.getUserName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserNameDisplaying() {
        return userNameDisplaying;
    }

    public void setUserNameDisplaying(String userNameDisplaying) {
        this.userNameDisplaying = userNameDisplaying;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result
                + ((userNameDisplaying == null) ? 0 : userNameDisplaying.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostDto other = (PostDto) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        }
        else if (!content.equals(other.content))
            return false;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        }
        else if (!createdDate.equals(other.createdDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        if (userNameDisplaying == null) {
            if (other.userNameDisplaying != null)
                return false;
        }
        else if (!userNameDisplaying.equals(other.userNameDisplaying))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PostDto [id=" + id + ", content=" + content + ", userId=" + userId
                + ", createdDate=" + createdDate + ", userNameDisplaying=" + userNameDisplaying
                + "]";
    }
}
