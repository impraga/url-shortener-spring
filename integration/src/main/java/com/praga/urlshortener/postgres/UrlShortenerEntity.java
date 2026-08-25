package com.praga.urlshortener.postgres;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "url_shortener")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortenerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "snowflake_id",
        nullable = false,
        unique = true)
    private long snowflakeId;

    @Column(name = "short_code",
        nullable = false,
        unique = true,
        length = 20)
    private String shortCode;

    @Column(name = "original_url",
        nullable = false,
        length = 2048)
    private String originalUrl;

//    @Column(name = "click_count")
//    private long clickCount;
//
//    @Column(name = "active")
//    private Boolean active;

    @Column(name = "created_at",
        nullable = false)
    private String createdAt;

}
