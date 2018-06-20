package sjph.life.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Shaohui Guo
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
