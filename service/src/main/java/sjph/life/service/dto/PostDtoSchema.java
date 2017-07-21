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
package sjph.life.service.dto;

/**
 * Contains all properties of {@link PostDto}.
 * 
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public enum PostDtoSchema {

    //@formatter:off
    ID                                                                                       ("id"),
    CONTENT                                                                             ("content"),
    USER_ID                                                                              ("userId"),
    CREATED_DATE                                                                    ("createdDate"),
    USER_NAME_DISPLAYING                                                     ("userNameDisplaying");
    //@formatter:on

    private String description;

    private PostDtoSchema(String description) {
        this.description = description;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }
}
