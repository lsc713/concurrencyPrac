package com.service.concurrencyprac.payment.service.product;

import com.service.concurrencyprac.payment.entity.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Product getProductById(Long productId);

    public List<Product> getProductsByIds(List<Long> productsIds);

    void decreaseStockQuantity(Map<Product, Integer> productQuantityMap);

    void increaseStockQuantity(Map<Product, Integer> productQuantityMap);
}
