package org.prgms.shoppingbasket.server.shopping.service;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

public interface OrderService {

	Order createOrder(UUID voucherId, String email, String address, String postcode, List<OrderItem> orderItems);

	List<Order> getAllOrders();

	Order updateDeliveryDestination(UUID orderId, String email, String address, String postcode);
}
