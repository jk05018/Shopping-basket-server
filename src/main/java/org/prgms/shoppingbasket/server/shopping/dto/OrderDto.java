package org.prgms.shoppingbasket.server.shopping.dto;

import java.util.List;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private UUID voucherId;
	private String email;
	private String address;
	private String postcode;
	private List<OrderItem> orderItems;
}
