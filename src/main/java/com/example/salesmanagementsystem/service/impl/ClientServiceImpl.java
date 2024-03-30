package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.*;
import com.example.salesmanagementsystem.exception.AppException;
import com.example.salesmanagementsystem.exception.ClientNotFoundException;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.model.RefreshToken;
import com.example.salesmanagementsystem.repository.RefreshTokenRepository;
import com.example.salesmanagementsystem.repository.ClientRepository;
import com.example.salesmanagementsystem.security.JwtTokenProvider;
import com.example.salesmanagementsystem.service.ClientService;
import com.example.salesmanagementsystem.service.RefreshTokenService;
import com.example.salesmanagementsystem.service.TokenBlacklist;
import com.example.salesmanagementsystem.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklist tokenBlacklist;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtil userUtil;
    private final ModelMapper mapper;


    @Override
    public String registerClient(ClientRequestDTO clientRequestDto) {
        if(clientRepository.existsByEmail(clientRequestDto.getEmail())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email already exists: " + clientRequestDto.getEmail());
        }

        if (clientRepository.existsByUsername(clientRequestDto.getUsername())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Username already exists: " + clientRequestDto.getUsername());
        }

        clientRepository.save(Client.builder()
                .email(clientRequestDto.getEmail())
                .username(clientRequestDto.getUsername())
                .firstName(clientRequestDto.getFirstName())
                .lastName(clientRequestDto.getLastName())
                .mobile(clientRequestDto.getMobile())
                .address(clientRequestDto.getAddress())
                .password(passwordEncoder.encode(clientRequestDto.getPassword()))
                .role(clientRequestDto.getRole())
                .build()
        );
        return "Client Registered Successfully";
    }

    @Override
    public JWTAuthResponse LoginClient(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginDto.getUsername());

            return JWTAuthResponse.builder()
                .accessToken(jwtTokenProvider.generateToken(loginDto.getUsername()))
                .tokenType("Bearer")
                .refreshToken(refreshToken.getToken())
                .build();
        } catch (AuthenticationException e) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    @Override
    public ClientResponseDTO updateClient(ClientRequestDTO clientRequestDTO) {
        String clientUsername = userUtil.getAuthenticatedUsername();
        Client client = clientRepository.findByUsername(clientUsername).orElseThrow(
            ()-> new ClientNotFoundException(HttpStatus.NOT_FOUND, "Client not found")
        );

        client.setFirstName(clientRequestDTO.getFirstName());
        client.setLastName(clientRequestDTO.getLastName());
        client.setMobile(clientRequestDTO.getMobile());
        client.setEmail(clientRequestDTO.getEmail());
        client.setAddress(clientRequestDTO.getAddress());

        Client updatedClient = clientRepository.save(client);

        return mapToDto(updatedClient);
    }

    @Override
    public ClientResponseDTO getClientByID(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
            ()-> new ClientNotFoundException(HttpStatus.NOT_FOUND, "Client not found.")
        );

        return mapToDto(client);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public String deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
            ()-> new ClientNotFoundException(HttpStatus.NOT_FOUND, "Client not found")
        );
        clientRepository.delete(client);
        return "Client has been deleted successfully.";
    }

    @Override
    public String logoutUser(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String refreshToken = request.getHeader("Refresh-Token");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            tokenBlacklist.addToBlacklist(authorizationHeader.substring(7));

            refreshTokenRepository.findByToken(refreshToken).ifPresent(refreshTokenRepository::delete);

            return "Client Successfully Logged Out.";
        } else {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Authorization Header is missing or invalid.");
        }
    }

    private Client mapToEntity(ClientRequestDTO clientDTO) {
        return mapper.map(clientDTO, Client.class);
    }

    private ClientResponseDTO mapToDto(Client client) {
        return mapper.map(client, ClientResponseDTO.class);
    }
}
