package org.prgms.shoppingbasket.server.shopping.service.impl;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.repository.OrderItemRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Override
	public Order createOrder(UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems) {
		final Order order = Order.create(voucherId, email, address, postcode, orderItems);
		orderRepository.save(order);
		order.getOrderItems().forEach(oi -> orderItemRepository.save(order.getOrderId(), oi));

		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}
