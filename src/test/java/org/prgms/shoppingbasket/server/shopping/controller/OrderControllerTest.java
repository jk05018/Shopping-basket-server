package org.prgms.shoppingbasket.server.shopping.controller;

import static org.prgms.shoppingbasket.server.shopping.dto.OrderDto.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.fixture.OrderFixture;
import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderFixture orderFixture;

	@DisplayName("createOrder 성공 테스트")
	@Test
	void createOrder_pass_test() throws Exception {
		final Order order = orderFixture.createOrder();

		final CreateRequestDto createDto = CreateRequestDto.builder()
			.voucherId(List.of(order.getVoucherId()))
			.email("j05018@naver.com")
			.address("서울시 강남구")
			.postcode("12345")
			.orderItems(order.getOrderItems())
			.build();

		System.out.println(objectMapper.writeValueAsString(createDto));

		mock.perform(post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)))
			.andExpect(status().isCreated());

		// mock진행한 뒤 반환된 응답 내부를 test하는 로직도 필요함
		// OrderRepository도 불러와서 잘 들어갔는지도 확인해보자
	}

	@DisplayName("createOrder 실패 테스트")
	@Test
	void createOrder_fail_test() throws Exception {
		final Order order = orderFixture.createOrder();

		final CreateRequestDto createDto_without_orderItems = CreateRequestDto.builder()
			.voucherId(List.of(order.getVoucherId()))
			.email("j05018@naver.com")
			.address("서울시 강남구")
			.postcode("12345")
			.build();

		mock.perform(post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto_without_orderItems)))
			.andExpect(status().isBadRequest());
	}

	@DisplayName("update 성공 테스트")
	@Test
	void update_test() throws Exception {

		final Order order = orderFixture.createOrder();

		final UpdateRequestDto updateDto = UpdateRequestDto.builder()
			.email("j05018@naver.com")
			.address("서울시 강남구")
			.postcode("12345")
			.build();

		mock.perform(post("/api/v1/orders/" + order.getOrderId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)))
			.andExpect(status().isAccepted());
	}

	@DisplayName("custom한 EmailConstraint 작동 확인 테스트")
	@Test
	void update_test_with_invalid_email() throws Exception {

		final Order order = orderFixture.createOrder();

		final UpdateRequestDto updateDto = UpdateRequestDto.builder()
			.email("jk05018")
			.address("서울시 강남구")
			.postcode("12345")
			.build();

		mock.perform(post("/api/v1/orders/" + order.getOrderId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)))
			.andExpect(status().isBadRequest());
	}

}
