/**
 * 
 */
package sjph.life.security.authentication;

import java.util.HashMap;
import java.util.Map;

import sjph.life.model.User;

/**
 * @author shaohuiguo
 *
 */
public class UserRole {

    public enum Role {
        //@formatter:off
        NONE('N', "NONE"),
        USER('U', "USER"),
        ADMIN('A', "ADMIN"),
        BUSINESS_ANALYST('B', "BUSINESS_ANALYST"),
        SUPPORT('S', "SUPPORT"),
        OPERATION('O', "OPERATION");
        //@formatter:on

        // Fields
        private char                        charValue;
        private String                      description;
        private static Map<Character, Role> lookUp = new HashMap<>();

        private Role(char charValue, String description) {
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

        public static Role get(char charValue) {
            Role role = lookUp.get(charValue);
            if (role == null) {
                role = NONE;
            }
            return role;
        }

        static {
            for (Role role : Role.values()) {
                lookUp.put(role.charValue, role);
            }
        }
    }

    private User   user;
    private String role;

    public UserRole() {
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
