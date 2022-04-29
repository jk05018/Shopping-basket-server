package org.prgms.shoppingbasket.domain.voucher.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTest {

	@DisplayName("Voucher 생성 성공 테스트")
	@Test
	void voucher_create_pass_test() {
		// when
		final Voucher fixedVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.create(1000);
		// then
		assertThat(fixedVoucher.getVoucherId()).isNotNull();
		assertThat(fixedVoucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER);
		assertThat(fixedVoucher.getValue()).isEqualTo(1000);

		// when
		final Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.create(50);
		// then
		assertThat(percentVoucher.getVoucherId()).isNotNull();
		assertThat(percentVoucher.getType()).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER);
		assertThat(percentVoucher.getValue()).isEqualTo(50);
	}

	@DisplayName("fixedAmountVoucher의 할인 금액은 0원 초과 100만원 이하여야 한다.")
	@Test
	void fixedAmountVoucher_value_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(-10));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(0));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(1000001));
	}

	@DisplayName("percentDiscountVoucher의 할인율은 0% 초과 100% 이하여야 한다.")
	@Test
	void percentDiscountVoucher_value_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(-10));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(0));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(101));
	}
}
