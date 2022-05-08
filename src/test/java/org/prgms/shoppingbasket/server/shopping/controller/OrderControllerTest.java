package org.prgms.shoppingbasket.server.shopping.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.shoppingbasket.server.fixture.OrderFixture;
import org.prgms.shoppingbasket.server.shopping.dto.OrderCreateDto;
import org.prgms.shoppingbasket.server.shopping.dto.OrderUpdateDto;
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

		final OrderCreateDto createDto = OrderCreateDto.builder()
			.voucherId(List.of(order.getVoucherId()))
			.email("j05018@naver.com")
			.address("서울시 강남구")
			.postcode("12345")
			.orderItems(order.getOrderItems())
			.build();

		mock.perform(post("/api/v1/orders")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(createDto)))
			.andExpect(status().isCreated());
	}

	@DisplayName("createOrder 실패 테스트")
	@Test
	void createOrder_fail_test() throws Exception {
		final Order order = orderFixture.createOrder();

		final OrderCreateDto createDto_without_orderItems = OrderCreateDto.builder()
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

		final OrderUpdateDto updateDto = OrderUpdateDto.builder()
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

		final OrderUpdateDto updateDto = OrderUpdateDto.builder()
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
