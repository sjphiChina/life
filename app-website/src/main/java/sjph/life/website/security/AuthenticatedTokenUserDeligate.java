package sjph.life.website.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedTokenUserDeligate extends AuthenticationResponse
        implements Authentication {

    private Authentication deligate;
    private String         userName;

    /**
     * @param deligate
     */
    public AuthenticatedTokenUserDeligate(Authentication deligate) {
        super();
        this.deligate = deligate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "AuthenticatedTokenUserDeligate [userName=" + userName + ", getUserName()="
                + getUserName() + ", getName()=" + getName() + ", getAuthorities()="
                + getAuthorities() + ", getCredentials()=" + getCredentials() + ", getDetails()="
                + getDetails() + ", super.toString()=" + super.toString() + "]";
    }

    @Override
    public String getName() {
        return deligate.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return deligate.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return deligate.getCredentials();
    }

    @Override
    public Object getDetails() {
        return deligate.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return deligate.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return deligate.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        deligate.setAuthenticated(isAuthenticated);
    }

}
