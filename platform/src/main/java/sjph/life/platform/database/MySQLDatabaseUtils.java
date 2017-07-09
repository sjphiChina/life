/**
 * 
 */
package sjph.life.platform.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author shaohuiguo
 *
 */
@Configuration
public class MySQLDatabaseUtils {

    /**
     * @return a {@link DataSource} of MySQL
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/life_common");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("admin");
        return driverManagerDataSource;
    }

    /**
     * @return a {@link JdbcTemplate} for MySQL {@link DataSource}
     */
    @Bean("mysqlJdbcTemplate")
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
