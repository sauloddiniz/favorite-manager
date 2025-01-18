package br.com.favoritmanager.adapter.feign.client;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "luizalabs-api-product", url = "${api.url}")
public interface ProductClientApiFeign {

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDTO> getProductByIdLuizaLabs(@PathVariable Long id);
}
