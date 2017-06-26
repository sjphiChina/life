/**
 * 
 */
package sjph.life.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sjph.life.model.User;
import sjph.life.model.dao.UserDao;
import sjph.life.model.dao.schema.UserSchema;

/**
 * @author shaoguo
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

    //@formatter:off
    private static final String FULL_TABLE_COLUMNS_SQL = 
            UserSchema.ID + ", " +
            UserSchema.USER_NAME + ", " +
            UserSchema.EMAIL + ", " +
            UserSchema.PASSWORD + ", " +
            UserSchema.STATE + ", " +
            UserSchema.CREATED_DT + ", " +
            UserSchema.MODIFIED_DT;
    //@formatter:on

    @Autowired(required=true)
    private JdbcTemplate        jdbcTemplate;

    public int createUser(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int deleteUser(Long id) {
        // TODO Auto-generated method stub
        return 0;
    }

    public User findUser(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    public User updateUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }
}