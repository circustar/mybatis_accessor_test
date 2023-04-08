package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.ProductOrderDetail;
import com.test.mybatis_accessor.mapper.ProductOrderDetailMapper;
import com.test.mybatis_accessor.service.IProductOrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderDetailServiceImpl extends ServiceImpl<ProductOrderDetailMapper, ProductOrderDetail> implements IProductOrderDetailService {
}
