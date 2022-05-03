package org.prgms.shoppingbasket.server.shopping.controller;

import org.prgms.shoppingbasket.server.shopping.dto.OrderDto;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders")
	public Order createOrder(@RequestBody OrderDto orderRequest) {
		if (orderRequest.getVoucherId() != null) {
			return orderService.createOrder(orderRequest.getVoucherId(), orderRequest.getEmail(),
				orderRequest.getAddress(), orderRequest.getPostcode(), orderRequest.getOrderItems());
		}
		return orderService.createOrder(null, orderRequest.getEmail(), orderRequest.getAddress()
			, orderRequest.getPostcode(), orderRequest.getOrderItems());
	}
}
