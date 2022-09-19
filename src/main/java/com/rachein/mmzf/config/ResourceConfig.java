package com.rachein.mmzf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    private static final String PATH = "C:/Users/Rache/IdeaProjects/mmzf/src/main/resources/static/";

    @Value("${resource.path.former}")
    private String former;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("i/**").addResourceLocations("file:"+PATH);
        registry.addResourceHandler("i/**").addResourceLocations("file:"+former);
//        addResourceHandlers(registry);
    }
}
