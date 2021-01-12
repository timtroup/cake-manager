package com.waracle.cakemgr.translator;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapperSpringConfig.class)
public abstract class CakeEntityMapper implements Converter<CakeEntity, CakeDTO> {

    public static CakeEntityMapper INSTANCE = Mappers.getMapper(CakeEntityMapper.class);

    public abstract CakeDTO convert(CakeEntity entity);

}