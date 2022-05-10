package org.prgms.shoppingbasket.server.shopping.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {
	/* 바우처 식별 번호 */
	private UUID voucherId;
	/* 할인 금액, 할인 율 */
	private int value;
	/* 바우처 타입 */
	private String type;
	/* 바우처 상세 설명 */
	private String description;
	/* 바우처 생성 시간 */
	private LocalDateTime createdAt;
	/* 바우처 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public static VoucherDto of(Voucher voucher){
		return new VoucherDto(voucher.getVoucherId(), voucher.getValue(), voucher.getType(), voucher.getDescription(),
			voucher.getCreatedAt(), voucher.getUpdatedAt());
	}
}
