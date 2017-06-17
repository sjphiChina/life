package sjph.life.data.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import sjph.life.data.database.dao.PostDao;
import sjph.life.data.database.dao.PostSchema;
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

/**create section    */
    private static final String CREATE_POST =
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

/**select section    */
    private static final String SELECT_FULL_TABLE_COLUMNS_SQL =
            "SELECT " +
                    FULL_TABLE_COLUMNS_SQL + " " +
            "FROM " +
                    PostSchema.tableName;

    private static final String FIND = SELECT_FULL_TABLE_COLUMNS_SQL;

    private static final String FIND_BY_ID =
            SELECT_FULL_TABLE_COLUMNS_SQL + " " +
            "WHERE " +
                    PostSchema.ID + " = ?";

    private static final String FIND_BY_USER_ID =
            SELECT_FULL_TABLE_COLUMNS_SQL +
            "WHERE " +
                    PostSchema.USER_ID + " = ?";

/**update section    */
    private static final String UPDATE_TABLE_SQL =
            "UPDATE " +
                    PostSchema.tableName;
    
    private static final String UPDATE_CONTENT =
            UPDATE_TABLE_SQL + " " +
            "SET " +
                    PostSchema.CONTENT + " = ?, " +
                    PostSchema.MODIFIED_DT + "= ? " +
            "WHERE " +
                    PostSchema.ID + " = ?";

/**delete section    */
    private static final String DELETE_POST_SQL =
            "DELETE FROM " +
                    PostSchema.tableName;

    private static final String DELETE_POST_BY_ID =
            DELETE_POST_SQL + " " +
            "WHERE " +
                    PostSchema.ID + " = ? ";

    private static final String DELETE_POST_BY_USER_ID =
            DELETE_POST_SQL + " " +
            "WHERE " +
                    PostSchema.USER_ID + " = ? ";
    //@formatter:on

    private final PostRowMapper postRowMapper                 = new PostRowMapper();
    private JdbcTemplate        jdbcTemplate;

    @Required
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(CREATE_POST,
                        new String[] { PostSchema.ID.name() });
                ps.setString(1, post.getContent());
                ps.setLong(2, post.getUserId());
                ps.setObject(3, post.getCreatedDate());
                ps.setObject(4, post.getModifiedDate());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
        // return jdbcTemplate.update(CREATE_POST_SQL, post.getContent(), post.getCreatedDate(),
        // post.getModifiedDate(), post.getUserId());
    }

    @Override
    public List<Post> findPosts() {
        return jdbcTemplate.query(FIND, postRowMapper);
    }

    @Override
    public Post findPost(Long id) {
        final Object[] sqlParameters = new Object[] { id };
        return jdbcTemplate.queryForObject(FIND_BY_ID, sqlParameters, postRowMapper);
    }

    @Override
    public List<Post> findPosts(Long userId) {
        final Object[] sqlParameters = new Object[] { userId };
        return jdbcTemplate.query(FIND_BY_USER_ID, sqlParameters, postRowMapper);
    }

    @Override
    public int updatePost(Post post) {
        return jdbcTemplate.update(UPDATE_CONTENT, new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement stmt) throws SQLException {
                stmt.setString(1, post.getContent());
                stmt.setObject(2, post.getModifiedDate());
                stmt.setLong(3, post.getId());
            }
        });
    }

    @Override
    public int deletePost(Long id) {
        return jdbcTemplate.update(DELETE_POST_BY_ID, id);
    }

    @Override
    public int deletePostByUserId(Long userId) {
        return jdbcTemplate.update(DELETE_POST_BY_USER_ID, userId);
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
