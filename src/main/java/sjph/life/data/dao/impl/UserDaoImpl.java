/**
 * 
 */
package sjph.life.data.dao.impl;

import sjph.life.data.dao.UserDao;
import sjph.life.data.dao.UserSchema;
import sjph.life.data.model.User;


/**
 * @author shaoguo
 *
 */
public class UserDaoImpl implements UserDao {

    private static final String FULL_TABLE_COLUMNS_SQL = 
            UserSchema.ID + ", " +
                    UserSchema.USER_NAME + ", " +
                    UserSchema.EMAIL + ", " +
                    UserSchema.PASSWORD + ", " +
                    UserSchema.STATE + ", " +
                    UserSchema.CREATED_DT + ", " +
                    UserSchema.MODIFIED_DT;
    
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
