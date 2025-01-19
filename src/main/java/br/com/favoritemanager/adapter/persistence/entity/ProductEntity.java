package br.com.favoritemanager.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PRODUCT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    @EqualsAndHashCode.Include
    private Long productId;

    @Column(name = "PRODUCT_ID_LUIZA_LABS")
    @EqualsAndHashCode.Include
    private Long productIdLuizaLabs;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "TITLE")
    @EqualsAndHashCode.Include
    private String title;

    @Column(name = "REVIEW_SCORE")
    private String reviewScore;

    @ManyToOne()
    @JoinColumn(name = "CLIENT_ID")
    @EqualsAndHashCode.Include
    private ClientEntity client;
}
