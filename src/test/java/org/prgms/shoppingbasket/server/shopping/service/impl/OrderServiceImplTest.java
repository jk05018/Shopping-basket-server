package org.prgms.shoppingbasket.server.shopping.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceImplTest {

	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	VoucherService voucherService;
	@Autowired
	OrderRepository orderRepository;

	@DisplayName("Voucher를 적용한 Order는 생성할 수 있어야 한다")
	@Test
	void orderService_create_test_with_voucher() {
		// given
		final Voucher voucher = voucherService.createVoucher("FIXED_AMOUNT_VOUCHER", 10000,
			"10000원 할인입니다!");

		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(OrderItem.create(product1.getId(), product1.getPrice(), 10));
		orderItems.add(OrderItem.create(product2.getId(), product2.getPrice(), 10));

		final Order savedOrder = orderService.createOrder(voucher.getVoucherId(), "han@email.com", "gangnam", "12345",
			orderItems);

		//when
		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		//then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(findOrder.get().getEmail()).isEqualTo("han@email.com");
		assertThat(findOrder.get().getOrderItems()).map(i -> i.getProductId())
			.contains(product1.getId(), product2.getId());

	}

	@DisplayName("Voucher를 적용하지 않은 Order는 생성할 수 있어야 한다")
	@Test
	void orderService_create_test_without_voucher() {
		// given
		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = List.of(
			OrderItem.create(product1.getId(), product1.getPrice(), 10),
			OrderItem.create(product2.getId(), product2.getPrice(), 10));

		//when
		final Order savedOrder = orderService.createOrder(null, "han@email.com", "gangnam", "12345",
			orderItems);

		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		//then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isNull();
		assertThat(findOrder.get().getEmail()).isEqualTo("han@email.com");
		assertThat(findOrder.get().getOrderItems()).map(i -> i.getProductId())
			.contains(product1.getId(), product2.getId());

	}

	@DisplayName("주문을 하면 각 주문 수량만큼 재고가 줄어들어야 한다.")
	@Test
	void decrease_remainQuantity_after_order() {
		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = List.of(
			OrderItem.create(product1.getId(), product1.getPrice(), 10),
			OrderItem.create(product2.getId(), product2.getPrice(), 10));

		//when
		final Order savedOrder = orderService.createOrder(null, "han@email.com", "gangnam", "12345",
			orderItems);

		final Product findProduct1 = productRepository.findById(product1.getId()).orElseThrow();
		final Product findProduct2 = productRepository.findById(product2.getId()).orElseThrow();

		//then
		assertThat(findProduct1.getRemainQuantity()).isEqualTo(10);
		assertThat(findProduct2.getRemainQuantity()).isEqualTo(20);
	}

	@DisplayName("주문시 상품 주문 양이 상품의 재고보다 많다면 IllegalStateException 에러를 내야 한다.")
	@Test
	void order_quantity_must_less_than_remainQuantity() {
		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");

		List<OrderItem> orderItems = List.of(OrderItem.create(product1.getId(), product1.getPrice(), 21));

		//when
		assertThatThrownBy(() -> orderService.createOrder(null, "han@email.com", "gangnam", "12345",
			orderItems)).isInstanceOf(IllegalStateException.class);

	}

	@DisplayName("Order의 배송지를 변경할 수 있어야 한다.")
	@Test
	void change_delivery_destination_test() {
		// given
		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");

		final Order savedOrder = orderService.createOrder(null, "han@email.com", "gangnam", "12345",
			List.of(OrderItem.create(product1.getId(), product1.getPrice(), 10)));

		//when
		orderService.updateDeliveryDestination(savedOrder.getOrderId(), "updated@email.com", "updateGangnam", "54321");

		//then
		final Order updatedOrder = orderRepository.findById(savedOrder.getOrderId()).orElseThrow();

		assertThat(updatedOrder.getEmail()).isEqualTo("updated@email.com");
		assertThat(updatedOrder.getAddress()).isEqualTo("updateGangnam");
		assertThat(updatedOrder.getPostcode()).isEqualTo("54321");

	}

}
