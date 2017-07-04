/**
 * 
 */
package sjph.life.model.dao.schema;

import sjph.life.platform.database.DatabaseConstants;

/**
 * @author shaohuiguo
 *
 */
public enum RelationshipSchema {

    USER_ID,
    FOLLOWER_ID;

    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "relationship";
}
