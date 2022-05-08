package org.prgms.shoppingbasket.server.shopping.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Order;

public interface OrderRepository {

	Order save(Order order);

	Order update(Order order);

	Optional<Order> findById(UUID orderId);

	List<Order> findAll();

	void deleteOrder(UUID orderId);

}
