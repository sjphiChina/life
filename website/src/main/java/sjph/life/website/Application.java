package sjph.life.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Shaohui Guo
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class Application extends SpringBootServletInitializer {

    private static Class applicationClass = Application.class;

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }
    
    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
