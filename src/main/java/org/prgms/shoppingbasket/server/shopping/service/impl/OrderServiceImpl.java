package org.prgms.shoppingbasket.server.shopping.service.impl;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public Order createOrder(UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems) {
		final Order order = new Order(voucherId, email, address, postcode, orderItems);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}
