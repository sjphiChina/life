/**
 * 
 */
package sjph.life.user.database.schema;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public enum UserSchema {

    ID,
    USER_NAME,
    EMAIL,
    PASSWORD,
    STATE,
    CREATED_DT,
    MODIFIED_DT,
    FIRST_NAME,
    LAST_NAME,
    PORTRAY,
    LEVEL
    ;
    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "user";

    public static final String FAKE_RECORD_VALUE = "-1";
}
