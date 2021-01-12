package com.waracle.cakemgr.spring;

import com.waracle.cakemgr.translator.CakeDTOMapper;
import com.waracle.cakemgr.translator.CakeEntityMapper;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(CakeDTOMapper.INSTANCE);
        registry.addConverter(CakeEntityMapper.INSTANCE);
    }
}
