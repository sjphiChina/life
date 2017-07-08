package sjph.life.website.service;

import java.util.Date;

/**
 * @author shaohuiguo
 *
 */

@SuppressWarnings("javadoc")
public class PostBean {

    private final Long   id;
    private final String content;
    private final Long   userId;
    private final Date   createdDate;
    private final Date   modifiedDate;

    public PostBean(Long id, String content, Long userId, Date createdDate, Date modifiedDate) {
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

    @Override
    public String toString() {
        return "PostBean [id=" + id + ", content=" + content + ", userId=" + userId
                + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
    }

}
