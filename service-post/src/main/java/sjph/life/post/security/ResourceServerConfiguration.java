package sjph.life.post.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers(HttpMethod.GET).anonymous();
        http
        .authorizeRequests()
          .antMatchers(HttpMethod.DELETE, "/v1/post/**")
          .hasRole("ADMIN");
        http
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, "/v1/post/**").authenticated();
        http
        .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/v1/post/**").authenticated();
        http
        .authorizeRequests()
          .antMatchers(HttpMethod.PUT, "/v1/post/**").authenticated();
//        http
//        .authorizeRequests()
//          .antMatchers(HttpMethod.DELETE, "/v1/post/**")
//          .hasRole("ADMIN")
//          .anyRequest()
//          .authenticated();
    }
}
