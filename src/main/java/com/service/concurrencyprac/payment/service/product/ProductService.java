package com.service.concurrencyprac.payment.service.product;

import com.service.concurrencyprac.payment.entity.Product;
import java.util.List;

public interface ProductService {

    public List<Product> getProductsByIds(List<Long> productsIds);

}
