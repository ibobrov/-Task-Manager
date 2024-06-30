package ru.ibobrov.backend.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.ibobrov.backend.model.dto.AuthenticationRequest;
import ru.ibobrov.backend.model.dto.AuthenticationResponse;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        final UserDetails user = userDetailsService.loadUserByUsername(authRequest.getEmail());
        return new AuthenticationResponse(tokenService.generateToken(user.getUsername()), tokenService.tokenExpiration);
    }
}
