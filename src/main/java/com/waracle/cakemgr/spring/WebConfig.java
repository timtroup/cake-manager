package com.waracle.cakemgr.spring;

import com.waracle.cakemgr.config.ApplicationConfig;
import com.waracle.cakemgr.translator.CakeDTOMapper;
import com.waracle.cakemgr.translator.CakeEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@ComponentScan(value = "com.waracle.cakemgr.config")
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(CakeDTOMapper.INSTANCE);
        registry.addConverter(CakeEntityMapper.INSTANCE);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (applicationConfig.isCorsEnabled()) {
            registry.addMapping("/cakes")
                    .allowedOrigins(applicationConfig.getAllowedOrigins().toArray(new String[0]))
                    .allowedMethods(applicationConfig.getAllowedMethods().toArray(new String[0]));
        }
    }
}
