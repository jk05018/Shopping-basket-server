package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static com.google.common.base.Preconditions.*;

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
import org.prgms.shoppingbasket.server.shopping.repository.JdbcRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderItemRepository;
import org.prgms.shoppingbasket.server.shopping.repository.OrderRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository, JdbcRepository<Order> {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final OrderItemRepository orderItemRepository;

	@Override
	public Order save(Order order) {
		final String ORDER_SAVE_SQL =
			"insert into orders (order_id, voucher_id, email, address, postcode, created_at, updated_at)"
				+ " values (:orderId, :voucherId, :email, :address, :postcode, :createdAt, :updatedAt)";
		final int update = jdbcTemplate.update(ORDER_SAVE_SQL, orderToParamMap(order));

		checkState(update == 1, "order가 save되 않았습니다. orderId = " + order.getOrderId());

		return order;
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		final String ORDER_FIND_BY_ID_SQL = "select * from orders where order_id = :orderId";

		try {
			return Optional.of(jdbcTemplate.queryForObject(ORDER_FIND_BY_ID_SQL,
				Collections.singletonMap("orderId", UUIDConverter.uuidToBytes(orderId)), toMapper()));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Order> findAll() {
		final String ORDER_FIND_ALL_SQL = "select * from orders";
		return jdbcTemplate.query(ORDER_FIND_ALL_SQL, toMapper());
	}

	@Override
	public Order update(Order order) {
		final String ORDER_UPDATE_SQL =
			"update orders set voucher_id = :voucherId , email = :email, address = :address,"
				+ " postcode = :postcode, created_at = :createdAt , updated_at = :updatedAt"
				+ " where order_id = :orderId";

		final int update = jdbcTemplate.update(ORDER_UPDATE_SQL, orderToParamMap(order));

		checkState(update == 1, "order가 update되지 않았습니다. orderId = " + order.getOrderId());

		return order;
	}

	@Override
	public void deleteAll() {
		final String DELETE_SQL = "delete from orders";

		jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());

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

	// return Map.of(
	// 	"orderId", UUIDConverter.uuidToBytes(order.getOrderId()),
	// 	"voucherId", UUIDConverter.uuidToBytes(order.getVoucherId()),
	// 	"email", order.getEmail(),
	// 	"address", order.getAddress(),
	// 	"postcode", order.getPostcode(),
	// 	"createdAt", order.getCreatedAt(),
	// 	"updatedAt", order.getUpdatedAt()
	// 	);

	@Override
	public RowMapper<Order> toMapper() {
		return (rs, rowNum) -> {
			final UUID orderId = UUIDConverter.bytesToUUID(rs.getBytes("order_id"));
			final UUID voucherId = UUIDConverter.bytesToUUID(rs.getBytes("voucher_id"));
			final String email = rs.getString("email");
			final String address = rs.getString("address");
			final String postcode = rs.getString("postcode");
			final LocalDateTime createdAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("created_at"));
			final LocalDateTime updatedAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("updated_at"));

			final List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);

			return Order.bind(orderId, voucherId, email, address, postcode, orderItems, createdAt, updatedAt);
		};
	}
}
