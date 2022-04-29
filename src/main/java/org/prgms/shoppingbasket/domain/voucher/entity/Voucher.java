package org.prgms.shoppingbasket.domain.voucher.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.google.common.base.Preconditions;

import lombok.Getter;

/**
 * 바우처 실물
 */
@Getter
public class Voucher {
	/* 바우처 식별 번호 */
	private final UUID voucherId;
	/* 할인 금액, 할인 율 */
	private final int value;
	/* 바우처 타입 */
	private final VoucherType type;
	/* 바우처 생성 시간 */
	private final LocalDateTime createdAt;
	/* 바우처 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public Voucher(UUID voucherId, int value, VoucherType type) {
		checkNotNull(voucherId, "voucherId는 null이면 안됩니다.");
		checkNotNull(type, "voucherType는 null이면 안됩니다.");

		this.voucherId = voucherId;
		this.value = value;
		this.type = type;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}


}
