package org.prgms.shoppingbasket.domain.order.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

class OrderTest {

	@DisplayName("createOrder, orderItem test")
	@Test
	void create_order_test() {
		// given
		final VoucherType fixedAmountVoucher = VoucherType.FIXED_AMOUNT_VOUCHER;
		final Voucher voucher = fixedAmountVoucher.create(1000);
		String email = "jk05018@naver.com";

		// when
		final Order order = new Order(voucher.getVoucherId(), email
			, "서울시 강남구", "12314", Collections.EMPTY_LIST, 10000);
		// then

		assertThat(order.getOrderId()).isNotNull();
		assertThat(order.getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(order.getEmail()).isEqualTo(email);
		assertThat(order.getTotalPrice()).isEqualTo(10000);

		//given
		final Product product = new Product("snack", 1000, 10, "my snack");

		//when
		final OrderItem orderItem = new OrderItem(order.getOrderId(), product.getProductId(), product.getPrice(), 10);

		//then
		assertThat(orderItem.getOrderId()).isNotNull();
		assertThat(orderItem.getProductId()).isEqualTo(product.getProductId());
	}

	@DisplayName("나중에 서비스에서 할때 OrderItem의 주문량이 product의 remain보다 작을 때 예외를 내도록 처리하자 Controller 단에서 처리할 수 있음 더 좋고")
	@Test
	void test(){
		// given

		//when

		//then

	}



}
