package br.com.favoritmanager.adapter.persistence.repository;

import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByEmail(String email);
}
