package org.prgms.shoppingbasket.server.shopping.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

	@DisplayName("Product 생성 태스트")
	@Test
	void product_create_test() {
		// when
		final Product snack = Product.create("snack", 10000, 20, "나는 과자다");
		// then
		assertThat(snack.getId()).isNotNull();
		assertThat(snack.getName()).isEqualTo("snack");
		assertThat(snack.getPrice()).isEqualTo(10000);
		assertThat(snack.getRemainQuantity()).isEqualTo(20);
		assertThat(snack.getDescription()).isEqualTo("나는 과자다");

		//given
		final UUID uuid = UUID.randomUUID();
		//when
		final Product new_snack = Product.bind(uuid, "snack2", 20000, 30, "나는 과자다2", LocalDateTime.now(),
			LocalDateTime.now());
		//then
		assertThat(new_snack.getId()).isEqualTo(uuid);
		assertThat(new_snack.getName()).isEqualTo("snack2");
		assertThat(new_snack.getPrice()).isEqualTo(20000);
		assertThat(new_snack.getRemainQuantity()).isEqualTo(30);
		assertThat(new_snack.getDescription()).isEqualTo("나는 과자다2");
	}

	@DisplayName("price는 음수이면 안된다")
	@Test
	void price_not_minus_test() {
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", -1, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", -20, 10, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", -1000000, 100, "나는 과자다"));
	}

	@DisplayName("remainQunatity는 음수이면 안된다")
	@Test
	void price_remianQuantity_not_minus_test() {
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", 1000, -1, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", 200, -100, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> Product.create("snack", 200, -10000000, "나는 과자다"));
	}

	@DisplayName("productName은 NULL과 공백이 아니어야 하고 1자이상 20자 미만이어야 한다.")
	@Test
	void productName_description_length_test() {
		assertThrows(IllegalArgumentException.class, () -> Product.create("", 10000, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class, () -> Product.create(null, 10000, 20, "나는 과자다"));
		assertThrows(IllegalArgumentException.class,
			() -> Product.create("snackkkkkkkkkkkkkkkkkkkkkkkkkkㅣㅣㅣ", 10000, 20, "I'm snack"));
	}

}
