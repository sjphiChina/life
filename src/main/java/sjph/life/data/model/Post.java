package sjph.life.data.model;

import java.util.Date;
//import java.time.ZonedDateTime;
/**
 * @author shaoguo
 *
 */

@SuppressWarnings("javadoc")
public class Post {

    private Long          id;
    private String              content;
    private final Long          userId;
    private final Date createdDate;
    private Date       modifiedDate;

    public Post(String content, Long userId, Date createdDate,
            Date modifiedDate) {
        super();
        this.id = null;
        this.content = content;
        this.userId = userId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Post(Long id, String content, Long userId, Date createdDate,
            Date modifiedDate) {
        super();
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        Post other = (Post) obj;
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
        if (modifiedDate == null) {
            if (other.modifiedDate != null)
                return false;
        }
        else if (!modifiedDate.equals(other.modifiedDate))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", content=" + content + ", createdDate=" + createdDate
                + ", modifiedDate=" + modifiedDate + ", userId=" + userId + "]";
    }
}
