package com.imarket.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.imarket.user.domain.po.Address;
import com.imarket.user.mapper.AddressMapper;
import com.imarket.user.service.IAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2023-05-05
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
