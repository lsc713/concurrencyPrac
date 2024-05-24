package com.service.concurrencyprac.payment.service.impl;

import com.service.concurrencyprac.common.exception.InvalidParamException;
import com.service.concurrencyprac.payment.entity.Product;
import com.service.concurrencyprac.payment.repository.coupon.ProductRepository;
import com.service.concurrencyprac.payment.service.product.ProductService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long productId) {
        return productRepository
            .findById(productId)
            .orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<Product> getProductsByIds(List<Long> productsIds) {
        return productRepository.findAllById(productsIds);
    }

    @Override
    public void decreaseStockQuantity(Map<Product, Integer> productQuantityMap) {
        List<Product> result = productQuantityMap.entrySet().stream().map(entry -> {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            try {
                product.decreaseStock(quantity);
                return product;
            } catch (Exception e) {
                throw new RuntimeException("주문을 위해 상품재고 업데이트를 하는데 실패함" + product.getName(), e);
            }
        }).collect(Collectors.toList());
        productRepository.saveAll(result);
    }

    @Override
    public void increaseStockQuantity(Map<Product, Integer> productQuantityMap) {
        List<Product> result = productQuantityMap.entrySet().stream().map(entry -> {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            try {
                product.increaseStock(quantity);
                return product;
            } catch (Exception e) {
                throw new RuntimeException("주문을 위해 상품재고 업데이트를 하는데 실패함" + product.getName(), e);
            }
        }).collect(Collectors.toList());
        productRepository.saveAll(result);
    }
}
