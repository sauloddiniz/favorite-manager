package br.com.favoritmanager.core.model;

import br.com.favoritmanager.core.exception.ValueIsEmptyOrBlankException;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private Long id;
    private String name;
    private String email;
    private Set<Product> favoriteProducts = new HashSet<>();

    public Client(String name, String email) {
        validValue(name, "Name");
        this.name = name;
        validValue(email, "Email");
        this.email = email;
    }

    public Client(Long id, String name, String email) {
        this.id = id;
        validValue(name, "Name");
        this.name = name;
        validValue(email, "Email");
        this.email = email;
    }

    public Client(Long id, String name, String email, Set<Product> favoriteProducts) {
        this.id = id;
        validValue(name, "Name");
        this.name = name;
        validValue(email, "Email");
        this.email = email;
        this.favoriteProducts = favoriteProducts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void getOnlyRegister(Product favoriteProducts) {
        this.favoriteProducts = Set.of(favoriteProducts);
    }

    public boolean addProductInFavorite(Product favoriteProducts) {
        favoriteProducts.setClient(this);
        return this.favoriteProducts.add(favoriteProducts);
    }

    private void validValue(String value, String type){
        if (value == null || value.isEmpty()) {
            throw new ValueIsEmptyOrBlankException(type);
        }
    };
}
