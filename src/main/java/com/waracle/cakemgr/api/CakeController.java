package com.waracle.cakemgr.api;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class CakeController {

    private final CakeService cakeService;
    private final FormattingConversionService conversionService;

    @GetMapping("/cakes")
    public Flux<CakeDTO> getCakes() {
        return cakeService.getCakes().flatMap(cakeEntity ->
                Mono.just(Objects.requireNonNull(conversionService.convert(cakeEntity, CakeDTO.class))));
    }

    @PostMapping("/cakes")
    public Mono<CakeDTO> addCake(CakeDTO cakeDTO) {
        CakeEntity cakeEntity = conversionService.convert(cakeDTO, CakeEntity.class);

        return cakeService.addCake(cakeEntity).flatMap(cake ->
                Mono.just(Objects.requireNonNull(conversionService.convert(cake, CakeDTO.class))));
    }

}
