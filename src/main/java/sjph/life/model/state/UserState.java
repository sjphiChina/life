package sjph.life.model.state;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaoguo
 *
 */
public enum UserState {

    //@formatter:off
    NONE('N', "NONE"),
    ACTIVE('A', "ACTIVE"),
    INACTIVE('I', "INACTIVE"),
    DELETED('D', "DELETED"),
    BLOCKED('B', "BLOCKED");
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
     * @return
     */
    public char getCharValue() {
        return charValue;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

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
