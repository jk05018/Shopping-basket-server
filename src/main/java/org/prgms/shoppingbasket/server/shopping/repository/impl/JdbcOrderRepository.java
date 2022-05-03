package org.prgms.shoppingbasket.server.shopping.repository.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgms.shoppingbasket.server.common.exception.DatabaseException;
import org.prgms.shoppingbasket.server.common.utils.LocalDateTimeUtil;
import org.prgms.shoppingbasket.server.common.utils.UUIDConverter;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Order save(Order order) {
		final String ORDER_SAVE_SQL =
			"insert into orders (order_id, voucher_id, email, address, postcode, created_at, updated_at)"
				+ " values (:orderId, :voucherId, :email, :address, :postcode, :createdAt, :updatedAt)";
		final int update = jdbcTemplate.update(ORDER_SAVE_SQL, orderToParamMap(order));

		if (update != 1) {
			throw new DatabaseException("order가 save되지 않았습니다.");
		}

		order.getOrderItems()
			.forEach(item -> {
				jdbcTemplate.update(
					"INSERT INTO order_items(order_id, product_id, price, quantity) " +
						"values (:orderId, :productId, :price, :quantity) ",
					orderItemToParamMap(order.getOrderId(), item));
			});

		return order;
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		final String ORDER_FIND_BY_ID_SQL = "select * from orders where order_id = :orderId";

		try {
			return Optional.of(jdbcTemplate.queryForObject(ORDER_FIND_BY_ID_SQL,
				Collections.singletonMap("orderId", UUIDConverter.uuidToBytes(orderId)), toOrderMapper()));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Order> findAll() {
		final String ORDER_FIND_ALL_SQL = "select * from orders";
		return jdbcTemplate.query(ORDER_FIND_ALL_SQL, toOrderMapper());
	}

	@Override
	public Order update(Order order) {
		final String ORDER_UPDATE_SQL =
			"update orders set voucher_id = :voucherId , email = :email, address = :address,"
				+ " postcode = :postcode, created_at = :createdAt , updated_at = :updatedAt"
				+ " where order_id = :orderId";

		final int update = jdbcTemplate.update(ORDER_UPDATE_SQL, orderToParamMap(order));

		if (update != 1) {
			throw new DatabaseException("order가 update되지 않았습니다.");
		}

		return order;
	}

	@Override
	public void deleteAll() {
		final String DELETE_SQL = "delete from orders";

		jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());

	}

	private List<OrderItem> findOrderItemsByOrderId(UUID orderId) {
		final String ORDER_ITEM_FIND_ALL_SQL = "select * from order_items where order_id = :orderId";
		return jdbcTemplate.query(ORDER_ITEM_FIND_ALL_SQL,
			Collections.singletonMap("orderId", UUIDConverter.uuidToBytes(orderId)), toOrderItemMapper());
	}

	private Map<String, Object> orderToParamMap(Order order) {
		final HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", UUIDConverter.uuidToBytes(order.getOrderId()));
		paramMap.put("voucherId", UUIDConverter.uuidToBytes(order.getVoucherId()));
		paramMap.put("email", order.getEmail());
		paramMap.put("address", order.getAddress());
		paramMap.put("postcode", order.getPostcode());
		paramMap.put("createdAt", order.getCreatedAt());
		paramMap.put("updatedAt", order.getUpdatedAt());

		return paramMap;
	}

	private Map<String, Object> orderItemToParamMap(UUID orderId, OrderItem orderItem) {
		final HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", UUIDConverter.uuidToBytes(orderId));
		paramMap.put("productId", UUIDConverter.uuidToBytes(orderItem.getProductId()));
		paramMap.put("price", orderItem.getPrice());
		paramMap.put("quantity", orderItem.getQuantity());

		return paramMap;
	}

	private RowMapper<Order> toOrderMapper() {
		return (rs, rowNum) -> {
			final UUID orderId = UUIDConverter.bytesToUUID(rs.getBytes("order_id"));
			final UUID voucherId = UUIDConverter.bytesToUUID(rs.getBytes("voucher_id"));
			final String email = rs.getString("email");
			final String address = rs.getString("address");
			final String postcode = rs.getString("postcode");
			final LocalDateTime createdAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("created_at"));
			final LocalDateTime updatedAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("updated_at"));

			final List<OrderItem> orderItems = findOrderItemsByOrderId(orderId);

			return new Order(orderId, voucherId, email, address, postcode, orderItems, createdAt, updatedAt);

		};
	}

	private RowMapper<OrderItem> toOrderItemMapper() {
		return (rs, rowNum) -> {
			final UUID productId = UUIDConverter.bytesToUUID(rs.getBytes("product_id"));
			final int price = rs.getInt("price");
			final int quantity = rs.getInt("quantity");

			return new OrderItem(productId, price, quantity);

		};
	}
}
