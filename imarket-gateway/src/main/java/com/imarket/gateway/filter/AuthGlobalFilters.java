package com.imarket.gateway.filter;

import com.imarket.common.exception.UnauthorizedException;
import com.imarket.gateway.config.AuthProperties;
import com.imarket.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilters implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();
        //2.判断需不需要拦截(登陆注册等不需要拦截)
        if(isExclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        //3.获取token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if (headers != null && !headers.isEmpty()){
            token = headers.get(0);
        }
        //4.检验并解析token获取userid
        Long userId;
        try {
            userId = jwtTool.parseToken(token);
        }catch (UnauthorizedException e){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //5.如果检验通过，则将userid放入header中，继续向后执行
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                .header("user-info", userId.toString())
                .build();

        ServerWebExchange newExchange = exchange.mutate()
                .request(newRequest)
                .build();
        //6.放行
        return chain.filter(newExchange);
    }

    private boolean isExclude(String path) {
        for (String excludePath : authProperties.getExcludePaths()){
            if (antPathMatcher.match(excludePath, path)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
