package sjph.life.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import sjph.life.model.user.UserRole;


/**
 * @author shaohuiguo
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = true)
    @Qualifier("lifeUserDetailsService")
    private LifeUserDetailsService lifeUserDetailsService;

    // @Autowired
    // public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    // auth.inMemoryAuthentication().withUser("john").password("password").roles("USER");
    // auth.inMemoryAuthentication().withUser("admin").password("password").roles("USER","ADMIN");
    // }

    @SuppressWarnings("javadoc")
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(lifeUserDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin().loginPage("/login").usernameParameter("email")
                .passwordParameter("password");

        //httpSecurity.formLogin().defaultSuccessUrl("/posts/list").failureUrl("/login?error");
        httpSecurity.formLogin().failureUrl("/login?error");
        httpSecurity.formLogin().successHandler(getSuccessHandler("/posts/list"));

        httpSecurity.logout().logoutSuccessUrl("/login?logout");

        httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

        //@formatter:off
        httpSecurity.authorizeRequests().
                 antMatchers("/")
                    .permitAll().
                 antMatchers("/**/add")
                    .access("hasRole('" + UserRole.Role.USER.toString() + "')").
                 antMatchers("/**/settings/**")
                    .access("hasRole('" + UserRole.Role.USER.toString() + "')").
                 antMatchers("/**/admin/**")
                    .access("hasRole('" + UserRole.Role.ADMIN.toString() + "')");
        //@formatter:on

        httpSecurity.csrf().disable();
    }

    /**
     * @return a {@link DaoAuthenticationProvider}
     */
    @Bean
    public LifeAuthenticationProvider authenticationProvider() {
        LifeAuthenticationProvider authenticationProvider = new LifeAuthenticationProvider();
        authenticationProvider.setUserDetailsService(lifeUserDetailsService);
        return authenticationProvider;
    }
    
    @Bean
    public AuthenticationSuccessHandler getSuccessHandler(String defaultTargetUrl) {
        LifeWebsiteAuthenticationSuccessHandler lifeWebsiteAuthenticationSuccessHandler = new LifeWebsiteAuthenticationSuccessHandler();
        lifeWebsiteAuthenticationSuccessHandler.setDefaultTargetUrl(defaultTargetUrl);
        lifeWebsiteAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(false);
        return lifeWebsiteAuthenticationSuccessHandler;
    }
}
