package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.prgms.shoppingbasket.server.shopping.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JdbcVoucherRepositoryTest {

	@Autowired
	VoucherRepository voucherRepository;

	@DisplayName("productRepository 자동 주입 테스트")
	@Test
	void productRepository_autowired_test() {
		assertThat(voucherRepository).isNotNull();
	}

	@DisplayName("product save Test")
	@Test
	void product_save_pass_test() {
		// given
		final Voucher fixVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.create(1000, "1000원 할인");

		// when
		final Voucher savedFixVoucher = voucherRepository.save(fixVoucher);
		final Voucher findVoucher = voucherRepository.findById(savedFixVoucher.getVoucherId()).orElseThrow();

		// then
		assertThat(findVoucher.getVoucherId()).isEqualTo(fixVoucher.getVoucherId());
		assertThat(findVoucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER.name());
		assertThat(findVoucher.getValue()).isEqualTo(1000);
		assertThat(findVoucher.getDescription()).isEqualTo("1000원 할인");

		// given
		final Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.create(50,  "50% 할인");
		// when
		final Voucher savevdPercentVoucher = voucherRepository.save(percentVoucher);
		final Voucher findPercentVoucher = voucherRepository.findById(savevdPercentVoucher.getVoucherId()).orElseThrow();
		// then
		assertThat(findPercentVoucher.getVoucherId()).isEqualTo(percentVoucher.getVoucherId());
		assertThat(findPercentVoucher.getType()).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER.name());
		assertThat(findPercentVoucher.getValue()).isEqualTo(50);
		assertThat(findPercentVoucher.getDescription()).isEqualTo("50% 할인");
	}

	@DisplayName("findAll Test")
	@Test
	void findAll_Test() {
		// given
		final Voucher fixVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.create(1000, "1000원 할인");
		final Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.create(50, "50% 할인입니다!");

		voucherRepository.save(fixVoucher);
		voucherRepository.save(percentVoucher);

		//when
		final List<Voucher> vouchers = voucherRepository.findAll();

		//then
		assertThat(vouchers.size()).isEqualTo(2);
		assertThat(vouchers).map(v -> v.getVoucherId())
			.contains(fixVoucher.getVoucherId(), percentVoucher.getVoucherId());
	}

}
