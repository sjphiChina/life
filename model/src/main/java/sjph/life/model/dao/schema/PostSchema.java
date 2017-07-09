/**
 * 
 */
package sjph.life.model.dao.schema;

import sjph.life.platform.database.DatabaseConstants;

/**
 * @author shaoguo
 *
 */
@SuppressWarnings("javadoc")
public enum PostSchema {

    ID,
    CONTENT,
    USER_ID,
    CREATED_DT,
    MODIFIED_DT,
    USER_NAME
    ;
    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "post";

    public static final String FAKE_RECORD_VALUE = "-1";
}
