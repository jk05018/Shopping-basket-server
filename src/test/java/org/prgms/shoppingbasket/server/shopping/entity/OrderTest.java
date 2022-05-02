package org.prgms.shoppingbasket.server.shopping.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

	@DisplayName("createOrder, orderItem test")
	@Test
	void create_order_test() {
		// given
		final VoucherType fixedAmountVoucher = VoucherType.FIXED_AMOUNT_VOUCHER;
		final Voucher voucher = fixedAmountVoucher.create(1000, "1000원 할인입니다!");
		String email = "jk05018@naver.com";

		// when
		final Order order = new Order(voucher.getVoucherId(), email, "서울시 강남구", "12314", Collections.EMPTY_LIST);
		// then

		assertThat(order.getOrderId()).isNotNull();
		assertThat(order.getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(order.getEmail()).isEqualTo(email);

		//given
		final Product product = new Product("snack", 1000, 10, "my snack");

		//when
		final OrderItem orderItem = new OrderItem( product.getProductId(), product.getPrice(), 10);

		//then
		assertThat(orderItem.getProductId()).isEqualTo(product.getProductId());
		assertThat(orderItem.getQuantity()).isEqualTo(10);
	}

	@DisplayName("나중에 서비스에서 할때 OrderItem의 주문량이 product의 remain보다 작을 때 예외를 내도록 처리하자 Controller 단에서 처리할 수 있음 더 좋고")
	@Test
	void test() {
		// given

		//when

		//then

	}

}