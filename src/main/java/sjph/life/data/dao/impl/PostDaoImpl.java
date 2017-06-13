package sjph.life.data.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import sjph.life.data.dao.PostDao;
import sjph.life.data.dao.PostSchema;
import sjph.life.data.model.Post;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public class PostDaoImpl implements PostDao {

    //@formatter:off
    private static final String FULL_TABLE_COLUMNS_SQL = 
            PostSchema.ID + ", " +
                    PostSchema.CONTENT + ", " +
                    PostSchema.CREATED_DT + ", " +
                    PostSchema.MODIFIED_DT + ", " +
                    PostSchema.USER_ID;
    
    private static final String SELECT_FULL_TABLE_COLUMNS_SQL = 
            "SELECT " +
                    FULL_TABLE_COLUMNS_SQL  + " " +
            "FROM " +
            PostSchema.tableName + " ";
    
    private static final String CREATE_POST_SQL = 
            "INSERT INTO " +
                    PostSchema.tableName +
                    " ( " +
                    FULL_TABLE_COLUMNS_SQL  + " " +
                    " ) " +
            "VALUES " +
                    "( ?, ?, ?, ?, ?)";
    
    private static final String DELETE_POST_SQL = 
            "DELETE FROM " +
                    PostSchema.tableName + " " +
            "WHERE " +
                    PostSchema.ID + " = ? ";
    //@formatter:on

    private JdbcTemplate        jdbcTemplate;

    @Required
    public void setSystemDataSource(final DataSource systemDataSource) {
        this.jdbcTemplate = new JdbcTemplate(systemDataSource);
    }

    @Override
    public int createPost(Post post) {
        return jdbcTemplate.update(CREATE_POST_SQL, post.getId(), post.getContent(),
                post.getCreatedDate(), post.getModifiedDate(), post.getUserId());
    }

    @Override
    public int deletePost(Long id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Post findPost(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        // TODO Auto-generated method stub
        return null;
    }

}
