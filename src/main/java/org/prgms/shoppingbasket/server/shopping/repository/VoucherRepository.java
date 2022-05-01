package org.prgms.shoppingbasket.server.shopping.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);

	Optional<Voucher> findById(UUID voucherId);

	List<Voucher> findAll();

	void deleteAll();
}
