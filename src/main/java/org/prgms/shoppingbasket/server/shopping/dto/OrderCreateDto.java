package org.prgms.shoppingbasket.server.shopping.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.prgms.shoppingbasket.server.common.validation.email.EmailConstraint;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
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
