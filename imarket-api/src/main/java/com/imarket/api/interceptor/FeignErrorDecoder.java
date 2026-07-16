package com.imarket.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imarket.common.domain.R;
import com.imarket.common.exception.BadRequestException;
import com.imarket.common.exception.BizIllegalException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = Util.toString(response.body().asReader(Util.UTF_8));
            R<?> r = objectMapper.readValue(body, R.class);
            log.error("Feign调用异常, methodKey={}, code={}, msg={}", methodKey, r.getCode(), r.getMsg());
            if (r.getCode() == 400) {
                return new BadRequestException(r.getMsg());
            }
            return new BizIllegalException(r.getMsg());
        } catch (IOException e) {
            log.error("Feign错误解码失败, methodKey={}", methodKey, e);
            return new BizIllegalException("远程服务调用异常");
        }
    }
}
