package com.waracle.cakemgr.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CakeRepository extends ReactiveCrudRepository<CakeEntity, Integer> {

    @Query("SELECT * FROM posts WHERE title like $1")
    Flux<CakeEntity> findByTitleContains(String name);
}
