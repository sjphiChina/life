/**
 * 
 */
package sjph.life.authentication.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Shaohui guo
 *
 */
@SuppressWarnings("javadoc")
public class AuthenticatedUser extends User {

    private static final long          serialVersionUID = 6151222121443498098L;
    private final sjph.life.authentication.user.User user;

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
            Collection<? extends GrantedAuthority> authorities, sjph.life.authentication.user.User user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.user = user;
    }

    public sjph.life.authentication.user.User getUserOfLife() {
        return this.user;
    }

}
