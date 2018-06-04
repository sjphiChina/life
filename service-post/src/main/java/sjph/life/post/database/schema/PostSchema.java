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
package sjph.life.post.database.schema;

/**
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public enum PostSchema {

    ID,
    CONTENT,
    USER_ID,
    CREATED_DT,
    MODIFIED_DT,
    USER_NAME
    ;
    public static final String tableName = DatabaseConstants.SCHEMA_OBJECT_PREFIX + "post";

    public static final String FAKE_RECORD_VALUE = "-1";
}
