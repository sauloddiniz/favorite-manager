package br.com.favoritmanager.adapter.input;

import br.com.favoritmanager.application.usecase.ProductUseCase;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/client/{clientId}/product")
@RestController
public class FavoriteProductController {

    private final ProductUseCase productUseCase;

    public FavoriteProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping("/{productIdLuizaLabs}/favorite")
    public String getItems(@PathVariable Long clientId, @PathVariable Long productIdLuizaLabs) {
        productUseCase.saveProduct(clientId, productIdLuizaLabs);
        return "items";
    }
}
