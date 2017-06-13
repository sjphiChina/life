/**
 * 
 */
package sjph.life.data.dao;

/**
 * @author shaoguo
 *
 */
public enum PostSchema {

    ID,
    CONTENT,
    USER_ID,
    CREATED_DT,
    MODIFIED_DT
    ;
    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "post";

    public static final String FAKE_RECORD_VALUE = "-1";
}
