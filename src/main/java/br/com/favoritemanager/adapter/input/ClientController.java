package br.com.favoritemanager.adapter.input;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientRequestDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientResponseDTO;
import br.com.favoritemanager.application.usecase.ClientUseCase;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<ClientAndListFavoritesResponseDTO>> getAllClients(@PathParam("favorites") boolean favorites) {
        return ResponseEntity.ok(clientUseCase.getAllClients(favorites));
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
