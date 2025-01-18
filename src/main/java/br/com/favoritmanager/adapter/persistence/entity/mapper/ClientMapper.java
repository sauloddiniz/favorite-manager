package br.com.favoritmanager.adapter.persistence.entity.mapper;

import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.core.model.Client;
import br.com.favoritmanager.core.model.Product;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientMapper {
    private ClientMapper() {
    }

    public static ClientEntity toEntity(Client client) {
        return ClientEntity.builder()
                .clientId(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }

    public static Client toModel(ClientEntity clientEntity) {
        Set<Product> products = Optional.ofNullable(clientEntity.getFavoriteProducts())
                .orElse(Set.of())
                .stream()
                .map(ProductMapper::toModel)
                .collect(Collectors.toSet());
        return new Client(clientEntity.getClientId(), clientEntity.getName(), clientEntity.getEmail(), products);
    }
}
