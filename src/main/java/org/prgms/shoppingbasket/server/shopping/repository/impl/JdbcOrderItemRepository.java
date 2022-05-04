package org.prgms.shoppingbasket.server.shopping.repository.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.shoppingbasket.server.common.utils.UUIDConverter;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.repository.JdbcRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderItemRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcOrderItemRepository implements OrderItemRepository, JdbcRepository<OrderItem> {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public OrderItem save(UUID orderId, OrderItem orderItem) {
		final String ORDER_ITEM_SAVE_SQL = "INSERT INTO order_items(order_id, product_id, price, quantity) " +
			"values (:orderId, :productId, :price, :quantity) ";

		jdbcTemplate.update(ORDER_ITEM_SAVE_SQL, orderItemToParamMap(orderId, orderItem));
		return orderItem;
	}

	public List<OrderItem> findOrderItemsByOrderId(UUID orderId) {
		final String ORDER_ITEM_FIND_ALL_SQL = "select * from order_items where order_id = :orderId";
		return jdbcTemplate.query(ORDER_ITEM_FIND_ALL_SQL,
			Collections.singletonMap("orderId", UUIDConverter.uuidToBytes(orderId)), toMapper());
	}

	private Map<String, Object> orderItemToParamMap(UUID orderId, OrderItem orderItem) {
		return Map.of(
			"orderId", UUIDConverter.uuidToBytes(orderId),
			"productId", UUIDConverter.uuidToBytes(orderItem.getProductId()),
			"price", orderItem.getPrice(),
			"quantity", orderItem.getQuantity()
		);
	}

	@Override
	public RowMapper<OrderItem> toMapper() {
		return (rs, rowNum) -> {
			final UUID productId = UUIDConverter.bytesToUUID(rs.getBytes("product_id"));
			final int price = rs.getInt("price");
			final int quantity = rs.getInt("quantity");

			return OrderItem.bind(productId, price, quantity);

		};
	}
}
