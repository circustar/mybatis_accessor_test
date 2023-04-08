package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.ProductOrder;
import com.test.mybatis_accessor.mapper.ProductOrderMapper;
import com.test.mybatis_accessor.service.IProductOrderService;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements IProductOrderService {
}
