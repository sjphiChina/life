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
package sjph.life.platform.cache.redis;

import java.util.Map;

import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * A temp copy of the deprecated {@link JacksonHashMapper}
 * 
 * @author Shaohui Guo
 * @param <T> type
 *
 */
public class JacksonHashMapperWarpper<T> implements HashMapper<T, String, Object> {

    private final ObjectMapper mapper;
    private final JavaType userType;
    private final JavaType mapType = TypeFactory.defaultInstance()
            .constructMapType(Map.class, String.class, Object.class);

    /**
     * Creates new {@link JacksonHashMapperWarpper}.
     * 
     * @param type type
     */
    public JacksonHashMapperWarpper(Class<T> type) {

        this(type, new ObjectMapper());
        //mapper.getSerializationConfig()..setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * @param type type
     * @param mapper type
     */
    public JacksonHashMapperWarpper(Class<T> type, ObjectMapper mapper) {

        this.mapper = mapper;
        this.userType = TypeFactory.defaultInstance().constructType(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T fromHash(Map<String, Object> hash) {
        return (T) mapper.convertValue(hash, userType);
    }

    @Override
    public Map<String, Object> toHash(T object) {
        return mapper.convertValue(object, mapType);
    }
}
