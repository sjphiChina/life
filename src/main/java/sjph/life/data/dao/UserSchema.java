/**
 * 
 */
package sjph.life.data.dao;


/**
 * @author shaoguo
 *
 */
public enum UserSchema {

    ID,
    USER_NAME,
    EMAIL,
    PASSWORD,
    STATE,
    CREATED_DT,
    MODIFIED_DT
    ;
    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "user";

    public static final String FAKE_RECORD_VALUE = "-1";
}
