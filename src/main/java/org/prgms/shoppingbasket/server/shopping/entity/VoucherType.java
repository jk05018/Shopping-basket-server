package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

/**
 * Voucher의 종류
 */
public enum VoucherType {
	/* 고정 금액 할인 Voucher */
	FIXED_AMOUNT_VOUCHER {
		@Override
		public Voucher create(int value, String description) {
			return new Voucher(value, VoucherType.FIXED_AMOUNT_VOUCHER.name(), description);
		}

		/* 퍼센트 할인 Voucher */
	}, PERCENT_DISCOUNT_VOUCHER {
		@Override
		public Voucher create(int value, String description) {
			return new Voucher(value, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), description);
		}
	};

	public abstract Voucher create(int value, String description);
}
