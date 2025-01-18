package br.com.favoritmanager.core.model;

import br.com.favoritmanager.core.exception.ValueIsEmptyOrBlankException;

import java.util.HashSet;
import java.util.Set;

public class Client {

    public static final String NAME = "Name";
    public static final String EMAIL = "Email";

    private Long id;
    private String name;
    private String email;
    private Set<Product> favoriteProducts = new HashSet<>();

    public Client(String name, String email) {
        validName(name);
        this.name = name;
        validEmail(email);
        this.email = email;
    }

    public Client(Long id, String name, String email) {
        this.id = id;
        validName(name);
        this.name = name;
        validEmail(email);
        this.email = email;
    }

    public Client(Long id, String name, String email, Set<Product> favoriteProducts) {
        this.id = id;
        validName(name);
        this.name = name;
        validEmail(email);
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

    private void validName(String value){
        if (value == null || value.isEmpty()) {
            throw new ValueIsEmptyOrBlankException("Invalid name: " + value);
        }
    }

    private void validEmail(String value) {
        if (value == null || value.isEmpty() || !value.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new ValueIsEmptyOrBlankException("Invalid email: " + value + " | example: exemple@email.com");
        }
    }
}
