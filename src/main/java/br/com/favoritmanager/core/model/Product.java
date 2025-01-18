package br.com.favoritmanager.core.model;

import java.util.Objects;

public class Product {
    private Long id;
    private Long productIdLuizaLabs;
    private String image;
    private Double price;
    private String title;
    private String reviewScore;
    private Client client;

    public Product(Long productIdLuizaLabs, String image, Double price, String title, String reviewScore) {
        this.productIdLuizaLabs = productIdLuizaLabs;
        this.image = image;
        this.price = price;
        this.title = title;
        this.reviewScore = reviewScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(String reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Long getProductIdLuizaLabs() {
        return productIdLuizaLabs;
    }

    public void setProductIdLuizaLabs(Long productIdLuizaLabs) {
        this.productIdLuizaLabs = productIdLuizaLabs;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product item = (Product) o;
        return Objects.equals(id, item.id) && Objects.equals(image, item.image) && Objects.equals(price, item.price) && Objects.equals(title, item.title) && Objects.equals(reviewScore, item.reviewScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, price, title, reviewScore);
    }
}
