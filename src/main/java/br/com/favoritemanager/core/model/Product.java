package br.com.favoritemanager.core.model;

import java.util.Objects;

public class Product {
    private Long productId;
    private String image;
    private Double price;
    private String title;
    private String reviewScore;
    private Client client;

    public Product(Long productId, String image, Double price, String title, String reviewScore) {
        this.productId = productId;
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(price, product.price) && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price, title);
    }
}
