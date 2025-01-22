package br.com.favoritemanager.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "product", schema = "favorite")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_EXTERNAL_ID")
    private Long productExternalId;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "REVIEW_SCORE")
    private String reviewScore;

    @ManyToOne()
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;
}
