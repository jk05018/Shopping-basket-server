package org.prgms.shoppingbasket.server.shopping.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.prgms.shoppingbasket.server.common.validation.email.EmailConstraint;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


// DTO에서는 RequestDto와 REsponse DTO 둘 다 만들어라
// DTO를 만들떄는 최소한으로 만ㄷ르어 @Getter이랑 @NoArgsConstructor
// 내부에 static class를 선언해서 관리해보자 requdstdto, responsedto
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

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
