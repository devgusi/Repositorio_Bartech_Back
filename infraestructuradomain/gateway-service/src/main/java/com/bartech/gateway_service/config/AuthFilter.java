package com.bartech.gateway_service.config;

import com.bartech.gateway_service.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private WebClient.Builder webClient;

    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            logger.info("Path: " + exchange.getRequest().getPath());
            String path = exchange.getRequest().getPath().toString();
            if(path.contains("user/song")) {
                return chain.filter(exchange);
            }
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String [] chunks = tokenHeader.split(" ");
            if(chunks.length != 2 || !chunks[0].equals("Bearer"))
                return onError(exchange, HttpStatus.BAD_REQUEST);
            return webClient.build()
                .post()
                .uri("http://businessdomain-auth/auth/V1/validate?token=" + chunks[1])
                .retrieve().bodyToMono(TokenDto.class)
                .map(t -> {
                    t.getToken();
                    return exchange;
                }).flatMap(chain::filter);
        }));
    }

    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {}

}
