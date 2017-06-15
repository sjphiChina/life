package sjph.life.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


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
                    PostSchema.USER_ID + ", " + 
                    PostSchema.CREATED_DT + ", " +
                    PostSchema.MODIFIED_DT;
    
    private static final String SELECT_FULL_TABLE_COLUMNS_SQL = 
            "SELECT " + FULL_TABLE_COLUMNS_SQL + " " +
            "FROM " +
            PostSchema.tableName + " " +
            "WHERE " + 
            PostSchema.ID + " = ?";
    
    private static final String CREATE_POST_SQL = 
            "INSERT INTO " +
                    PostSchema.tableName +
                    "( " +
                    PostSchema.CONTENT + ", " +
                    PostSchema.USER_ID + ", " + 
                    PostSchema.CREATED_DT + ", " +
                    PostSchema.MODIFIED_DT + " " + 
                    ") " +
            "VALUES " +
                    "( ?, ?, ?, ?)";
    
    private static final String DELETE_POST_SQL = 
            "DELETE FROM " +
                    PostSchema.tableName + " " +
            "WHERE " +
                    PostSchema.ID + " = ? ";
    //@formatter:on

    private final PostRowMapper postRowMapper                 = new PostRowMapper();
    private JdbcTemplate        jdbcTemplate;

    @Required
    public void setSystemDataSource(final DataSource systemDataSource) {
        this.jdbcTemplate = new JdbcTemplate(systemDataSource);
    }

    @Override
    public Long createPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps =
                        connection.prepareStatement(CREATE_POST_SQL, new String[] {PostSchema.ID.name()});
                    ps.setString(1, post.getContent());
                    ps.setLong(2, post.getUserId());
                    ps.setObject(3, post.getCreatedDate());
                    ps.setObject(4, post.getModifiedDate());
                    return ps;
                }
            },
            keyHolder);
        return keyHolder.getKey().longValue();
        
        
//        return jdbcTemplate.update(CREATE_POST_SQL, post.getContent(), post.getCreatedDate(),
//                post.getModifiedDate(), post.getUserId());
    }

    @Override
    public int deletePost(Long id) {
        return jdbcTemplate.update(DELETE_POST_SQL, id);
    }

    @Override
    public Post findPost(Long postId) {
        final Object[] sqlParameters = new Object[] { postId };
        return jdbcTemplate.queryForObject(SELECT_FULL_TABLE_COLUMNS_SQL, sqlParameters,
                postRowMapper);
    }

    @Override
    public List<Post> findPosts(Long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        // TODO Auto-generated method stub
        return null;
    }

    private static final class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet arg0, int arg1) throws SQLException {
            return populatePostRecord(arg0);
        }
    }

    //@formatter:off
    private static Post populatePostRecord(final ResultSet rs) throws SQLException {
        return new Post(
                rs.getLong(PostSchema.ID.name()), 
                rs.getString(PostSchema.CONTENT.name()),
                rs.getLong(PostSchema.USER_ID.name()), 
                rs.getDate(PostSchema.CREATED_DT.name()),
                rs.getDate(PostSchema.MODIFIED_DT.name()));
    }
    //@formatter:on
}
