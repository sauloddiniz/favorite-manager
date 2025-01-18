package br.com.favoritmanager.adapter.persistence.entity.mapper;

import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.core.model.Client;

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
        return new Client(clientEntity.getClientId(), clientEntity.getName(), clientEntity.getEmail());
    }
}
