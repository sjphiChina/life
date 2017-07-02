package sjph.life.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import sjph.life.model.dao.RelationshipDao;
import sjph.life.model.dao.schema.RelationshipSchema;

/**
 * @author shaohuiguo
 *
 */
@Repository
public class RelationshipDaoImpl implements RelationshipDao {

    //@formatter:off
    /**create section    */
    private static final String CREATE_RELATIONSHIP =
            "INSERT INTO " +
                    RelationshipSchema.tableName +
                    " (" +
                    RelationshipSchema.USER_ID + ", " +
                    RelationshipSchema.FOLLOWER_ID +
                    ") " +
            "VALUES " +
                    "( ?, ?)";

    /**select section    */
    private static final String SELECT_FOLLOWER_ID_SQL =
            "SELECT " +
                    RelationshipSchema.FOLLOWER_ID + " " +
            "FROM " +
                    RelationshipSchema.tableName + " " +
            "WHERE " +
                    RelationshipSchema.USER_ID + " = ?";

    /**delete section    */
    private static final String DELETE_FOLLOWER_ID_SQL =
            "DELETE FROM " +
                    RelationshipSchema.tableName + " " +
            "WHERE " +
                    RelationshipSchema.USER_ID + " = ? " +
            "AND " +
                    RelationshipSchema.FOLLOWER_ID + " = ?";
    //@formatter:on

    private final FollowerIdRowMapper followerIdRowMapper    = new FollowerIdRowMapper();
    @Autowired(required = true)
    private JdbcTemplate              jdbcTemplate;

    @Override
    public void createRelationship(Long userId, Long followerId) {
        jdbcTemplate.update(CREATE_RELATIONSHIP, userId, followerId);
    }

    @Override
    public List<Long> getFollwers(Long userId) {
        return jdbcTemplate.query(SELECT_FOLLOWER_ID_SQL, followerIdRowMapper, userId);

    }

    @Override
    public int deleteFollwer(Long userId, Long followerId) {
        return jdbcTemplate.update(DELETE_FOLLOWER_ID_SQL, userId, followerId);
    }

    private static final class FollowerIdRowMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet arg0, int arg1) throws SQLException {
            return populateRecord(arg0);
        }
    }

    private static Long populateRecord(final ResultSet rs) throws SQLException {
        return rs.getLong(RelationshipSchema.FOLLOWER_ID.toString());
    }
}
