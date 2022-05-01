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
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Voucher save(Voucher voucher) {
		final String VOUCHER_SAVE_SQL =
			"insert into vouchers (voucher_id, value, type, description, created_at, updated_at)"
				+ " values (:voucherId, :value, :type, :description, :createdAt, :updatedAt)";
		final int update = jdbcTemplate.update(VOUCHER_SAVE_SQL, voucherToParamMap(voucher));

		if (update != 1) {
			throw new DatabaseException("product가 save되지 않았습니다.");
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		final String VOUCHER_FIND_BY_ID_SQL = "select * from vouchers where voucher_id = :voucherId";

		try {
			return Optional.of(jdbcTemplate.queryForObject(VOUCHER_FIND_BY_ID_SQL,
				Collections.singletonMap("voucherId", UUIDConverter.uuidToBytes(voucherId)), toVoucherMapper()));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Voucher> findAll() {
		final String VOUCHER_FIND_ALL_SQL = "select * from vouchers";
		return jdbcTemplate.query(VOUCHER_FIND_ALL_SQL, toVoucherMapper());
	}

	@Override
	public void deleteAll() {
		final String DELETE_SQL = "delete from vouchers";

		jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());
	}

	private Map<String, Object> voucherToParamMap(Voucher voucher) {
		final HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("voucherId", UUIDConverter.uuidToBytes(voucher.getVoucherId()));
		paramMap.put("value", voucher.getValue());
		paramMap.put("type", voucher.getType());
		paramMap.put("description", voucher.getDescription());
		paramMap.put("createdAt", voucher.getCreatedAt());
		paramMap.put("updatedAt", voucher.getUpdatedAt());

		return paramMap;
	}

	private RowMapper<Voucher> toVoucherMapper() {
		return (rs, rowNum) -> {
			final UUID voucherId = UUIDConverter.bytesToUUID(rs.getBytes("voucher_id"));
			final int value = rs.getInt("value");
			final String type = rs.getString("type");
			final String description = rs.getString("description");
			final LocalDateTime createdAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("created_at"));
			final LocalDateTime updatedAt = LocalDateTimeUtil.toLocalDateTime(rs.getTimestamp("updated_at"));

			return new Voucher(voucherId, value, type, description, createdAt, updatedAt);
		};
	}
}
