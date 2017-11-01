/**
 * 
 */
package sjph.life.user.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sjph.life.user.database.dao.UserDao;
import sjph.life.user.database.schema.UserSchema;
import sjph.life.user.model.User;
import sjph.life.user.model.state.UserState;


/**
 * @author shaoguo
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER                        = LogManager
            .getLogger(UserDaoImpl.class);

    //@formatter:off
    private static final String FULL_TABLE_COLUMNS_SQL = 
                    UserSchema.ID + ", " +
                    UserSchema.USER_NAME + ", " +
                    UserSchema.EMAIL + ", " +
                    UserSchema.PASSWORD + ", " +
                    UserSchema.STATE + ", " +
                    UserSchema.CREATED_DT + ", " +
                    UserSchema.MODIFIED_DT + ", " +
                    UserSchema.FIRST_NAME + ", " +
                    UserSchema.LAST_NAME + ", " +
                    UserSchema.PORTRAY + ", " +
                    UserSchema.LEVEL;

/**create section    */
    private static final String CREATE_USER =
            "INSERT INTO " +
                    UserSchema.tableName +
                    " (" +
                    UserSchema.USER_NAME + ", " +
                    UserSchema.EMAIL + ", " +
                    UserSchema.PASSWORD + ", " +
                    UserSchema.STATE + ", " +
                    UserSchema.CREATED_DT + ", " +
                    UserSchema.MODIFIED_DT + ", " +
                    UserSchema.FIRST_NAME + ", " +
                    UserSchema.LAST_NAME + ", " +
                    UserSchema.PORTRAY + ", " +
                    UserSchema.LEVEL +
                    ") " +
            "VALUES " +
                    "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

/**select section    */
    private static final String SELECT_FULL_TABLE_COLUMNS_SQL =
            "SELECT " +
                    FULL_TABLE_COLUMNS_SQL + " " +
            "FROM " +
                    UserSchema.tableName;

    //private static final String ORDER_BY = "ORDER BY";

    //private static final String DESC = "DESC";

    private static final String FIND_BY_ID =
            SELECT_FULL_TABLE_COLUMNS_SQL + " " +
            "WHERE " +
                    UserSchema.ID + " = ?";

    private static final String FIND_BY_EMAIL =
            SELECT_FULL_TABLE_COLUMNS_SQL + " " +
            "WHERE " +
                    UserSchema.EMAIL + " = ?";

    private static final String FIND_BY_USER_NAME =
            SELECT_FULL_TABLE_COLUMNS_SQL + " " +
            "WHERE " +
                    UserSchema.USER_NAME + " = ?";
/**update section    */
    private static final String UPDATE_TABLE_SQL =
            "UPDATE " +
                    UserSchema.tableName;
    
    private static final String UPDATE_USER =
            UPDATE_TABLE_SQL + " " +
            "SET " +
                    UserSchema.USER_NAME + " = ?, " +
                    UserSchema.EMAIL + " = ?, " +
                    UserSchema.PASSWORD + " = ?, " +
                    UserSchema.STATE + " = ?, " +
                    UserSchema.MODIFIED_DT + " = ?, " +
                    UserSchema.FIRST_NAME + " = ?, " +
                    UserSchema.LAST_NAME + " = ?, " +
                    UserSchema.PORTRAY + " = ?, " +
                    UserSchema.LEVEL + " = ?, " +
            "WHERE " +
                    UserSchema.ID + " = ?";

/**delete section    */
    private static final String DELETE_USER_SQL =
            "DELETE FROM " +
                    UserSchema.tableName;

    private static final String DELETE_USER_BY_ID =
            DELETE_USER_SQL + " " +
            "WHERE " +
                    UserSchema.ID + " = ?";
    //@formatter:on

    private final UserRowMapper userRowMapper                 = new UserRowMapper();
    @Autowired(required = true)
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate        jdbcTemplate;

    @Override
    public long createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(CREATE_USER,
                        new String[] { UserSchema.ID.name() });
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, String.valueOf(user.getUserState().getCharValue()));
                ps.setObject(5, user.getCreatedDate());
                ps.setObject(6, user.getModifiedDate());
                ps.setString(7, user.getFirstName());
                ps.setString(8, user.getLastName());
                ps.setString(9, user.getPortray());
                ps.setInt(10, user.getLevel());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public User findUser(Long userId) {
        try {
            final Object[] sqlParameters = new Object[] { userId };
            return jdbcTemplate.queryForObject(FIND_BY_ID, sqlParameters, userRowMapper);
        }
        catch (@SuppressWarnings("unused") EmptyResultDataAccessException e) {
            LOGGER.warn("Don't find user, userId=" + userId);
            return null;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            final Object[] sqlParameters = new Object[] { email };
            return jdbcTemplate.queryForObject(FIND_BY_EMAIL, sqlParameters, userRowMapper);
        }
        catch (@SuppressWarnings("unused") EmptyResultDataAccessException e) {
            LOGGER.warn("Don't find user, email=" + email);
            return null;
        }
    }

    @Override
    public User findUserByUserName(String userName) {
        try {
            final Object[] sqlParameters = new Object[] { userName };
            return jdbcTemplate.queryForObject(FIND_BY_USER_NAME, sqlParameters, userRowMapper);
        }
        catch (@SuppressWarnings("unused") EmptyResultDataAccessException e) {
            LOGGER.warn("Don't find user, userName=" + userName);
            return null;
        }
    }

    @Override
    public int updateUser(User user) {
        return jdbcTemplate.update(UPDATE_USER, new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, String.valueOf(user.getUserState().getCharValue()));
                ps.setObject(5, user.getModifiedDate());
                ps.setString(6, user.getFirstName());
                ps.setString(7, user.getLastName());
                ps.setString(8, user.getPortray());
                ps.setInt(9, user.getLevel());
            }
        });
    }

    @Override
    public int deleteUser(Long userId) {
        return jdbcTemplate.update(DELETE_USER_BY_ID, userId);
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet arg0, int arg1) throws SQLException {
            return populateUserRecord(arg0);
        }
    }

    //@formatter:off
    private static User populateUserRecord(final ResultSet rs) throws SQLException {
        return new User(
                rs.getLong(UserSchema.ID.name()), 
                rs.getString(UserSchema.USER_NAME.name()),
                rs.getString(UserSchema.EMAIL.name()), 
                rs.getString(UserSchema.PASSWORD.name()),
                UserState.get((rs.getString(UserSchema.STATE.name()).charAt(0))),
                rs.getTimestamp(UserSchema.CREATED_DT.name()),
                rs.getTimestamp(UserSchema.MODIFIED_DT.name()),
                rs.getString(UserSchema.FIRST_NAME.name()),
                rs.getString(UserSchema.LAST_NAME.name()),
                rs.getString(UserSchema.PORTRAY.name()), 
                rs.getInt(UserSchema.LEVEL.name())
                );
    }
    //@formatter:on
}
