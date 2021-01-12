package com.waracle.cakemgr.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("cake")
public class CakeEntity {

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