package org.prgms.shoppingbasket.server.shopping.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

	@DisplayName("Order 생성 테스트")
	@Test
	void create_order_test() {
		// given
		final VoucherType fixedAmountVoucher = VoucherType.FIXED_AMOUNT_VOUCHER;
		final Voucher voucher = fixedAmountVoucher.create(1000, "1000원 할인입니다!");
		String email = "jk05018@naver.com";

		// when
		final Order order = Order.create(voucher.getVoucherId(), email, "서울시 강남구", "12314", Collections.EMPTY_LIST);
		// then

		assertThat(order.getOrderId()).isNotNull();
		assertThat(order.getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(order.getEmail()).isEqualTo(email);

		//given
		final Product product = Product.create("snack", 1000, 10, "my snack");

		//when
		final OrderItem orderItem = OrderItem.create(product.getId(), product.getPrice(), 10);

		//then
		assertThat(orderItem.getProductId()).isEqualTo(product.getId());
		assertThat(orderItem.getQuantity()).isEqualTo(10);
	}

}
