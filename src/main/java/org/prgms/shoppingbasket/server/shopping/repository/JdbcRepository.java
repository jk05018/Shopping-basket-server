package org.prgms.shoppingbasket.server.shopping.repository;

import org.springframework.jdbc.core.RowMapper;

public interface JdbcRepository<T> {

	RowMapper<T> toMapper();
}
