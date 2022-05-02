package org.prgms.shoppingbasket.server.shopping.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceImplTest {

	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository productRepository;

	@AfterEach
	void afterEach() {
		productRepository.deleteAll();
	}

	@DisplayName("productService createProduct testd")
	@Test
	void productservice_create_test() {
		// given
		final Product savedProduct = productService.createProduct("product1", 10000, 30, "product1");

		//when
		final Optional<Product> findProduct = productRepository.findById(savedProduct.getProductId());

		//then
		assertThat(findProduct).isNotNull();
		assertThat(findProduct.get().getProductId()).isEqualTo(savedProduct.getProductId());
		assertThat(findProduct.get().getProductName()).isEqualTo(savedProduct.getProductName());

	}

	@DisplayName("productService findAll test")
	@Test
	void productService_findAll_test() {
		// given
		final Product savedProduct1 = productService.createProduct("product1", 10000, 30, "product1");
		final Product savedProduct2 = productService.createProduct("product2", 20000, 20, "product2");

		//when
		final List<Product> products = productService.getAllProducts();

		//then
		assertThat(products.size()).isEqualTo(2);
		assertThat(products).map(p -> p.getProductId())
			.contains(savedProduct1.getProductId(), savedProduct2.getProductId());

	}

}
