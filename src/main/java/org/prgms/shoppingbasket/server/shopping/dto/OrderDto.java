package org.prgms.shoppingbasket.server.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.prgms.shoppingbasket.server.common.validation.email.EmailConstraint;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DTO에서는 RequestDto와 REsponse DTO 둘 다 만들어라
// DTO를 만들떄는 최소한으로 만ㄷ르어 @Getter이랑 @NoArgsConstructor
// 내부에 static class를 선언해서 관리해보자 requdstdto, responsedto
public class OrderDto {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateRequestDto{

		private List<UUID> voucherId;

		@NotBlank(message = "email is blank")
		@EmailConstraint(message = "email is not valid")
		private String email;

		@NotBlank(message = "address is blank")
		private String address;

		@NotBlank(message = "postcode is blank")
		private String postcode;

		@NotEmpty(message = "orderItems is empty")
		private List<OrderItem> orderItems;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OrderResponseDto {

		/** 주문 식별 번호**/
		private UUID orderId;
		/* 바우처 식별번호 */
		private UUID voucherId;
		/* 고객 이메일 */
		private String email;
		/* 고객 주소 */
		private String address;
		/* 고객 지번 번호 */
		private String postcode;
		/* 주문 상품 리스트 */
		private List<OrderItem> orderItems;
		/* 주문 생성 시간 */
		private LocalDateTime createdAt;
		/* 주문 마지만 update 시간 */
		private LocalDateTime updatedAt;

		public static OrderResponseDto of(Order order){
			return new OrderResponseDto(order.getOrderId(), order.getVoucherId(), order.getEmail(), order.getAddress(),
				order.getPostcode(), order.getOrderItems(), order.getCreatedAt(), order.getUpdatedAt());
		}

	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateRequestDto {
		@EmailConstraint
		private String email;

		@NotBlank
		private String address;

		@NotBlank
		private String postcode;
	}

	public static class UpdateResponseDto{

	}


}
