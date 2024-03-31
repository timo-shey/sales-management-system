package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.clients.ClientRequestDTO;
import com.example.salesmanagementsystem.dto.clients.ClientResponseDTO;
import com.example.salesmanagementsystem.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ClientController", description = "APIs for Creating, Fetching, Updating and Deleting Clients.")
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "REST API to get a client. Both the CLIENT and ADMIN can access this API.", tags = "ClientController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientByID(id));
    }

    @Operation(summary = "REST API to get all clients. Both the CLIENT and ADMIN can access this API.", tags = "ClientController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping
    ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Operation(summary = "REST API to update a client. Both the CLIENT and ADMIN can access this API.", tags = "ClientController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PutMapping
    ResponseEntity<ClientResponseDTO> updateClient(@RequestBody ClientRequestDTO client) {
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    @Operation(summary = "REST API to delete a client. Only CLIENT can access this API.", tags = "ClientController")
    @ApiResponse(responseCode = "204", description = "Http Status 204 NO CONTENT")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.NO_CONTENT);
    }
}
