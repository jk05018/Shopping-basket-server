package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcOrderRepositoryTest {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	ProductRepository productRepository;

	@AfterEach
	void afterEach() {
		orderRepository.deleteAll();
		voucherRepository.deleteAll();
		productRepository.deleteAll();
	}

	@DisplayName("productRepository 자동 주입 테스트")
	@Test
	void productRepository_autowired_test() {
		assertThat(orderRepository).isNotNull();
	}

	@DisplayName("product save Test")
	@Test
	void product_save_pass_test() {
		// given
		final Product product1 = productRepository.save(new Product("product1", 10000, 20, "product1 입니다!"));
		final Product product2 = productRepository.save(new Product("product2", 20000, 10, "product2 입니다!"));

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem(product1.getProductId(), product1.getPrice(), 10));
		orderItems.add(new OrderItem(product2.getProductId(), product2.getPrice(), 5));

		final Voucher voucher = voucherRepository.save(VoucherType.FIXED_AMOUNT_VOUCHER.create(1000, "1000원 할인입니다."));
		final Order order = new Order(voucher.getVoucherId(), "jan@naver.com", "seoul gangnam", "12345",
			orderItems);

		// when
		final Order savedOrder = orderRepository.save(order);
		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		// then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(findOrder.get().getOrderItems().size()).isEqualTo(2);

		// update test
		// given
		final Order updateOrder = findOrder.get();
		updateOrder.updateEmail("updatEmail@naver.com");
		updateOrder.updateAddress("update_address");

		// when
		orderRepository.update(updateOrder);
		final Optional<Order> findUpdatedOrder = orderRepository.findById(updateOrder.getOrderId());

		//then
		assertThat(findUpdatedOrder.get().getAddress()).isEqualTo(updateOrder.getAddress());
		assertThat(findUpdatedOrder.get().getEmail()).isEqualTo(updateOrder.getEmail());
	}
}
