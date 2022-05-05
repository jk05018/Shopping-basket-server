package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.repository.OrderItemRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JdbcOrderRepositoryTest {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderItemRepository orderItemRepository;

	@DisplayName("product 생성 테스트, update 테스트 ")
	@Test
	void product_save_pass_test() {
		// given
		final Product product1 = productRepository.save(Product.create("product1", 10000, 20, "product1 입니다!"));
		final Product product2 = productRepository.save(Product.create("product2", 20000, 10, "product2 입니다!"));

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(OrderItem.create(product1.getId(), product1.getPrice(), 10));
		orderItems.add(OrderItem.create(product2.getId(), product2.getPrice(), 5));

		final Voucher voucher = voucherRepository.save(VoucherType.FIXED_AMOUNT_VOUCHER.create(1000, "1000원 할인입니다."));
		final Order order = Order.create(voucher.getVoucherId(), "jan@naver.com", "seoul gangnam", "12345",
			orderItems);

		// when
		final Order savedOrder = orderRepository.save(order);
		order.getOrderItems().forEach(i -> orderItemRepository.save(order.getOrderId(), i));

		final Order findOrder = orderRepository.findById(savedOrder.getOrderId()).orElseThrow();

		// then
		assertThat(findOrder.getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(findOrder.getEmail()).isEqualTo("jan@naver.com");
		assertThat(findOrder.getAddress()).isEqualTo("seoul gangnam");
		assertThat(findOrder.getPostcode()).isEqualTo("12345");
		assertThat(findOrder.getOrderItems().size()).isEqualTo(2);

		// update test
		// given
		findOrder.updateDeliveryDestination("update@email.com", "pohang", "1324");

		// when
		orderRepository.update(findOrder);
		final Optional<Order> findUpdatedOrder = orderRepository.findById(findOrder.getOrderId());

		//then
		assertThat(findUpdatedOrder.get().getEmail()).isEqualTo("update@email.com");
		assertThat(findUpdatedOrder.get().getAddress()).isEqualTo("pohang");
		assertThat(findUpdatedOrder.get().getPostcode()).isEqualTo("1324");
	}

	@DisplayName("바우처 등록하지 않는 Order 생성 테스트")
	@Test
	void order_save_pass_test_without_voucher() {
		// given
		final Product product1 = productRepository.save(Product.create("product1", 10000, 20, "product1 입니다!"));
		final Product product2 = productRepository.save(Product.create("product2", 20000, 10, "product2 입니다!"));

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(OrderItem.create(product1.getId(), product1.getPrice(), 10));
		orderItems.add(OrderItem.create(product2.getId(), product2.getPrice(), 5));

		final Order order = Order.create(null, "jan@naver.com", "seoul gangnam", "12345",
			orderItems);

		// when
		final Order savedOrder = orderRepository.save(order);
		order.getOrderItems().forEach(i -> orderItemRepository.save(order.getOrderId(), i));
		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		// then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isNull();
		assertThat(findOrder.get().getOrderItems().size()).isEqualTo(2);

	}
}
