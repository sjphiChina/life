package sjph.life.web.post;

import java.util.Date;

/**
 * @author shaohuiguo
 *
 */

@SuppressWarnings("javadoc")
public class PostBean {

    private Long   id;
    private String content;
    private Long   userId;
    private Date   createdDate;
    private Date   modifiedDate;

    public PostBean() {
    }

    public PostBean(String content) {
        this.id = null;
        this.content = content;
        this.userId = null;
        this.createdDate = null;
        this.modifiedDate = null;
    }

    public PostBean(String content, Long userId, Date createdDate, Date modifiedDate) {
        this.id = null;
        this.content = content;
        this.userId = userId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

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

    public void setContent(String content) {
        this.content = content;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
