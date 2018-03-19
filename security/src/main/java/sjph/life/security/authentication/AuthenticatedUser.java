/**
 * 
 */
package sjph.life.security.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author shaohuiguo
 *
 */
@SuppressWarnings("javadoc")
public class AuthenticatedUser extends User {

    private static final long          serialVersionUID = 6151222121443498098L;
    private final sjph.life.model.user.User user;

    /**
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     * @param user
     */
    public AuthenticatedUser(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities, sjph.life.model.user.User user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.user = user;
    }

    public sjph.life.model.user.User getUserOfLife() {
        return this.user;
    }

}
