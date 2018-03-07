package sjph.life.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Shaohui Guo
 *
 */
//@Component
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${database.ip}")
    private String databaseIp;

    @Value("${database.port}")
    private String databasePort;

    @Value("${database.keyspace}")
    private String databaseKeyspace;

    @Value("${spring.jpa.show-sql}")
    private String jpaShowSql;

    @Value("${spring.database.driverClassName}")
    private String databaseDriverClassName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String jpaPropertiesHibernateDialect;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getDatabaseIp() {
        return databaseIp;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseKeyspace() {
        return databaseKeyspace;
    }

    public String getJpaShowSql() {
        return jpaShowSql;
    }

    public String getDatabaseDriverClassName() {
        return databaseDriverClassName;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public String getJpaPropertiesHibernateDialect() {
        return jpaPropertiesHibernateDialect;
    }

    public String getRedisIp() {
        return redisIp;
    }

    public String getRedisPort() {
        return redisPort;
    }

}
