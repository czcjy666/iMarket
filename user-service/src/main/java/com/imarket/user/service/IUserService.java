package com.imarket.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imarket.user.domain.dto.LoginFormDTO;
import com.imarket.user.domain.po.User;
import com.imarket.user.domain.vo.UserLoginVO;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @since 2023-05-05
 */
public interface IUserService extends IService<User> {

    UserLoginVO login(LoginFormDTO loginFormDTO);

    void deductMoney(String pw, Integer totalFee);
}
