package com.waracle.cakemgr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Data
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    @NotNull
    private boolean corsEnabled;

    private List<String> allowedOrigins;

    private List<String> allowedMethods;

}
