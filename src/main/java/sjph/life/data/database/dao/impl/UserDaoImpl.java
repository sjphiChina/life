/**
 * 
 */
package sjph.life.data.database.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import sjph.life.data.database.dao.UserDao;
import sjph.life.data.database.dao.UserSchema;
import sjph.life.data.model.User;

/**
 * @author shaoguo
 *
 */
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

    private JdbcTemplate        jdbcTemplate;

    @Required
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int createUser(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteUser(Long id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public User findUser(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User updateUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

}