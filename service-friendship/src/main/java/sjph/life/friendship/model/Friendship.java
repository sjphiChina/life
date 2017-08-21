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
package sjph.life.friendship.model;

import java.util.Set;

//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Model friendship table in Cassandra's life_user key space.
 * 
 * @author Shaohui Guo
 *
 */
//@Table("friendship")
@SuppressWarnings("javadoc")
public class Friendship {

  //  @PrimaryKey
    private final String      user_id;

    private final Set<String> followings;
    private final Set<String> followers;

    public Friendship(String user_id, Set<String> followings, Set<String> followers) {
        super();
        this.user_id = user_id;
        this.followings = followings;
        this.followers = followers;
    }

    public String getUser_id() {
        return user_id;
    }

    public Set<String> getFollowing() {
        return followings;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((followers == null) ? 0 : followers.hashCode());
        result = prime * result + ((followings == null) ? 0 : followings.hashCode());
        result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
        Friendship other = (Friendship) obj;
        if (followers == null) {
            if (other.followers != null)
                return false;
        }
        else if (!followers.equals(other.followers))
            return false;
        if (followings == null) {
            if (other.followings != null)
                return false;
        }
        else if (!followings.equals(other.followings))
            return false;
        if (user_id == null) {
            if (other.user_id != null)
                return false;
        }
        else if (!user_id.equals(other.user_id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Friendship [user_id=" + user_id + ", followings=" + followings + ", followers="
                + followers + "]";
    }
}
