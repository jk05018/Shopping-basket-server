package org.prgms.shoppingbasket.domain.product.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

	@DisplayName("Product 생성 태스트")
	@Test
	void product_create_test() {
		// when
		final Product snack = new Product("snack", 10000, 20, "나는 과자다");
		// then
		assertThat(snack.getProductId()).isNotNull();
		assertThat(snack.getPrice()).isEqualTo(10000);

		//given
		final UUID uuid = UUID.randomUUID();
		//when
		final Product new_snack = new Product(uuid, "snack2", 20000, 30, "나는 과자다2");
		//then
		assertThat(new_snack.getProductId()).isEqualTo(uuid);
	}

	@DisplayName("price와 remainQunatity는 음수이면 안된다")
	@Test
	void price_remianQuantity_not_minus_test() {
		assertThrows(IllegalArgumentException.class, () -> new Product("snack", -1000, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> new Product("snack", 200, -10, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> new Product("snack", -200, -10, "나는 과자다"));
	}

	@DisplayName("productName과 description은 정해진 글자 수를 따라야 한다.")
	@Test
	void productName_description_length_test() {
		assertThrows(IllegalArgumentException.class, () -> new Product("", 10000, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> new Product("snack", 10000, 20, ""));
		assertThrows(IllegalArgumentException.class, () -> new Product("snackkkkkkkkkkkkkkkkkkkkkkkkkk", 10000, 20, "I'm snack"));
		assertThrows(IllegalArgumentException.class, () -> new Product(null, 10000, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> new Product("snack", 10000, 20, null));
	}

}
