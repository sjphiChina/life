package sjph.life.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import sjph.life.model.user.UserRole;
import sjph.life.security.authentication.LifeUserDetailsService;


/**
 * @author shaohuiguo
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean("lifeUserDetailsService")
    @DependsOn
    public LifeUserDetailsService getLifeUserDetailsService(){
        return new LifeUserDetailsService();
    }

    // @Autowired
    // public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    // auth.inMemoryAuthentication().withUser("john").password("password").roles("USER");
    // auth.inMemoryAuthentication().withUser("admin").password("password").roles("USER","ADMIN");
    // }

    @SuppressWarnings("javadoc")
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getLifeUserDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin().loginPage("/login").usernameParameter("email")
                .passwordParameter("password");

        httpSecurity.formLogin().defaultSuccessUrl("/posts/list").failureUrl("/login?error");

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
    //@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getLifeUserDetailsService());
        return authenticationProvider;
    }
}
