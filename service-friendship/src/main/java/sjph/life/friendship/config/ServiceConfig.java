package sjph.life.friendship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${database.ip}")
    private String databaseIp;

    @Value("${database.port}")
    private String databasePort;

    @Value("${database.keyspace}")
    private String databaseKeyspace;

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

    public String getRedisIp() {
        return redisIp;
    }

    public String getRedisPort() {
        return redisPort;
    }

}
