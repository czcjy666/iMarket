package com.imarket.api.client;

import com.imarket.api.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "cart-service", path = "/carts") // 要调用的服务名称
public interface CartClient {

    @DeleteMapping
    public void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);
}
