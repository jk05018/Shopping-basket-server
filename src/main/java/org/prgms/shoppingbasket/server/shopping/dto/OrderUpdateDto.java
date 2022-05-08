package org.prgms.shoppingbasket.server.shopping.dto;

import javax.validation.constraints.NotBlank;

import org.prgms.shoppingbasket.server.common.validation.email.EmailConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {

	@EmailConstraint
	private String email;

	@NotBlank
	private String address;

	@NotBlank
	private String postcode;

}
