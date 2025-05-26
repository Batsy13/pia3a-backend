package br.com.project.pi.application.infra.segurity;

import br.com.project.pi.application.exception.InvalidTokenException;
import br.com.project.pi.application.exception.MissingTokenException;
import br.com.project.pi.application.exception.model.ApiError;
import br.com.project.pi.application.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SecutiryFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = recoverToken(request);
            if (token == null) {
                throw new MissingTokenException();
            }

            var email = tokenService.validateToken(token);
            if (email == null || email.isEmpty()) {
                throw new InvalidTokenException();
            }

            userRepository.findByEmail(email).ifPresent(user -> {
                var authorities = user.getAuthorities().isEmpty() ?
                        List.of(new SimpleGrantedAuthority("ROLE_USER")) :
                        user.getAuthorities();

                var authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });

            filterChain.doFilter(request, response);

        } catch (MissingTokenException ex) {
            buildErrorResponse(response, HttpStatus.UNAUTHORIZED, ex);
        } catch (InvalidTokenException ex) {
            buildErrorResponse(response, HttpStatus.FORBIDDEN, ex);
        } catch (Exception ex) {
            buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR,
                    new InvalidTokenException());
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        return (authHeader == null || !authHeader.startsWith("Bearer ")) ?
                null : authHeader.substring(7);
    }

    private void buildErrorResponse(HttpServletResponse response,
                                    HttpStatus status,
                                    Exception ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .code(status.value())
                        .status(status.getReasonPhrase())
                        .errors(List.of(ex.getMessage()))
                        .build()
        ));
    }
}