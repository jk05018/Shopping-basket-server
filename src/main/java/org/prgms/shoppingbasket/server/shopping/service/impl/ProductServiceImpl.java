package org.prgms.shoppingbasket.server.shopping.service.impl;

import java.util.List;

import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public Product createProduct(String name, int price, int remainQuantity, String description) {
		final Product product = Product.create(name, price, remainQuantity, description);
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
}
