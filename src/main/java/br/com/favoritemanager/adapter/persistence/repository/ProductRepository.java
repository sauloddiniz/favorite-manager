package br.com.favoritemanager.adapter.persistence.repository;

import br.com.favoritemanager.adapter.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Modifying
    @Query("DELETE FROM ProductEntity WHERE client.clientId = :clientId AND productExternalId = :productId")
    void deleteByClientIdAndProductExternalId(@Param("clientId") Long clientId,
                                               @Param("productId") Long productId);
}
