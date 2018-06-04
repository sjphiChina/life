package sjph.life.post.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @author shaohuiguo
 *
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig{
//public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @SuppressWarnings("javadoc")
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.
//    }
    
//    @Autowired
//    private ResourceServerProperties sso;
//
//    @Bean
//    public OAuth2RestOperations lifeOAuth2RestOperations() {
//        BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
//        resource.setClientId(this.sso.getClientId());
//        return new LifeOAuth2RestTemplate(resource);
//    }
//    
//    @Bean
//    public ResourceServerTokenServices myUserInfoTokenServices() {
//        LifeUserInfoTokenServices lifeUserInfoTokenServices = new LifeUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
//        lifeUserInfoTokenServices.setRestTemplate(lifeOAuth2RestOperations());
//        return lifeUserInfoTokenServices;
//    }
}
