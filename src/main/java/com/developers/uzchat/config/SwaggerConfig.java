//package com.developers.uzchat.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.DescriptionResolver;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig {
//
//    private final Environment environment;
//
//    public SwaggerConfig(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Bean
//    public DescriptionResolver descriptionResolver() {
//        return new DescriptionResolver(environment);
//    }
//}
