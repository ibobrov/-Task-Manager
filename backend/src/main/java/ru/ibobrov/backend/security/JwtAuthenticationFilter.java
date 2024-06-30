package ru.ibobrov.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ibobrov.backend.model.dto.ExceptionDto;

import java.io.IOException;

import static ru.ibobrov.backend.controller.BaseController.JWT_TOKEN_EXPIRED_MSG;
import static ru.ibobrov.backend.controller.BaseController.JWT_TOKEN_INCORRECT_MSG;
import static ru.ibobrov.backend.model.dto.ExceptionDto.StatusDto.JWT_TOKEN_EXPIRED;
import static ru.ibobrov.backend.model.dto.ExceptionDto.StatusDto.JWT_TOKEN_INCORRECT;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorization = req.getHeader(AUTHORIZATION_HEADER_NAME);
            if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(req, resp);
                return;
            }

            final String jwtToken = authorization.substring(BEARER_PREFIX.length());
            final String username = tokenService.extractUsername(jwtToken);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (tokenService.isValid(jwtToken, userDetails.getUsername()) && userDetails.isEnabled()
                        && userDetails.isAccountNonLocked()) {

                    updateContext(userDetails, req);
                }
            }
        } catch (ExpiredJwtException e) {
            writeExceptionDtoToResp(resp, ExceptionDto.onError(JWT_TOKEN_EXPIRED_MSG, JWT_TOKEN_EXPIRED));
            return;
        } catch (SignatureException e) {
            writeExceptionDtoToResp(resp, ExceptionDto.onError(JWT_TOKEN_INCORRECT_MSG, JWT_TOKEN_INCORRECT));
            return;
        }

        filterChain.doFilter(req, resp);
    }

    private void updateContext(@NotNull UserDetails userDetails, @NotNull HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    private void writeExceptionDtoToResp(@NotNull HttpServletResponse resp, ExceptionDto exceptionDto) throws IOException {
        resp.setStatus(HttpStatus.FORBIDDEN.value());
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(OBJECT_MAPPER.writeValueAsString(exceptionDto));
    }
}
