package com.waracle.cakemgr.translator;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapperSpringConfig.class)
public abstract class CakeDTOMapper implements Converter<CakeDTO, CakeEntity> {

    public static CakeDTOMapper INSTANCE = Mappers.getMapper(CakeDTOMapper.class);

    public abstract CakeEntity convert(CakeDTO dto);

}