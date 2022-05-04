package org.prgms.shoppingbasket.server.shopping.entity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Voucher의 종류
 */
public enum VoucherType {
	/* 고정 금액 할인 Voucher */
	FIXED_AMOUNT_VOUCHER {
		@Override
		public Voucher create(int value, String description) {
			return Voucher.create(value, VoucherType.FIXED_AMOUNT_VOUCHER.name(), description);
		}

		@Override
		public Voucher bind(UUID voucherId, int value, String type, String description, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
			return Voucher.bind(voucherId, value, type, description, createdAt, updatedAt);
		}

		/* 퍼센트 할인 Voucher */
	}, PERCENT_DISCOUNT_VOUCHER {
		@Override
		public Voucher create(int value, String description) {
			return Voucher.create(value, VoucherType.PERCENT_DISCOUNT_VOUCHER.name(), description);
		}

		@Override
		public Voucher bind(UUID voucherId, int value, String type, String description, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
			return Voucher.bind(voucherId, value, type, description, createdAt, updatedAt);
		}
	};

	public abstract Voucher create(int value, String description);

	public abstract Voucher bind(UUID voucherId, int value, String type, String description, LocalDateTime createdAt,
		LocalDateTime updatedAt);
}
