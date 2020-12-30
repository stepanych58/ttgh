package com.stbegradleapp.fixer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
/**
 * @author Neil Alishev
 */
@Configuration
@ComponentScan("com.stbegradleapp.fixer")
@ConfigurationPropertiesScan("com.stbegradleapp.fixer")
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:3000/");
        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedOrigin("http://127.0.0.1:3000/");
//        config.addAllowedOrigin("http://127.0.0.1:3000");
//        config.addAllowedOrigin("3000");
//        config.addAllowedOrigin("localhost:3000");
//        config.addAllowedOrigin("localhost:3000/");
//        config.addAllowedOrigin("127.0.0.1:3000/");
//        config.addAllowedOrigin("127.0.0.1:3000");
        config.setAllowedHeaders(Collections.unmodifiableList(
                Collections.singletonList(CorsConfiguration.ALL)));
        config.setAllowedMethods(Collections.unmodifiableList(
                Collections.singletonList(CorsConfiguration.ALL)));
        source.registerCorsConfiguration("/**", config);
        System.out.println("STBE CONFIGURED");
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }
//    private final ApplicationContext applicationContext;
//
//    @Autowired
//    public SpringConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public FilterRegistrationBean hiddenHttpMethodFilter(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
//        return filterRegistrationBean;
//    }
}
