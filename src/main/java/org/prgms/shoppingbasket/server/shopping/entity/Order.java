package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Order {
	/* 주문 식별번호 */
	private final UUID orderId;
	/* 바우처 식별번호 */
	private UUID voucherId;
	/* 고객 이메일 */
	private String email;
	/* 고객 주소 */
	private String address;
	/* 고객 지번 번호 */
	private String postcode;
	/* 주문 상품 리스트 */
	private final List<OrderItem> orderItems;
	/* 주문 생성 시간 */
	private final LocalDateTime createdAt;
	/* 주문 마지만 update 시간 */
	private LocalDateTime updatedAt;

	private Order(UUID orderId, UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
		checkNotNull(orderId, "orderId는 null이면 안됩니다.");
		validateFields(email, address, postcode);

		this.orderId = orderId;
		this.voucherId = voucherId;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderItems = orderItems;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private void validateFields(String email, String address, String postcode){
		checkArgument(checkEmail(email), "email은 형식에 맞춰서 기입해야 합니다.");
		checkNotNull(address, "address 는 공백이면 안됩니다");
		checkNotNull(postcode, "postcode 는 공백이면 안됩니다");
	}

	private boolean checkEmail(String email) {
		return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email);
	}

	public static Order create(UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems) {
		return new Order(UUID.randomUUID(), voucherId, email, address, postcode, orderItems, LocalDateTime.now(),
			LocalDateTime.now());
	}

	/**
	 * 데이터베이스 Binding 용
	 */
	public static Order bind(UUID orderId, UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
		return new Order(orderId, voucherId, email, address, postcode, orderItems, createdAt, updatedAt);
	}

	public void updateDeliveryDestination(String email, String address, String postcode) {
		validateFields(email, address, postcode);

		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.updatedAt = LocalDateTime.now();
	}

}
