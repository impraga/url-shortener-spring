package com.praga.urlshortener.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "url_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String shortCode;

    @Column(nullable = false)
    private String longUrl;

    private Long clickCount;
}