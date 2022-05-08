package org.prgms.shoppingbasket.server.shopping.service.impl;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.OrderItemRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;

	@Override
	public Order createOrder(UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems) {
		final Order order = Order.create(voucherId, email, address, postcode, orderItems);
		orderRepository.save(order);
		order.getOrderItems().forEach(oi -> {
			orderItemRepository.save(order.getOrderId(), oi);
			productRepository.update(updateProductRemainQuantity(oi));
		});
		return order;
	}

	private Product updateProductRemainQuantity(OrderItem orderItem) {
		final Product updateProduct = productRepository.findById(orderItem.getProductId())
			.orElseThrow(
				() -> new IllegalStateException("해당하는 상품이 존재하지 않습니다. productId = " + orderItem.getProductId()));

		updateProduct.decreaseRemainQuantity(orderItem.getQuantity());

		return updateProduct;

	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order updateDeliveryDestination(UUID orderId, String email, String address, String postcode) {
		final Order toUpdateOrder = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다. orderId = " + orderId));

		toUpdateOrder.updateDeliveryDestination(email, address, postcode);

		return orderRepository.update(toUpdateOrder);
	}

	@Override
	public void deleteOrder(UUID orderId) {
		orderRepository.deleteOrder(orderId);
	}

}
