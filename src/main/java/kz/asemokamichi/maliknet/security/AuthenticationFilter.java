package kz.asemokamichi.maliknet.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.asemokamichi.maliknet.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String BASIC_PREFIX = "Basic ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtUtil jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = request.getHeader(HEADER_NAME);

        if (StringUtils.isEmpty(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader.startsWith(BASIC_PREFIX)) {
            handleBasicAuth(authHeader, filterChain, request, response);
        } else if (authHeader.startsWith(BEARER_PREFIX)) {
            handleBearerToken(authHeader, filterChain, request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void handleBasicAuth(String authHeader, FilterChain filterChain, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {
        try {
            String credentials = authHeader.substring(BASIC_PREFIX.length());
            String decodedCredentials = new String(Base64.getDecoder().decode(credentials));
            String[] parts = decodedCredentials.split(":", 2);

            if (parts.length != 2) {
                log.warn("Invalid Basic Authentication format");
                filterChain.doFilter(request, response);
                return;
            }

            String username = parts[0];
            String password = parts[1];

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                setSecurityContext(userDetails, request);
                filterChain.doFilter(request, response);
            } else {
                log.warn("Invalid username or password");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
            }
        } catch (IllegalArgumentException e) {
            log.error("Error decoding Basic Authentication header", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authentication Header");
        }
    }


    private void handleBearerToken(String authHeader, FilterChain filterChain, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        try {
            var jwt = authHeader.substring(BEARER_PREFIX.length());
            var login = jwtService.extractUserName(jwt);

            if (StringUtils.isNotEmpty(login) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    setSecurityContext(userDetails, request);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error processing Bearer token", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }


    private void setSecurityContext(UserDetails userDetails, HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
