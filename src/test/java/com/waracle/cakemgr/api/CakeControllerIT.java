package com.waracle.cakemgr.api;

import com.waracle.cakemgr.domain.CakeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeControllerIT {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldCreateNewCake() {
        webClient.get()
                .uri("/cakes")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(20);

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

        webClient.get()
                .uri("/cakes")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(21);

    }
}
