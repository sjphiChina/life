package sjph.life.platform.database;

/**
 * Database access constants.
 */
public interface DatabaseConstants {

    /**
     * The prefix to schema object names that is dynamically replaced typically with the
     * Oracle schema or MySQL database name.
     */
    String SCHEMA_OBJECT_PREFIX   = "/* ?. */";

    /**
     * The prefix to syslocal object names.
     */
    String SYSLOCAL_OBJECT_PREFIX = "/*SYSLOCAL*/";

    /**
     * The database role assigned to the shared connection pool user for accessing objects in
     * account schemas.
     */
    String SHARED_CONN_POOL_ROLE  = "SHARED_CONN_POOL_ROLE";
}
