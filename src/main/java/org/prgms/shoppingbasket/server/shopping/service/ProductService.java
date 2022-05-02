package org.prgms.shoppingbasket.server.shopping.service;

import java.util.List;

import org.prgms.shoppingbasket.server.shopping.entity.Product;

public interface ProductService {

	Product createProduct(String productName, int price, int remainQuantity, String description);

	List<Product> getAllProducts();
}
