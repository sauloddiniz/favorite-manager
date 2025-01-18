package br.com.favoritmanager.adapter.input;

import br.com.favoritmanager.adapter.input.DTO.ClientRequestDTO;
import br.com.favoritmanager.adapter.input.DTO.ClientResponseDTO;
import br.com.favoritmanager.application.usecase.ClientUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientUseCase clientUseCase;

    public ClientController(ClientUseCase clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO responseDTO = clientUseCase.saveClient(clientRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/client/{id}")
                .buildAndExpand(responseDTO.id().toString())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long id) {
        ClientResponseDTO responseDTO = clientUseCase.getClient(id);
        return responseDTO == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) {
        clientUseCase.updateClient(id, clientRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteClient(@PathVariable Long id) {
        clientUseCase.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
