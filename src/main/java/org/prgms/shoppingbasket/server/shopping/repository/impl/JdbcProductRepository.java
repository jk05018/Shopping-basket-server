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
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.repository.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Product save(Product product) {
		final String PRODUCT_SAVE_SQL =
			"insert into products (product_id, product_name, price, remain_quantity, description, created_at, updated_at)"
				+ " values (:productId, :productName, :price, :remainQuantity, :description, :createdAt, :updatedAt)";
		final int update = jdbcTemplate.update(PRODUCT_SAVE_SQL, productToParamMap(product));

		if (update != 1) {
			throw new DatabaseException("product가 save되지 않았습니다.");
		}

		return product;
	}

	@Override
	public Optional<Product> findById(UUID productId) {
		final String PRODUCT_FIND_BY_ID_SQL = "select * from products where product_id = :productId";

		try {
			return Optional.of(jdbcTemplate.queryForObject(PRODUCT_FIND_BY_ID_SQL,
				Collections.singletonMap("productId", UUIDConverter.uuidToBytes(productId)), toProductMapper()));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Product> findAll() {
		final String PRODUCT_FIND_ALL_SQL = "select * from products";
		return jdbcTemplate.query(PRODUCT_FIND_ALL_SQL, toProductMapper());
	}

	@Override
	public Product update(Product product) {
		final String PRODUCT_UPDATE_SQL =
			"update products set product_id = :productId, product_name = :productName , price = :price,"
				+ " remain_quantity = :remainQuantity, description = :description, created_at = :createdAt, updated_at = :updatedAt"
				+ " where product_id = :productId";

		final int update = jdbcTemplate.update(PRODUCT_UPDATE_SQL, productToParamMap(product));

		if (update != 1) {
			throw new DatabaseException("product가 update되지 않았습니다.");
		}

		return product;
	}

	@Override
	public void deleteAll() {
		final String DELETE_SQL = "delete from products";

		jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());

	}

	private Map<String, Object> productToParamMap(Product product) {
		final HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", UUIDConverter.uuidToBytes(product.getProductId()));
		paramMap.put("productName", product.getProductName());
		paramMap.put("price", product.getPrice());
		paramMap.put("remainQuantity", product.getRemainQuantity());
		paramMap.put("description", product.getDescription());
		paramMap.put("createdAt", product.getCreatedAt());
		paramMap.put("updatedAt", product.getUpdatedAt());

		return paramMap;
	}

	private RowMapper<Product> toProductMapper() {
		return (rs, rowNum) -> {
			final UUID productId = UUIDConverter.bytesToUUID(rs.getBytes("product_id"));
			final String productName = rs.getString("product_name");
			final int price = rs.getInt("price");
			final int remainQuantity = rs.getInt("remain_quantity");
			final String description = rs.getString("description");
			final LocalDateTime createdAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("created_at"));
			final LocalDateTime updatedAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("updated_at"));

			return new Product(productId, productName, price, remainQuantity, description, createdAt, updatedAt);

		};
	}
}
