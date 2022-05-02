package org.prgms.shoppingbasket.server.shopping.service.impl;

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
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceImplTest {

	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	VoucherRepository voucherRepository;

	@AfterEach
	void afterEach() {
		orderRepository.deleteAll();
		voucherRepository.deleteAll();
		productRepository.deleteAll();
	}

	@DisplayName("order service create test Voucher 있을 때")
	@Test
	void orderService_create_test_with_voucher() {
		// given
		final Voucher voucher = voucherService.createVoucher("FIXED_AMOUNT_VOUCHER", 10000,
			"10000원 할인입니다!");

		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem(product1.getProductId(), product1.getPrice(), 10));
		orderItems.add(new OrderItem(product2.getProductId(), product2.getPrice(), 10));

		final Order savedOrder = orderService.createOrder(voucher.getVoucherId(), "han@email.com", "gangnam", "12345",
			orderItems);

		//when
		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		//then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
		assertThat(findOrder.get().getEmail()).isEqualTo("han@email.com");
		assertThat(findOrder.get().getOrderItems()).map(i -> i.getProductId())
			.contains(product1.getProductId(), product2.getProductId());

	}

	@DisplayName("order service create test Voucher 없을 때")
	@Test
	void orderService_create_test_without_voucher() {
		// given
		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem(product1.getProductId(), product1.getPrice(), 10));
		orderItems.add(new OrderItem(product2.getProductId(), product2.getPrice(), 10));

		final Order savedOrder = orderService.createOrder(null, "han@email.com", "gangnam", "12345",
			orderItems);

		//when
		final Optional<Order> findOrder = orderRepository.findById(savedOrder.getOrderId());

		//then
		assertThat(findOrder).isNotNull();
		assertThat(findOrder.get().getVoucherId()).isNull();
		assertThat(findOrder.get().getEmail()).isEqualTo("han@email.com");
		assertThat(findOrder.get().getOrderItems()).map(i -> i.getProductId())
			.contains(product1.getProductId(), product2.getProductId());

	}

}
