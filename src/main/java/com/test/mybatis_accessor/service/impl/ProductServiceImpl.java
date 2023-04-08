package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.Product;
import com.test.mybatis_accessor.mapper.ProductMapper;
import com.test.mybatis_accessor.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
