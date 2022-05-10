package org.prgms.shoppingbasket.server.shopping.controller;

import static org.prgms.shoppingbasket.server.shopping.dto.OrderDto.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.prgms.shoppingbasket.server.common.exception.ApiException;
import org.prgms.shoppingbasket.server.common.exception.ExceptionEnum;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	// ResponseEntity로 반환하는 것도 충분히 납득할 만하다.
	// 그런데 프론트엔드와 잘 협의가 된다면 굳이 응답코드를 나누어서 하지 않아도 된다.
	// 엥간한 Opensource API가 아닌 이상 이렇게 빡세게 할 필요 엾다.
	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrderList() {
		final List<OrderResponseDto> orderList = orderService.getAllOrders().stream().map(o -> OrderResponseDto.of(o))
			.collect(Collectors.toList());

		// ResponseDto로 반환해 주자
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(orderList);
	}

	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(
		@Valid @RequestBody CreateRequestDto orderRequest,
		BindingResult bindingResult) {

		// bindingResult를 굳이 안해줘도 된다?
		// 한번 자세히 알아보자 dto에서 validation을 적용했는데 서버 사이트 렌더링 개발에서는 잡아서 해줬음
		// 근데 API에서는 잡아 줄 필요 없다?
		// 멘토님이 현업에서 DTO에 Validation을 적용할 수도 있고 안할수도 있다고 했다. 잘 사용하지 않으시니 불필요한 코드라고 생각하시지 않았을까
		if (bindingResult.hasErrors()) {
			throw new ApiException(ExceptionEnum.BINDING_EXCEPTION.withMessage(errorToMessage(bindingResult)));
		}

		// 비즈니스 로직
		final OrderResponseDto orderResponseDto = OrderResponseDto.of(
			orderService.createOrder(orderRequest.getVoucherId().get(0), orderRequest.getEmail(),
				orderRequest.getAddress(), orderRequest.getPostcode(), orderRequest.getOrderItems()));

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(orderResponseDto);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<OrderResponseDto> updateDeleveryDestination(@PathVariable UUID orderId,
		@Valid @RequestBody UpdateRequestDto orderUpdateDto, BindingResult bindingResult,
		HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			throw new ApiException(ExceptionEnum.BINDING_EXCEPTION.withMessage(errorToMessage(bindingResult)));
		}

		final OrderResponseDto orderResponseDto = OrderResponseDto.of(
			orderService.updateDeliveryDestination(orderId, orderUpdateDto.getEmail(),
				orderUpdateDto.getAddress(), orderUpdateDto.getPostcode()));

		return ResponseEntity
			.status(HttpStatus.ACCEPTED)
			.body(orderResponseDto);
	}

	@DeleteMapping("/{orderId}")
	public void deleteOrder(@PathVariable UUID orderId) {
		orderService.deleteOrder(orderId);
	}

	private String errorToMessage(BindingResult bindingResult) {
		StringBuilder builder = new StringBuilder();
		bindingResult.getAllErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.forEach(builder::append);

		return builder.toString();
	}

}
