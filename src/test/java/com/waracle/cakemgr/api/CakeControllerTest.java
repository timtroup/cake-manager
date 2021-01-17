package com.waracle.cakemgr.api;

import com.waracle.cakemgr.domain.CakeDTO;
import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.persistence.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CakeController.class)
@Import(CakeService.class)
class CakeControllerTest {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    @MockBean
    private CakeRepository cakeRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getCakes() {
        CakeEntity cakeEntity = CakeEntity.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .image(IMAGE)
                .build();

        Mockito.when(cakeRepository.findAll()).thenReturn(Flux.just(cakeEntity));

        webClient.get()
                .uri("/cakes")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].title").isEqualTo(TITLE)
                .jsonPath("$[0].description").isEqualTo(DESCRIPTION)
                .jsonPath("$[0].image").isEqualTo(IMAGE);

        Mockito.verify(cakeRepository, times(1)).findAll();
    }

    @Test
    void postCake() {
        ArgumentCaptor<CakeEntity> cakeEntityArgumentCaptor = ArgumentCaptor.forClass(CakeEntity.class);

        CakeEntity cakeEntity = new CakeEntity();
        Mockito.when(cakeRepository.save(any(CakeEntity.class))).thenReturn(Mono.just(cakeEntity));

        CakeDTO cakeDTO = CakeDTO.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .image(IMAGE)
                .build();

        webClient.post()
                .uri("/cakes")
                .body(BodyInserters.fromValue(cakeDTO))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        Mockito.verify(cakeRepository, times(1)).save(cakeEntityArgumentCaptor.capture());
        CakeEntity value = cakeEntityArgumentCaptor.getValue();
        assertThat(value.getTitle()).isEqualTo(TITLE);
        assertThat(value.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(value.getImage()).isEqualTo(IMAGE);
    }
}