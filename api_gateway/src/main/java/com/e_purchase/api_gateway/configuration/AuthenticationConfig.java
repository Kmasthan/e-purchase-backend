package com.e_purchase.api_gateway.configuration;

import com.e_purchase.api_gateway.utils.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthenticationConfig implements GlobalFilter, Ordered {

    private static final String BEARER = "Bearer ";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationConfig.class);

    private final JwtTokenValidator jwtTokenValidator;

    public AuthenticationConfig(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/authentication/")) {
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            return setUnauthorizedResponse(exchange);
        }

        try {
            Claims claims = jwtTokenValidator.validateToken(authHeader.substring(7));
            if (Objects.isNull(claims)) {
                return setUnauthorizedResponse(exchange);
            }
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.set("USER-ID", String.valueOf(claims.get("user_id")));
                        headers.set("USER-NAME", String.valueOf(claims.get("sub")));
                        headers.set("ROLE", String.valueOf(claims.get("role")));
                    })).build();
            LOGGER.info("User {} allowed to access the endpoint: {}", claims.get("sub"), path);
            return chain.filter(mutatedExchange);
        } catch (JwtException e) {
            return setUnauthorizedResponse(exchange);
        }
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        LOGGER.info("Unauthorized access attempt");
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}