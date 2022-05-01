package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
	private final String type;
	/* 바우처 상세 설명 */
	private final String description;
	/* 바우처 생성 시간 */
	private final LocalDateTime createdAt;
	/* 바우처 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public Voucher(UUID voucherId, int value, String type, String description, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		checkNotNull(voucherId, "voucherId는 null이면 안됩니다.");
		checkNotNull(type != null , "voucherType는 null이면 안됩니다.");
		checkArgument(0 < value && value <= 1000000, "할인 금액은 0원 이상 100만원 미만이어야 합니다.");
		checkArgument(description != null && description.length() > 0, "description은 한글자 이상이어야 합니다.");

		this.voucherId = voucherId;
		this.value = validateValueByType(value,type);
		this.type = type;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private int validateValueByType(int value, String type) {
		if(type.equals(VoucherType.PERCENT_DISCOUNT_VOUCHER.name())){
			checkArgument(value > 0 && value <= 100, "할인율은 1% 이상 100% 이하여야 합니다.");
		}
		return value;
	}

	public Voucher( int value, String type, String description) {
		this(UUID.randomUUID(), value, type, description, LocalDateTime.now(), LocalDateTime.now());
	}


}
