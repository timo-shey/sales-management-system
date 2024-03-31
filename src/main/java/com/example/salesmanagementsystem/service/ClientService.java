package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.clients.ClientResponseDTO;
import com.example.salesmanagementsystem.dto.auth.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.auth.LoginDTO;
import com.example.salesmanagementsystem.dto.clients.ClientRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ClientService {
    String registerClient(ClientRequestDTO clientRequestDto);
    JWTAuthResponse LoginClient(LoginDTO loginDto);
    ClientResponseDTO updateClient(ClientRequestDTO clientRequestDTO);
    String deleteClient(Long id);
    String logoutUser(HttpServletRequest request);
    ClientResponseDTO getClientByID(Long id);
    List<ClientResponseDTO> getAllClients();
}
