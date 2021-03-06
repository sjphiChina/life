package sjph.life.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Shaohui Guo
 *
 */
@Component
public class ServiceConfig {

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

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private String redisPort;

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

    public String getRedisServer() {
        return redisServer;
    }

    public String getRedisPort() {
        return redisPort;
    }

}
