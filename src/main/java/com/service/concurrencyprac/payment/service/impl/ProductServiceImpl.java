package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.repository.coupon.ProductRepository;
import com.service.concurrencyprac.payment.service.product.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsByIds(List<Long> productsIds) {
        return productRepository.findAllById(productsIds);
    }
}
