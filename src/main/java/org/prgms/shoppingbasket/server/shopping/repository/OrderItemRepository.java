package org.prgms.shoppingbasket.server.shopping.repository;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

public interface OrderItemRepository {
	OrderItem save(UUID orderId, OrderItem orderItem);

	List<OrderItem> findOrderItemsByOrderId(UUID orderId);
}
