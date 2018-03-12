package sjph.life.authentication.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author shaohuiguo
 *
 */
@Service("lifeUserDetailsService")
public class LifeUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(LifeUserDetailsService.class);

    @Autowired(required = true)
    private PersonRestTemplateClient         personRestTemplateClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = personRestTemplateClient.getUser(email);
        if (user != null) {
            logger.info("Found User: " + user.toString());
            logger.info("USER role: " + UserRole.Role.USER.toString());
        }
        else {
            logger.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> result = buildUserAuthority(user.getId());
        return buildUserForAuthentication(user, result);
    }

    // private List<GrantedAuthority> buildUserAuthority(Long userId, Set<UserRole> userRoles) {
    private List<GrantedAuthority> buildUserAuthority(Long userId) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

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

        List<GrantedAuthority> authorities = new ArrayList<>(setAuths);
        logger.info("authorities : {}", authorities);
        return authorities;
    }

    private AuthenticatedUser buildUserForAuthentication(User user,
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
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user.getEmail(),
                user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, result, user);
        return authenticatedUser;
    }
}
