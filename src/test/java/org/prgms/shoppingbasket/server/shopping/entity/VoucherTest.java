package org.prgms.shoppingbasket.server.shopping.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTest {

	@DisplayName("Voucher 생성 성공 테스트")
	@Test
	void voucher_create_pass_test() {
		// when
		final Voucher fixedVoucher = VoucherType.FIXED_AMOUNT_VOUCHER.create(1000, "1000원 할인!");
		// then
		assertThat(fixedVoucher.getVoucherId()).isNotNull();
		assertThat(fixedVoucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER.name());
		assertThat(fixedVoucher.getValue()).isEqualTo(1000);

		// when
		final Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.create(50, "50% 할인!");
		// then
		assertThat(percentVoucher.getVoucherId()).isNotNull();
		assertThat(percentVoucher.getType()).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER.name());
		assertThat(percentVoucher.getValue()).isEqualTo(50);
	}

	@DisplayName("fixedAmountVoucher의 할인 금액은 0원 초과 100만원 이하여야 한다.")
	@Test
	void fixedAmountVoucher_value_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(-10, "10원 줍니다"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(0, "할인 안해줄거야!"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED_AMOUNT_VOUCHER.create(1000001, "이건 무슨일?"));
	}

	@DisplayName("percentDiscountVoucher의 할인율은 0% 초과 100% 이하여야 한다.")
	@Test
	void percentDiscountVoucher_value_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(-10, "음수는 안돼!"));
		assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(0, "할인 안해줄거야!"));
		assertThrows(IllegalArgumentException.class,
			() -> VoucherType.PERCENT_DISCOUNT_VOUCHER.create(101, "101퍼센트 할인?"));
	}

	@DisplayName("Percent Voucher의 할인율은 1% 이상 100% 이하여야 한다.")
	@Test
	void percent_discount_fail_test() {
		// given, when, then
		assertThatThrownBy(
			() -> new Voucher(150, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), "150% 할인입니다!")).isInstanceOf(
			IllegalArgumentException.class);
		assertThatThrownBy(
			() -> new Voucher(250, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), "150% 할인입니다!")).isInstanceOf(
			IllegalArgumentException.class);
	}
}
