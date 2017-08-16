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
package sjph.life.friendship.database.schema;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This doesn't work due to current spring cassandra api. Will revisit to it once api is matual.
 * 
 * @author Shaohui Guo
 *
 */
@WritingConverter
public class FriendshipWriteConverter implements Converter<Friendship, String> {

    private static final Logger LOGGER = LogManager.getLogger(FriendshipWriteConverter.class);

    @Override
    public String convert(Friendship source) {
        // TODO see above
        LOGGER.debug("The write source is: " + source.toString());
        try {
            return new ObjectMapper().writeValueAsString(source);
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
