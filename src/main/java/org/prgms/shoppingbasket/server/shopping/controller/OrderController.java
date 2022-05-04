package org.prgms.shoppingbasket.server.shopping.controller;

import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.dto.OrderCreateDto;
import org.prgms.shoppingbasket.server.shopping.dto.OrderUpdateDto;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public Order createOrder(@RequestBody OrderCreateDto orderRequest) {
		if (orderRequest.getVoucherId().size() != 0) {
			return orderService.createOrder(orderRequest.getVoucherId().get(0), orderRequest.getEmail(),
				orderRequest.getAddress(), orderRequest.getPostcode(), orderRequest.getOrderItems());
		}
		return orderService.createOrder(null, orderRequest.getEmail(), orderRequest.getAddress()
			, orderRequest.getPostcode(), orderRequest.getOrderItems());
	}

	@PostMapping("/{orderId}")
	public Order updateDeleveryDestination(@PathVariable UUID orderId, @RequestBody OrderUpdateDto orderUpdateDto) {
		// order update 로직
		return orderService.updateDeliveryDestination(orderId, orderUpdateDto.getEmail(), orderUpdateDto.getAddress(),
			orderUpdateDto.getPostcode());
	}

}
