package br.com.favoritemanager.core.model;

import java.util.Objects;

public class Product {
    private Long productId;
    private Long productExternalId;
    private String image;
    private Double price;
    private String title;
    private String reviewScore;
    private Client client;

    public Product(Long productExternalId, String image, Double price, String title, String reviewScore) {
        this.productExternalId = productExternalId;
        this.image = image;
        this.price = price;
        this.title = title;
        this.reviewScore = reviewScore;
    }

    public Product(Long productId, Long productExternalId, String image, Double price, String title, String reviewScore) {
        this.productId = productId;
        this.productExternalId = productExternalId;
        this.image = image;
        this.price = price;
        this.title = title;
        this.reviewScore = reviewScore;
    }

    public String getImage() {
        return image;
    }

    public Double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public Long getProductId() {
        return productId;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getProductExternalId() {
        return productExternalId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productExternalId, product.productExternalId) && Objects.equals(image, product.image) && Objects.equals(price, product.price) && Objects.equals(title, product.title) && Objects.equals(reviewScore, product.reviewScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productExternalId, image, price, title, reviewScore);
    }
}
