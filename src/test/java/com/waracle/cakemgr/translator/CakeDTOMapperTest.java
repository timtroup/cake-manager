package com.waracle.cakemgr.translator;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeDTOMapperTest {

    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String TITLE = "title";

    @Test
    void convert() {
        CakeDTO cakeDTO = CakeDTO.builder()
                .description(DESCRIPTION)
                .image(IMAGE)
                .title(TITLE)
                .build();
        CakeEntity cakeEntity = CakeDTOMapper.INSTANCE.convert(cakeDTO);
        assertThat(cakeEntity.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(cakeEntity.getImage()).isEqualTo(IMAGE);
        assertThat(cakeEntity.getTitle()).isEqualTo(TITLE);
    }
}