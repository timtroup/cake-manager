package com.waracle.cakemgr.api;

import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.persistence.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CakeController.class)
@Import(CakeService.class)
class CakeControllerTest {

    @MockBean
    private CakeRepository cakeRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getCakes() {
        CakeEntity cakeEntity = new CakeEntity();
        Mockito.when(cakeRepository.findAll()).thenReturn(Flux.just(cakeEntity));

        webClient.get()
                .uri("/cakes")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        Mockito.verify(cakeRepository, times(1)).findAll();
    }

    @Test
    void postCake() {
        CakeEntity cakeEntity = new CakeEntity();
        Mockito.when(cakeRepository.save(cakeEntity)).thenReturn(Mono.just(cakeEntity));

        webClient.post()
                .uri("/cakes")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        Mockito.verify(cakeRepository, times(1)).save(cakeEntity);
    }
}