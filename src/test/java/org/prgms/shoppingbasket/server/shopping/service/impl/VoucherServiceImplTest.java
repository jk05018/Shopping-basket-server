package org.prgms.shoppingbasket.server.shopping.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class VoucherServiceImplTest {

	@Autowired
	VoucherService voucherService;
	@Autowired
	VoucherRepository voucherRepository;

	@DisplayName("productService createProduct testd")
	@Test
	void productservice_create_test() {
		// given
		final Voucher savedVoucher = voucherService.createVoucher("FIXED_AMOUNT_VOUCHER", 10000, "10000원 할인");

		//when
		final Optional<Voucher> findVocuher = voucherRepository.findById(savedVoucher.getVoucherId());

		//then
		assertThat(findVocuher).isNotNull();
		assertThat(findVocuher.get().getVoucherId()).isEqualTo(savedVoucher.getVoucherId());
		assertThat(findVocuher.get().getType()).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER.name());
		assertThat(findVocuher.get().getValue()).isEqualTo(savedVoucher.getValue());

	}

	@DisplayName("productService findAll test")
	@Test
	void productService_findAll_test() {
		// given
		final Voucher savedVoucher1 = voucherService.createVoucher("FIXED_AMOUNT_VOUCHER", 10000, "10000원 할인");
		final Voucher savedVoucher2 = voucherService.createVoucher("PERCENT_DISCOUNT_VOUCHER", 30, "30% 할인");

		//when
		final List<Voucher> vouchers = voucherService.getAllVouchers();

		//then
		assertThat(vouchers).map(p -> p.getVoucherId())
			.contains(savedVoucher1.getVoucherId(), savedVoucher2.getVoucherId());

	}

}
