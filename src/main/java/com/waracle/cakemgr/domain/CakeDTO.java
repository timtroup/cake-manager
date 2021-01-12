package com.waracle.cakemgr.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeDTO {

    @Id
    @Column("id")
    private Integer id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("image")
    private String image;
}