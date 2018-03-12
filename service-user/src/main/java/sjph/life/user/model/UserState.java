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
package sjph.life.user.model.state;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shaohui Guo
 *
 */
@SuppressWarnings("javadoc")
public enum UserState {

    //@formatter:off
    NONE                                                                              ('N', "NONE"),
    ACTIVE                                                                          ('A', "ACTIVE"),
    INACTIVE                                                                      ('I', "INACTIVE"),
    EXPIRED                                                                        ('E', "EXPIRED"),
    CREDENTIAL_EXPIRED                                                  ('C', "CREDENTIAL_EXPIRED"),
    LOCKED                                                                          ('L', "LOCKED");
    //@formatter:on

    // Fields
    private char                             charValue;
    private String                           description;
    private static Map<Character, UserState> lookUp = new HashMap<>();

    private UserState(char charValue, String description) {
        this.charValue = charValue;
        this.description = description;
    }

    /**
     * @return char value
     */
    public char getCharValue() {
        return charValue;
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
    public static UserState get(char charValue) {
        UserState userState = lookUp.get(charValue);
        if (userState == null) {
            userState = NONE;
        }
        return userState;
    }

    static {
        for (UserState userState : UserState.values()) {
            lookUp.put(userState.charValue, userState);
        }
    }
}
