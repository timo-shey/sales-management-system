package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.LoginDTO;
import com.example.salesmanagementsystem.dto.RegisterDTO;
import com.example.salesmanagementsystem.exception.AppException;
import com.example.salesmanagementsystem.exception.UserNotFoundException;
import com.example.salesmanagementsystem.model.RefreshToken;
import com.example.salesmanagementsystem.model.User;
import com.example.salesmanagementsystem.repository.RefreshTokenRepository;
import com.example.salesmanagementsystem.repository.UserRepository;
import com.example.salesmanagementsystem.security.JwtTokenProvider;
import com.example.salesmanagementsystem.service.UserService;
import com.example.salesmanagementsystem.service.RefreshTokenService;
import com.example.salesmanagementsystem.service.TokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklist tokenBlacklist;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public String registerUser(RegisterDTO registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email already exists: " + registerDto.getEmail());
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Username already exists: " + registerDto.getUsername());
        }

        userRepository.save(User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(registerDto.getRole())
                .build()
        );
        return "User Registered Successfully";
    }

    @Override
    public JWTAuthResponse LoginUser(LoginDTO loginDto) {
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
    public String logoutUser(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String refreshToken = request.getHeader("Refresh-Token");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            tokenBlacklist.addToBlacklist(authorizationHeader.substring(7));

            refreshTokenRepository.findByToken(refreshToken).ifPresent(refreshTokenRepository::delete);

            return "User Successfully Logged Out.";
        } else {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Authorization Header is missing or invalid.");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() ->
                new UserNotFoundException("User with email: " + email + " Not Found",HttpStatus.BAD_REQUEST,"Contact Admin"));
    }
}
