package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JdbcProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;

	@DisplayName("product 생성 테스트")
	@Test
	void product_save_pass_test() {
		// given
		final Product yes_description_product = Product.create("snack", 10000, 20, "this is snack");
		// when
		final Product savedProduct = productRepository.save(yes_description_product);
		final Product findProduct = productRepository.findById(yes_description_product.getId()).orElseThrow();
		// then
		assertThat(findProduct.getId()).isEqualTo(yes_description_product.getId());
		assertThat(findProduct.getName()).isEqualTo("snack");
		assertThat(findProduct.getPrice()).isEqualTo(10000);
		assertThat(findProduct.getRemainQuantity()).isEqualTo(20);
		assertThat(findProduct.getDescription()).isEqualTo("this is snack");

		// given
		final Product no_description_product = Product.create("snack", 10000, 20, null);
		// when
		final Product savedProduct2 = productRepository.save(no_description_product);
		final Product findProduct2 = productRepository.findById(no_description_product.getId()).orElseThrow();
		// then
		assertThat(findProduct2.getId()).isEqualTo(no_description_product.getId());
		assertThat(findProduct2.getName()).isEqualTo("snack");
		assertThat(findProduct2.getPrice()).isEqualTo(10000);
		assertThat(findProduct2.getRemainQuantity()).isEqualTo(20);
		assertThat(findProduct2.getDescription()).isNull();
	}

	@DisplayName("findAll Test")
	@Test
	void findAll_Test() {
		// given
		final Product product1 = Product.create("snack", 10000, 20, "this is snack");
		final Product product2 = Product.create("snack", 10000, 20, null);

		final Product savedProduct1 = productRepository.save(product1);
		final Product savedProduct2 = productRepository.save(product2);

		//when
		final List<Product> products = productRepository.findAll();

		//then
		assertThat(products).contains(product1, product2);
	}

	@DisplayName("product 재고 감소 test")
	@Test
	void update_test() {
		// given
		final Product product = Product.create("snack", 10000, 20, "this is snack");
		final Product savedProduct = productRepository.save(product);

		savedProduct.decreaseRemainQuantity(10);

		//when
		productRepository.update(savedProduct);

		final Product updatedProduct = productRepository.findById(savedProduct.getId()).orElseThrow();

		//then
		assertThat(updatedProduct.getRemainQuantity()).isEqualTo(10);

	}
}
