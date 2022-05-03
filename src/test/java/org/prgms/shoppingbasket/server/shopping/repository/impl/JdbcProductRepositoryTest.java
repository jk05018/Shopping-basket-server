package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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

	@DisplayName("productRepository 자동 주입 테스트")
	@Test
	void productRepository_autowired_test() {
		assertThat(productRepository).isNotNull();
	}

	@DisplayName("product save Test")
	@Test
	void product_save_pass_test() {
		// given
		final Product yes_description_product = new Product("snack", 10000, 20, "this is snack");
		// when
		final Product savedProduct = productRepository.save(yes_description_product);
		final Optional<Product> findProduct = productRepository.findById(savedProduct.getProductId());
		// then
		assertThat(findProduct).isNotNull();
		assertThat(findProduct.get().getProductId()).isEqualTo(yes_description_product.getProductId());
		assertThat(findProduct.get().getPrice()).isEqualTo(yes_description_product.getPrice());

		// given
		final Product no_description_product = new Product("snack", 10000, 20, null);
		// when
		final Product savedProduct2 = productRepository.save(no_description_product);
		final Optional<Product> findProduct2 = productRepository.findById(savedProduct2.getProductId());
		// then
		assertThat(findProduct2).isNotNull();
		assertThat(findProduct2.get().getProductId()).isEqualTo(no_description_product.getProductId());
		assertThat(findProduct2.get().getPrice()).isEqualTo(no_description_product.getPrice());
	}

	@DisplayName("findAll Test")
	@Test
	void findAll_Test() {
		// given
		final Product product1 = new Product("snack", 10000, 20, "this is snack");
		final Product product2 = new Product("snack", 10000, 20, null);

		final Product savedProduct1 = productRepository.save(product1);
		final Product savedProduct2 = productRepository.save(product2);

		//when
		final List<Product> products = productRepository.findAll();

		//then
		assertThat(products.size()).isEqualTo(2);
		assertThat(products).contains(product1, product2);
	}

	@DisplayName("update test")
	@Test
	void update_test() {
		// given
		final Product product = new Product("snack", 10000, 20, "this is snack");
		final Product savedProduct = productRepository.save(product);
		assertThat(savedProduct.getPrice()).isEqualTo(10000);
		assertThat(savedProduct.getProductName()).isEqualTo("snack");
		//when
		savedProduct.updatePrice(20000);
		savedProduct.updateProductName("updatedSanck");
		savedProduct.updateRemainQuantity(savedProduct.getRemainQuantity() - 10);

		productRepository.update(savedProduct);
		//then

		final Optional<Product> updatedProduct = productRepository.findById(savedProduct.getProductId());

		assertThat(updatedProduct).isNotNull();
		assertThat(updatedProduct.get().getPrice()).isEqualTo(savedProduct.getPrice());
		assertThat(updatedProduct.get().getProductName()).isEqualTo(savedProduct.getProductName());
		assertThat(updatedProduct.get().getRemainQuantity()).isEqualTo(savedProduct.getRemainQuantity());

	}
}
