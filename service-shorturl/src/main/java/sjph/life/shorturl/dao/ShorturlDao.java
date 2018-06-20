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
package sjph.life.shorturl.dao;

import sjph.life.shorturl.model.Shorturl;

/**
 * @author Shaohui Guo
 *
 */
public interface ShorturlDao {

    // currently we use one object to create the record since we are still considering other ways
    // rather than longurl -> id 
    long createShorturlRecord(Shorturl shorturlObject);

    Shorturl findByShorturl(String shorturlString);

    Shorturl findByLongurl(String longurlString);

    int updateShorturlRecord(Shorturl shorturlObject);

    int deleteById(long id);

    int deleteByShorturl(String shorturlString);

    int deleteByLongurl(String longurlString);
}
