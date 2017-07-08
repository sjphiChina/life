package sjph.life.app.rest.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import sjph.life.app.rest.interceptor.ProcessingTimeLogInterceptor;

/**
 * @author shaohuiguo
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "sjph.life")
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER   = LogManager.getLogger(WebApplicationContextConfig.class);

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    /**
//     * @return
//     */
//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//
//        return resolver;
//    }

    // @Override
    // public void configurePathMatch(PathMatchConfigurer configurer) {
    // UrlPathHelper urlPathHelper = new UrlPathHelper();
    // urlPathHelper.setRemoveSemicolonContent(false);
    //
    // configurer.setUrlPathHelper(urlPathHelper);
    // }

//    /**
//     * @return
//     */
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
//        resource.setBasename("messages");
//        return resource;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("file:///./data/local/life/data/images/posts/");
//    }
//
//    /**
//     * @return
//     */
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        return resolver;
//    }

    /**
     * @return a MappingJackson2JsonView
     */
    @Bean
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrettyPrint(true);

        return jsonView;
    }

    // @Bean
    // public MarshallingView xmlView() {
    // Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // marshaller.setClassesToBeBound(Product.class);
    //
    // MarshallingView xmlView = new MarshallingView(marshaller);
    // return xmlView;
    // }

    /**
     * @param manager ContentNegotiationManager
     * @return a ViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        List<View> views = new ArrayList<>();
        views.add(jsonView());
        resolver.setDefaultViews(views);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessingTimeLogInterceptor());
        LOGGER.debug("Add ProcessingTimeLogInterceptor");
    }
    //
    // @Bean
    // public LocaleResolver localeResolver() {
    // SessionLocaleResolver resolver = new SessionLocaleResolver();
    // resolver.setDefaultLocale(new Locale("en"));
    //
    // return resolver;
    // }
    //
    // @Bean
    // public HandlerInterceptor promoCodeInterceptor() {
    // PromoCodeInterceptor promoCodeInterceptor = new PromoCodeInterceptor();
    // promoCodeInterceptor.setPromoCode("OFF3R");
    // promoCodeInterceptor.setOfferRedirect("market/products");
    // promoCodeInterceptor.setErrorRedirect("invalidPromoCode");
    //
    // return promoCodeInterceptor;
    // }
    //
    // @Bean(name = "validator")
    // public LocalValidatorFactoryBean validator() {
    // LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    // bean.setValidationMessageSource(messageSource());
    // return bean;
    // }
    //
    // @Override
    // public Validator getValidator() {
    // return validator();
    // }
    //
    // @Bean
    // public ProductValidator productValidator() {
    // Set<Validator> springValidators = new HashSet<>();
    // springValidators.add(new UnitsInStockValidator());
    //
    // ProductValidator productValidator = new ProductValidator();
    // productValidator.setSpringValidators(springValidators);
    //
    // return productValidator;
    // }

}
