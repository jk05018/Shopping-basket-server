package org.prgms.shoppingbasket.server.shopping.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository productRepository;

	@DisplayName("productService createProduct testd")
	@Test
	void productservice_create_test() {
		// given
		final Product savedProduct = productService.createProduct("product1", 10000, 30, "product1");

		//when
		final Optional<Product> findProduct = productRepository.findById(savedProduct.getId());

		//then
		assertThat(findProduct).isNotNull();
		assertThat(findProduct.get().getId()).isEqualTo(savedProduct.getId());
		assertThat(findProduct.get().getName()).isEqualTo(savedProduct.getName());

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
		assertThat(products).map(p -> p.getId())
			.contains(savedProduct1.getId(), savedProduct2.getId());

	}

}
