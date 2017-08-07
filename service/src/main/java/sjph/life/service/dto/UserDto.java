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
package sjph.life.service.dto;

import java.io.Serializable;

import sjph.life.model.User;

/**
 * DTO for {@link User}. It is also used by cache.
 * 
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public class UserDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3702045955458203861L;

    // TODO will consider whether need to remove this user id later for efficient cache storage
    private String            id;
    private String            userName;
    private String            email;
    private String            createdDate;
    private String            modifiedDate;
    private String            firstName;
    private String            lastName;
    private String            portray;

    public UserDto() {

    }

    public UserDto(User user) {
        this.id = String.valueOf(user.getId());
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.createdDate = String.valueOf(user.getCreatedDate().getTime());
        this.modifiedDate = String.valueOf(user.getModifiedDate().getTime());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.portray = user.getPortray();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPortray() {
        return portray;
    }

    public void setPortray(String portray) {
        this.portray = portray;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
        result = prime * result + ((portray == null) ? 0 : portray.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        UserDto other = (UserDto) obj;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        }
        else if (!createdDate.equals(other.createdDate))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        }
        else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        }
        else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        }
        else if (!lastName.equals(other.lastName))
            return false;
        if (modifiedDate == null) {
            if (other.modifiedDate != null)
                return false;
        }
        else if (!modifiedDate.equals(other.modifiedDate))
            return false;
        if (portray == null) {
            if (other.portray != null)
                return false;
        }
        else if (!portray.equals(other.portray))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        }
        else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", userName=" + userName + ", email=" + email
                + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", firstName="
                + firstName + ", lastName=" + lastName + ", portray=" + portray + "]";
    }
}
