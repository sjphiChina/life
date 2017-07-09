/**
 * 
 */
package sjph.life.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sjph.life.model.state.UserState;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public class User {

    private Long          id;
    private String        userName;
    private String        email;
    private String        password;
    private UserState     userState;
    private Date          createdDate;
    private Date          modifiedDate;
    private String        firstName;
    private String        lastName;
    private String        portray;
    private int           level;

    @JsonIgnore
    private MultipartFile profileImage;

    public User() {
    }

    public User(String userName, String email, String password, UserState userState,
            Date createdDate, Date modifiedDate, String firstName, String lastName, String portray,
            int level) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userState = userState;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.portray = portray;
        this.level = level;
    }

    public User(Long id, String userName, String email, String password, UserState userState,
            Date createdDate, Date modifiedDate, String firstName, String lastName, String portray,
            int level) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userState = userState;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.portray = portray;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + level;
        result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((portray == null) ? 0 : portray.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((userState == null) ? 0 : userState.hashCode());
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
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
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        }
        else if (!lastName.equals(other.lastName))
            return false;
        if (level != other.level)
            return false;
        if (modifiedDate == null) {
            if (other.modifiedDate != null)
                return false;
        }
        else if (!modifiedDate.equals(other.modifiedDate))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        }
        else if (!password.equals(other.password))
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
        if (userState != other.userState)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", email=" + email + ", userState="
                + userState + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
                + ", firstName=" + firstName + ", lastName=" + lastName + ", portray=" + portray
                + ", level=" + level + "]";
    }
}
