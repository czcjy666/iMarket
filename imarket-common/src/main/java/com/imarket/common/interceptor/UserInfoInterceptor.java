package com.imarket.common.interceptor;

import com.imarket.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //1.获取请求头的用户信息
        String userId = request.getHeader("user-info");
        //2.如果用户信息不为空，则将用户信息保存到UserContext的ThreadLocal中
        if (userId != null && !userId.isBlank()){
            UserContext.setUser(Long.valueOf(userId));
        }
        //3.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.removeUser();
    }
}
