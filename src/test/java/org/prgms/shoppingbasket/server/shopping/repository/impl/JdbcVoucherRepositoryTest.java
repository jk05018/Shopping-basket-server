package org.prgms.shoppingbasket.server.shopping.repository.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.entity.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcVoucherRepositoryTest {

	@Autowired
	JdbcVoucherRepository voucherRepository;

	@AfterEach
	void afterEach() {
		voucherRepository.deleteAll();
	}

	@DisplayName("productRepository 자동 주입 테스트")
	@Test
	void productRepository_autowired_test() {
		assertThat(voucherRepository).isNotNull();
	}

	@DisplayName("product save Test")
	@Test
	void product_save_pass_test() {
		// given
		final Voucher fixVoucher = new Voucher(1000, VoucherType.FIXED_AMOUNT_VOUCHER.name(), "1000원 할인입니다!");
		// when
		final Voucher savedFixVoucher = voucherRepository.save(fixVoucher);
		final Optional<Voucher> findVoucher = voucherRepository.findById(savedFixVoucher.getVoucherId());
		// then
		assertThat(findVoucher).isNotNull();
		assertThat(findVoucher.get().getVoucherId()).isEqualTo(fixVoucher.getVoucherId());
		assertThat(findVoucher.get().getType()).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER.name());

		// given
		final Voucher percentVoucher = new Voucher(50, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), "50% 할인입니다!");
		// when
		final Voucher savevdPercentVoucher = voucherRepository.save(percentVoucher);
		final Optional<Voucher> findPercentVoucher = voucherRepository.findById(savevdPercentVoucher.getVoucherId());
		// then
		assertThat(findPercentVoucher).isNotNull();
		assertThat(findPercentVoucher.get().getVoucherId()).isEqualTo(percentVoucher.getVoucherId());
		assertThat(findPercentVoucher.get().getType()).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER.name());
	}


	@DisplayName("findAll Test")
	@Test
	void findAll_Test() {
		// given

		final Voucher fixVoucher = new Voucher(1000, VoucherType.FIXED_AMOUNT_VOUCHER.name(), "1000원 할인입니다!");
		final Voucher percentVoucher = new Voucher(50, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), "50% 할인입니다!");

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
