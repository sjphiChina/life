/*
 * Copyright 2017 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sjph.life.post.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import sjph.life.post.config.ServiceConfig;

/**
 * Connection configuration for MySQL database.
 * 
 * @author Shaohui Guo
 *
 */
@Configuration
public class MySQLDatabaseUtils {

    @Autowired
    ServiceConfig config;

    /**
     * @return a {@link DataSource} of MySQL
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(config.getDatabaseDriverClassName());
        driverManagerDataSource.setUrl(config.getDatasourceUrl());
        driverManagerDataSource.setUsername(config.getDatasourceUsername());
        driverManagerDataSource.setPassword(config.getDatasourcePassword());
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
