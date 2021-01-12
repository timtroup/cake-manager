package com.waracle.cakemgr.service;

import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.persistence.CakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CakeService {

    private final CakeRepository cakeRepository;

    public Flux<CakeEntity> getCakes() {
        return cakeRepository.findAll();
    }

    public Mono<CakeEntity> addCake(CakeEntity cake) {
        return cakeRepository.save(cake);
    }
}
