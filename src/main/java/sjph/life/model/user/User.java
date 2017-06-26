/**
 * 
 */
package sjph.life.data.model;

import java.util.Date;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public class User {

    private final Long Id;
    private String     userName;
    private String     email;
    private String     password;
    private UserState  userState;
    private final Date createdDate;
    private Date       modifiedDate;

    public User(Long id, String userName, String email, String password, UserState userState,
            Date createdDate, Date modifiedDate) {
        super();
        Id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userState = userState;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return Id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserState getUserState() {
        return userState;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
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
        if (Id == null) {
            if (other.Id != null)
                return false;
        }
        else if (!Id.equals(other.Id))
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
        return "User [Id=" + Id + ", userName=" + userName + ", email=" + email + ", password="
                + password + ", userState=" + userState + ", createdDate=" + createdDate
                + ", modifiedDate=" + modifiedDate + "]";
    }

}
