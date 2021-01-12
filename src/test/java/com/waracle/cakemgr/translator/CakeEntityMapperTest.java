package com.waracle.cakemgr.translator;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeEntityMapperTest {

    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String TITLE = "title";

    @Test
    void convert() {
        CakeEntity cakeEntity = CakeEntity.builder()
                .description(DESCRIPTION)
                .image(IMAGE)
                .title(TITLE)
                .build();

        CakeDTO cakeDTO = CakeEntityMapper.INSTANCE.convert(cakeEntity);
        assertThat(cakeDTO.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(cakeDTO.getImage()).isEqualTo(IMAGE);
        assertThat(cakeDTO.getTitle()).isEqualTo(TITLE);
    }
}