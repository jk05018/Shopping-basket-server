package org.prgms.shoppingbasket.domain.voucher.entity;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

/**
 * Voucher의 종류
 */
public enum VoucherType {
	/* 고정 금액 할인 Voucher */
	FIXED_AMOUNT_VOUCHER {
		@Override
		Voucher create(int value) {
			checkArgument(0 < value && value <= 1000000, "할인 금액은 0원 이상 100만원 미만이어야 합니다.");

			return new Voucher(UUID.randomUUID(), value, VoucherType.FIXED_AMOUNT_VOUCHER);
		}

		@Override
		Voucher create(UUID voucherId, int value) {
			return new Voucher(voucherId, value, VoucherType.FIXED_AMOUNT_VOUCHER);
		}

	/* 퍼센트 할인 Voucher */
	}, PERCENT_DISCOUNT_VOUCHER {
		@Override
		Voucher create(int value) {
			checkArgument(0 < value && value < 100, "할인율은 0% 이상 100% 미만이어야 합니다.");

			return new Voucher(UUID.randomUUID(), value, VoucherType.PERCENT_DISCOUNT_VOUCHER);
		}

		@Override
		Voucher create(UUID voucherId, int value) {
			return new Voucher(voucherId, value, VoucherType.PERCENT_DISCOUNT_VOUCHER);
		}
	};

	abstract Voucher create(int value);
	abstract Voucher create(UUID voucherId, int value);
}
