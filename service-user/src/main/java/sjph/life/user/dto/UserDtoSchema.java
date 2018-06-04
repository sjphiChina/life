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
package sjph.life.user.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all properties of {@link PostDto}.
 * 
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public enum UserDtoSchema {

    //@formatter:off
    ID                                                                                       ("id"),
    USER_NAME                                                                          ("userName"),
    EMAIL                                                                                 ("email"),
    CREATED_DATE                                                                    ("createdDate"),
    MODIFIED_DATE                                                                  ("modifiedDate"),
    FIRST_NAME                                                                        ("firstName"),
    LAST_NAME                                                                          ("lastName"),
    PORTRAY                                                                             ("portray");
    //@formatter:on

    private String description;
    private static Map<String, UserDtoSchema> lookUp = new HashMap<>();

    private UserDtoSchema(String description) {
        this.description = description;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param charValue the char value
     * @return UserState
     */
    public static UserDtoSchema get(String description) {
        return lookUp.get(description);
    }

    static {
        for (UserDtoSchema userDtoSchema : UserDtoSchema.values()) {
            lookUp.put(userDtoSchema.description, userDtoSchema);
        }
    }
}
