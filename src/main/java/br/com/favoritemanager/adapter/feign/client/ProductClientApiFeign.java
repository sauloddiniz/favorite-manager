package br.com.favoritemanager.adapter.feign.client;

import br.com.favoritemanager.adapter.feign.client.DTO.ProductResponseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "luizalabs-api-product", url = "${api.url}")
public interface ProductClientApiFeign {


    @GetMapping("/{id}")
    @Cacheable(value = "product-cached", key = "#id")
    ResponseEntity<ProductResponseDTO> getProductByIdLuizaLabs(@PathVariable Long id);
}
