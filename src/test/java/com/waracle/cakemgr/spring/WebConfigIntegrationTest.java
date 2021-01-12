package com.waracle.cakemgr.spring;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WebAppConfiguration
@EnableAutoConfiguration
class WebConfigIntegrationTest {

    @Autowired
    @Qualifier("webFluxConversionService")
    FormattingConversionService conversionService;

    @Test
    void shortMessageDTOtoShortMessage() {
        assertThat(conversionService.canConvert(CakeDTO.class, CakeEntity.class)).isTrue();
        assertThat(conversionService.canConvert(CakeEntity.class, CakeDTO.class)).isTrue();
    }
}