/**
 * 
 */
package sjph.life.security.authentication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sjph.life.model.service.UserService;
import sjph.life.model.state.UserState;

/**
 * @author shaohuiguo
 *
 */
@Service("lifeUserDetailsService")
public class LifeUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(LifeUserDetailsService.class);

    @Autowired(required = true)
    private UserService         userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        sjph.life.model.User user = userService.findUser(email);
        if (user != null) {
            LOGGER.info("Found User: " + user.toString());
            LOGGER.info("USER role: " + UserRole.Role.USER.toString());
        }
        else {
            LOGGER.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> result = buildUserAuthority(user.getId(), null);
        return buildUserForAuthentication(user, result);
    }

    private List<GrantedAuthority> buildUserAuthority(Long userId, Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        // for (UserRole userRole : userRoles) {
        // setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        // }
        setAuths.add(new SimpleGrantedAuthority("ROLE_" + UserRole.Role.USER.toString()));

        // TODO will refine in the future
        if (userId == 1) {
            setAuths.add(new SimpleGrantedAuthority("ROLE_" + UserRole.Role.ADMIN.toString()));
        }
        // End

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);
        LOGGER.info("authorities : {}", authorities);
        return authorities;
    }

    private User buildUserForAuthentication(sjph.life.model.User user,
            List<GrantedAuthority> result) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        if (user.getUserState().equals(UserState.INACTIVE)) {
            enabled = false;
        }
        if (user.getUserState().equals(UserState.EXPIRED)) {
            accountNonExpired = false;
        }
        if (user.getUserState().equals(UserState.LOCKED)) {
            accountNonLocked = false;
        }
        if (user.getUserState().equals(UserState.CREDENTIAL_EXPIRED)) {
            credentialsNonExpired = false;
        }
        User userDetails = new User(user.getEmail(), user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, result);
        return userDetails;
    }
}
