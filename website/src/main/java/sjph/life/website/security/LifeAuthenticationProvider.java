package sjph.life.website.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class LifeAuthenticationProvider extends DaoAuthenticationProvider {

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Authentication deligate = super.authenticate(authentication);
        return new AuthenticatedTokenUserDeligate(deligate);
    }
}
