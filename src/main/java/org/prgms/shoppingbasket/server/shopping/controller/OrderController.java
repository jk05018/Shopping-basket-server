package org.prgms.shoppingbasket.server.shopping.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.prgms.shoppingbasket.server.common.exception.ApiException;
import org.prgms.shoppingbasket.server.common.exception.ApiExceptionEntity;
import org.prgms.shoppingbasket.server.common.exception.ExceptionEnum;
import org.prgms.shoppingbasket.server.shopping.dto.OrderCreateDto;
import org.prgms.shoppingbasket.server.shopping.dto.OrderUpdateDto;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public List<Order> getOrderList(){
		return orderService.getAllOrders();
	}

	@PostMapping
	public Order createOrder(@Valid @RequestBody OrderCreateDto orderRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()){
			throw new ApiException(ExceptionEnum.BINDING_EXCEPTION.withMessage(errorToMessage(bindingResult)));
		}
		if (orderRequest.getVoucherId().size() != 0) {
			return orderService.createOrder(orderRequest.getVoucherId().get(0), orderRequest.getEmail(),
				orderRequest.getAddress(), orderRequest.getPostcode(), orderRequest.getOrderItems());
		}
		return orderService.createOrder(null, orderRequest.getEmail(), orderRequest.getAddress()
			, orderRequest.getPostcode(), orderRequest.getOrderItems());
	}

	@PostMapping("/{orderId}")
	public Order updateDeleveryDestination(@PathVariable UUID orderId, @Valid @RequestBody OrderUpdateDto orderUpdateDto,BindingResult bindingResult, HttpServletResponse response) {

		if(bindingResult.hasErrors()){
			throw new ApiException(ExceptionEnum.BINDING_EXCEPTION.withMessage(errorToMessage(bindingResult)));
		}

		return orderService.updateDeliveryDestination(orderId, orderUpdateDto.getEmail(), orderUpdateDto.getAddress(),
			orderUpdateDto.getPostcode());
	}

	@DeleteMapping("/{orderId}")
	public void deleteOrder(@PathVariable UUID orderId){
		orderService.deleteOrder(orderId);
	}

	private String errorToMessage(BindingResult bindingResult){
		StringBuilder builder = new StringBuilder();
		bindingResult.getAllErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.forEach(builder::append);

		return builder.toString();
	}

}
