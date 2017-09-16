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
package sjph.life.friendship.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import sjph.life.friendship.config.ServiceConfig;
/**
 * temp config: will move the principle configs to platform in release/0.7.
 * 
 * @author Shaohui Guo
 *
 */
@Configuration
@EnableCassandraRepositories(basePackages = { "sjph.life.friendship" })
public class CassandraConfig {

    private static final Logger LOGGER = LogManager.getLogger(CassandraConfig.class);
    @Autowired
    ServiceConfig config;
    
    @Bean
    public CassandraClusterFactoryBean cluster() {

       
        LOGGER.debug(config.getExampleProperty());
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(config.getDatabaseIp());

        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {

//        CassandraMappingContext mappingContext = new CassandraMappingContext();
//        mappingContext.setUserTypeResolver(
//                new SimpleUserTypeResolver(cluster().getObject(), "life_user"));
        // TODO This doesn't work due to current spring cassandra api. 
        // Couldn't find PersistentEntity for type sjph.life.model.cassandra.Friendship
        // Will revisit to it once api is matual.
        //mappingContext.setCustomConversions(customConversions());
        
        BasicCassandraMappingContext mappingContext =  new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), "life_user"));
        
        return mappingContext;
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() throws Exception {

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName("life_user");
        session.setSchemaAction(SchemaAction.NONE);
        session.setConverter(converter());
        return session;
    }

    @Bean("cassandraOperations")
    public CassandraOperations cassandraTemplate() throws Exception {
        return new CassandraTemplate(session().getObject(), converter());
    }

//    @Bean
//    public CassandraCustomConversions customConversions() {
//
//        List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
//        converters.add(new FriendshipReadConverter());
//        converters.add(new FriendshipWriteConverter());
//
//        return new CassandraCustomConversions(converters);
//    }
}
