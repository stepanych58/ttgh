package com.stbegradleapp.fixer.config;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
@Configuration
@EnableAutoConfiguration
@ConfigurationPropertiesScan("com.stbegradleapp.fixer")
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:3000/l");
//        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedOrigin("http://127.0.0.1:3000/l");
//        config.addAllowedOrigin("http://127.0.0.1:3000");
//        config.addAllowedOrigin("3000");
//        config.addAllowedOrigin("localhost:3000");
//        config.addAllowedOrigin("localhost:3000/l");
//        config.addAllowedOrigin("127.0.0.1:3000/l");
//        config.addAllowedOrigin("127.0.0.1:3000");
//        config.setAllowedHeaders(Collections.unmodifiableList(
//                Collections.singletonList(CorsConfiguration.ALL)));
//        config.setAllowedMethods(Collections.unmodifiableList(
//                Collections.singletonList(CorsConfiguration.ALL)));
//        config.setAllowedOriginPatterns(Collections.unmodifiableList(
//                Collections.singletonList(CorsConfiguration.ALL)));
////        config.setAllowedOrigins(Collections.unmodifiableList(
////                Collections.singletonList(CorsConfiguration.ALL)));
////        config.
//        source.registerCorsConfiguration("/**", config);
//
//        System.out.println("STBE CONFIGURED");
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurer() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                config.setBasePath("/api");
            }
        };
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }

//    @Bean
//    public InMemoryTokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }

    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
}
